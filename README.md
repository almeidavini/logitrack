<h1 align="center">
  LogiTrack - API
</h1>

## 📦 Descrição

LogiTrack é uma API RESTful desenvolvida como parte do processo seletivo **Inbound - Mercado Livre**, com foco em **performance**, **escalabilidade** e **resiliência**. O sistema gerencia o ciclo de vida de pacotes e eventos de rastreamento em um ambiente logístico de alta escala.

---

## 🚀 Pré-requisitos

- Docker
- Docker Compose

---

## 🛠️ Tecnologias Utilizadas

- **Java:** 21
- **Spring Boot:** 3.4
- **Spring WebFlux:** Programação reativa e não-bloqueante
- **MySQL:** Banco de dados relacional
- **Docker:** Conteinerização da aplicação
- **Docker Compose:** Orquestração dos serviços

---

## ▶️ Execução da Aplicação

Para executar a aplicação via Docker Compose (configurado em `app/local`):

```bash
cd app/local
docker-compose up -d --build
```

A API estará disponível em:  
`http://localhost:8080`

---

## 📚 Endpoints da API

### 👤 Usuários

#### 🔹 Criar Usuário

**POST** `/logitrack/api/users`

Registra um novo usuário e seu endereço.

**Requisição:**
```json
{
  "name": "João",
  "email": "joao@gmail.com",
  "address": {
    "street": "Rua Da Paz",
    "number": "15",
    "city": "São Paulo",
    "state": "SP",
    "zip_code": "03591-020",
    "country": "BR"
  }
}
```

**Resposta:**
```json
{
  "id": "uuid",
  "name": "João",
  "email": "joao@gmail.com",
  "address": {
    "street": "Rua Da Paz",
    "city": "São Paulo",
    "state": "SP",
    "zip_code": "03591-020",
    "country": "BR",
    "created_at": "...",
    "updated_at": "..."
  },
  "created_at": "...",
  "updated_at": "..."
}
```

#### 🔹 Buscar Usuário por ID

**GET** `/logitrack/api/users/{id}`

Retorna um usuário existente.

**Resposta:**
```json
{
	"id": "64b5b8e0-3f51-425a-974b-0e875023fa0b",
	"name": "Adriana",
	"email": "adrianajosefa123@bing.io",
	"address": {
		"street": "Rua Coruripe",
		"city": "São Paulo",
		"state": "SP",
		"zip_code": "03551-020",
		"country": "BR",
		"created_at": "2025-05-18T18:37:43",
		"updated_at": "2025-05-18T18:37:43"
	},
	"created_at": "2025-05-18T18:37:43",
	"updated_at": "2025-05-18T18:37:43"
}
```


### 📦 Pacotes

#### 🔹 Criar Pacote

**POST** `/logitrack/api/parcels`

Cria um novo pacote associado a dois usuários diferentes (remetente e destinatário), acessando APIs externas para verificação de feriado e adição de curiosidades.

**Requisição:**
```json
{
  "description": "Remedio",
  "sender_id": "uuid1",
  "recipient_id": "uuid2",
  "estimated_delivery_date": "2025-09-07"
}
```

**Resposta:**
```json
{
  "id": "uuid",
  "description": "Remedio",
  "fun_fact": "Dogs see in colors of various shades of blue and yellow.",
  "status": "CREATED",
  "sender": {
    "id": "uuid",
    "name": "Pedro"
  },
  "recipient": {
    "id": "uuid",
    "name": "Adriana"
  },
  "delivery": {
    "estimated_delivery_date": "2025-09-07",
    "is_holiday": true
  },
  "created_at": "...",
  "updated_at": "..."
}
```

#### 🔹 Atualizar Status de Pacote

**PATCH** `/logitrack/api/parcels/{id}`

Atualiza o status do pacote para `IN_TRANSIT` ou `DELIVERED`.

**Requisição:**
```json
{
  "status": "DELIVERED"
}
```

**Resposta:**
```json
{
  "id": "uuid",
  "description": "Livro infantil",
  "sender": "Adriana",
  "recipient": "Adriana",
  "status": "DELIVERED",
  "created_at": "...",
  "updated_at": "...",
  "delivered_at": "..."
}
```

#### 🔹 Cancelar Pacote

**DELETE** `/logitrack/api/parcels/{id}`

Cancela um pacote que ainda esteja com status `CREATED`.

**Resposta:**
```json
{
  "id": "uuid",
  "status": "CANCELLED",
  "data_atualizacao": "..."
}
```

#### 🔹 Consultar Pacote por ID

**GET** `/logitrack/api/parcels/{id}`  
Se o header `events: true` for enviado, os eventos também serão retornados.

**Resposta:**
```json
{
  "id": "uuid",
  "description": "Livro infantil",
  "sender": "Adriana",
  "recipient": "Pedro",
  "status": "IN_TRANSIT",
  "created_at": "...",
  "updated_at": "...",
  "events": [
    {
      "location": "Centro de distribuição",
      "description": "pacote despachado",
      "created_at": "..."
    }
  ]
}
```

---

### 📍 Eventos

#### 🔹 Criar Evento

**POST** `/logitrack/api/events`

Cria um novo evento de rastreamento para um pacote existente.

**Requisição:**
```json
{
  "parcel_id": "uuid",
  "location": "Centro de distribuição",
  "description": "pacote despachado"
}
```

**Resposta:**
```json
{
  "id": "uuid",
  "parcel_id": "uuid",
  "location": "Centro de distribuição",
  "description": "pacote despachado",
  "created_at": "..."
}
```

#### 🔹 Histórico de Eventos do Pacote

**GET** `/logitrack/api/parcels/{id}/history`

Retorna todos os eventos associados a um pacote.

**Resposta:**
```json
{
  "events": [
    {
      "id": "uuid",
      "parcel_id": "uuid",
      "location": "Centro de distribuição",
      "description": "pacote despachado",
      "created_at": "..."
    },
    {
      "id": "uuid",
      "parcel_id": "uuid",
      "location": "Centro de distribuição",
      "description": "pacote despachado",
      "created_at": "..."
    }
  ]
}
```

## 📝 Considerações Finais

Este projeto demonstra o uso de arquitetura reativa com Spring WebFlux, integrações com APIs externas e uso de práticas modernas de desenvolvimento com Java 21. Idealizado para cenários de alta demanda e throughput.
