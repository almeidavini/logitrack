# 📘 Decisões Técnicas

Este documento tem como objetivo descrever e justificar as principais decisões técnicas tomadas durante o desenvolvimento da API LogiTrack, considerando os requisitos técnicos estabelecidos e a busca por alta performance, escalabilidade e resiliência.

---

## ☕ Plataforma e Framework

### ✔️ Java 21
A escolha pelo Java 21 se deve ao suporte a novos recursos da linguagem, melhorias de performance e maior suporte a programação orientada a concorrência e virtual threads.

### ✔️ Spring Boot 3.4 com WebFlux
Foi utilizado o Spring WebFlux em vez do tradicional Spring MVC, com os seguintes objetivos:
- **Programação reativa** com suporte nativo a **assincronismo** e **não bloqueio de threads**, fundamental para aplicações que precisam escalar sob alta carga.
- Utilização do modelo **event-loop** reativo (baseado em Project Reactor), ideal para lidar com chamadas IO intensivas, como interações com banco de dados e APIs externas.

---

## 🌐 Gestão de Recursos HTTP

- A API segue os **princípios RESTful**, utilizando corretamente os métodos HTTP (`GET`, `POST`, `PATCH`, `DELETE`) e retornando **códigos de status apropriados** (`200 OK`, `201 Created`, `400 Bad Request`, `404 Not Found`, `422 Unprocessable Entity`, `500 Internal Server Error` etc.).
- Todas as entradas da API são **validadas com anotações reativas** como `@Valid`, `@NotBlank`, `@Email`, garantindo integridade dos dados.
- O uso de **cabeçalhos HTTP** é incentivado para passagem de metadados. Por exemplo:
  - Um cabeçalho personalizado (`events: true`) é utilizado na listagem de pacotes para incluir ou não eventos relacionados.

---

## 🗂️ Modelagem de Dados

O banco de dados foi projetado com foco em **normalização**, **relacionamentos claros** e **eficiência nas consultas**, utilizando MySQL como SGBD. Abaixo estão os principais modelos da aplicação:

### 🧑‍💼 Tabela `users`

Armazena os dados de cada usuário (remetente ou destinatário).

| Campo       | Tipo         | Descrição                              |
|-------------|--------------|----------------------------------------|
| id          | VARCHAR(36)  | Identificador UUID primário            |
| name        | VARCHAR(255) | Nome completo do usuário               |
| email       | VARCHAR(255) | E-mail (único)                         |
| status      | ENUM         | `ACTIVE` ou `INACTIVE`                 |
| created_at  | DATETIME     | Data de criação                        |
| updated_at  | DATETIME     | Data da última atualização             |

- **Índices**: `email` (único), `status`

---

### 🏠 Tabela `addresses`

Cada usuário possui um único endereço vinculado ao seu `id`.

| Campo      | Tipo         | Descrição                                  |
|------------|--------------|--------------------------------------------|
| user_id    | VARCHAR(36)  | Chave primária e referência a `users(id)`  |
| street     | VARCHAR(255) | Logradouro                                 |
| number     | VARCHAR(100) | Número                                     |
| city       | VARCHAR(100) | Cidade                                     |
| state      | VARCHAR(50)  | Estado                                     |
| zip_code   | VARCHAR(20)  | CEP                                        |
| country    | VARCHAR(2)   | Código do país (ex: `BR`)                  |
| created_at | DATETIME     | Data de criação                            |
| updated_at | DATETIME     | Data da última atualização                 |

---

### 📦 Tabela `parcels`

Representa os pacotes enviados de um usuário para outro.

| Campo                    | Tipo         | Descrição                                        |
|--------------------------|--------------|--------------------------------------------------|
| id                       | CHAR(36)     | Identificador UUID primário                      |
| description              | VARCHAR(255) | Descrição do pacote                              |
| sender_id                | CHAR(36)     | Referência a `users(id)` (remetente)            |
| recipient_id             | CHAR(36)     | Referência a `users(id)` (destinatário)         |
| fun_fact                 | TEXT         | Curiosidade sobre cães, obtida via API externa   |
| is_holiday               | BOOLEAN      | Indica se a data prevista é feriado              |
| estimated_delivery_date  | DATE         | Data estimada de entrega                         |
| status                   | ENUM         | `CREATED`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED` |
| created_at               | TIMESTAMP    | Data de criação                                  |
| updated_at               | TIMESTAMP    | Data da última atualização                       |
| delivered_at             | TIMESTAMP    | Data da entrega (quando aplicável)              |

- **Índices**: `sender_id`, `recipient_id`, `status`, `estimated_delivery_date`

---

### 📍 Tabela `events`

Contém os eventos relacionados a um pacote (como atualizações de localização).

| Campo       | Tipo         | Descrição                                      |
|-------------|--------------|------------------------------------------------|
| id          | CHAR(36)     | Identificador UUID primário                    |
| parcel_id   | CHAR(36)     | Referência a `parcels(id)`                     |
| location    | VARCHAR(255) | Local do evento                                |
| description | TEXT         | Detalhes sobre o evento                        |
| created_at  | TIMESTAMP    | Data e hora do evento                          |

- **Índice composto**: `(parcel_id, created_at)` para facilitar ordenação cronológica por pacote

---

Essa modelagem permite a separação de responsabilidades, alta escalabilidade e suporte a consultas performáticas, respeitando os princípios de integridade referencial.


### Desenho das tabelas:
As tabelas foram projetadas para suportar milhões de registros com:
- Uso de chaves primárias do tipo `UUID` (representado como `CHAR(36)`), garantindo unicidade distribuída.
- Criação de **índices em colunas estratégicas**, como status, foreign keys e datas estimadas de entrega para melhorar a performance de leitura em queries frequentes.
- Separação entre `users` e `addresses` com relacionamento 1:1, promovendo a **normalização** e evitando redundância de dados.
- A tabela `events` foi desenhada para acumular grandes volumes de dados relacionados a pacotes, com índice composto em `(parcel_id, created_at)` que favorece ordenação cronológica em buscas.


### Estratégia de Expurgo
O sistema está preparado para suportar **expurgo de dados** através de políticas externas:
- A tabela de `events` pode ser particionada por data em uma futura evolução.
- Pacotes com status `DELIVERED` ou `CANCELLED` há mais de X meses podem ser arquivados ou removidos via jobs agendados.

---

## ⚙️ Processamento Assíncrono

- A API é reativa de ponta a ponta, o que elimina a necessidade de pools tradicionais como `ExecutorService` ou `ForkJoinPool`.
- O **modelo assíncrono do WebFlux** já é suficiente para processar múltiplas requisições simultâneas com segurança e eficiência.
- A criação de pacotes e eventos é tratada de forma não bloqueante e segura, permitindo alto throughput mesmo sob alta carga.

---

## 🔮 Futuras Melhorias Planejadas
- Implementação de **paginação na rota `GET /events`** para evitar sobrecarga de memória e reduzir latência na recuperação de grandes volumes de eventos por pacote.
- Adição de **circuit breakers** (ex: Resilience4j) para chamadas externas.
- Introdução de **cache local ou distribuído** para chamadas à API externa [Nager.Date](https://date.nager.at), evitando requisições repetidas para a mesma data e melhorando a performance geral da criação de pacotes.
---

## 📌 Conclusão

A aplicação foi construída com foco em **baixo acoplamento, alta coesão e responsividade sob carga**, utilizando tecnologias modernas como WebFlux e Java 21. Todas as decisões aqui descritas foram feitas considerando boas práticas de engenharia de software, alinhadas aos requisitos técnicos fornecidos.

