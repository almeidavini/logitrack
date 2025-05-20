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

## 🗃️ Modelagem de Dados – MySQL

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

- Suporte a **observabilidade** com log estruturado, tracing distribuído (ex: OpenTelemetry).
- Adição de **circuit breakers** (ex: Resilience4j) para chamadas externas.

---

## 📌 Conclusão

A aplicação foi construída com foco em **baixo acoplamento, alta coesão e responsividade sob carga**, utilizando tecnologias modernas como WebFlux e Java 21. Todas as decisões aqui descritas foram feitas considerando boas práticas de engenharia de software, alinhadas aos requisitos técnicos fornecidos.

