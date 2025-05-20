<h1 align="center">
  LogiTrack - Api
</h1>

## Descrição

Sistema desenvolvido como parte do processo seletivo **Inbound - Mercado Livre**, com foco em **performance**, **escalabilidade** e **resiliência**. A aplicação oferece uma API RESTful para gerenciar o ciclo de vida de pacotes e eventos de rastreamento em um sistema logístico de alta escala.


## Pré-requisitos

* Docker

## Tecnologias

* **Java:** 21
* **Spring Boot:** 3.4
* **WebFlux:** Para programação reativa e não-bloqueante
* **MySQL:** Banco de dados relacional
* **Docker:** Para conteinerização da aplicação
* **Docker Compose:** Para orquestração dos serviços Docker

## Execução

Para executar a aplicação utilizando Docker Compose (configurado em `app/local`):

1.  Navegue até o diretório `app/local`:
    ```bash
    cd app/local
    ```
2.  Execute os serviços definidos no `docker-compose.yml`:
    ```bash
    docker-compose up -d --build
    ```
    A API estará disponível em `http://localhost:8080`. O banco de dados MySQL estará acessível na porta padrão (configurada no `docker-compose.yml`).
	
## Utilização

Após execução, a API estará disponível em:  
`http://localhost:8080`

### 1. Criar um novo usuário

**POST** `/logitrack/api/users`  
Registra um novo usuário, verificando a integridade dos dados fornecidos e se o e-mail fornecido já foi cadastrado anteriormente.

#### Requisição:
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


#### Resposta:
```json
{
	"id": "e89d88ce-2b16-4310-96e5-df01a72fb28e",
	"name": "João",
	"email": "joao@gmail.com",
	"address": {
		"street": "Rua Da Paz",
		"city": "São Paulo",
		"state": "SP",
		"zip_code": "03591-020",
		"country": "BR",
		"created_at": "2025-05-20T09:43:48",
		"updated_at": "2025-05-20T09:43:48"
	},
	"created_at": "2025-05-20T09:43:48",
	"updated_at": "2025-05-20T09:43:48"
}
```

**GET** `/logitrack/api/users/{id}`  
Restorna usuário de acordo com o id fornecido.

#### Resposta:
```json
{
	"id": "e89d88ce-2b16-4310-96e5-df01a72fb28e",
	"name": "João",
	"email": "joao@gmail.com",
	"address": {
		"street": "Rua Da Paz",
		"city": "São Paulo",
		"state": "SP",
		"zip_code": "03591-020",
		"country": "BR",
		"created_at": "2025-05-20T09:43:48",
		"updated_at": "2025-05-20T09:43:48"
	},
	"created_at": "2025-05-20T09:43:48",
	"updated_at": "2025-05-20T09:43:48"
}
```

**POST** `/logitrack/api/parcels`  
Cria um pacote associado aos usuários remetente e destinatario, validando se não são o mesmo usuario e acessando a api de feriado e de fatos sobre cachorro para enriquecer os dados do pacote.

#### Requisição:
```json
{
  "description": "Remedio",
	"sender_id": "64b5b8e0-3f51-425a-974b-0e875023fa0",
	"recipient_id": "64b5b8e0-3f51-425a-974b-0e875023fa0b",
	"estimated_delivery_date": "2025-09-07"
}
```

#### Resposta:
```json
{
	"id": "73a74ab0-5a91-412f-82e0-e7aaef921df4",
	"description": "Remedio",
	"fun_fact": "Dogs see in colors of various shades of blue and yellow.",
	"status": "CREATED",
	"sender": {
		"id": "e89d88ce-2b16-4310-96e5-df01a72fb28e",
		"name": "Pedro"
	},
	"recipient": {
		"id": "64b5b8e0-3f51-425a-974b-0e875023fa0b",
		"name": "Adriana"
	},
	"delivery": {
		"estimated_delivery_date": "2025-09-07",
		"is_holiday": true
	},
	"created_at": "2025-05-20T09:48:24",
	"updated_at": "2025-05-20T09:48:24"
}
```

**PATCH** `/logitrack/api/parcels/{id}`  
Atualiza o status do pacote, podendo ser DELIVERED ou IN_TRANSIT

#### Requisição:
```json
{
	"status": "DELIVERED"
}
```

#### Resposta:
```json
{
	"id": "e0ed4de8-fc35-4121-b87f-faede43697ee",
	"description": "Livro infantil",
	"sender": "Adriana",
	"recipient": "Adriana",
	"status": "DELIVERED",
	"created_at": "2025-05-18T18:35:32",
	"updated_at": "2025-05-18T18:35:32",
	"delivered_at": "2025-05-19T13:47:23"
}
```

**DELETE** `/logitrack/api/parcels/{id}`  
Verifica se o pacote ainda esta no status CREATED e cancela caso for verdadeiro

#### Resposta:
```json
{
	"id": "73a74ab0-5a91-412f-82e0-e7aaef921df4",
	"status": "CANCELLED",
	"data_atualizacao": "2025-05-20T09:48:24"
}
```

**GET** `/logitrack/api/parcels/{id}`  
Lista as informações do pacote, e caso o header events seja fornecido com o falor true retorna também os eventos relacionados ao pacote

#### Resposta:
```json
{
	"id": "5173233e-0aab-4f2c-ae14-c04349ba4b89",
	"description": "Livro infantil",
	"sender": "Adriana",
	"recipient": "Pedro",
	"status": "IN_TRANSIT",
	"created_at": "2025-05-18T17:53:05",
	"updated_at": "2025-05-18T17:53:05",
	"events": [
		{
			"location": "Centro de distribuição",
			"description": "pacote despachado",
			"created_at": "2025-05-20T09:56:16"
		}
	]
}
```

**POST** `/logitrack/api/events`  
Cria um evento relacionado ao pacote

#### Requisição:
```json
{
	"parcel_id": "5173233e-0aab-4f2c-ae14-c04349ba4b89",
	"location": "Centro de distribuição",
	"description": "pacote despachado"
}
```

#### Resposta:
```json
{
	"id": "900e9c28-4cd5-49f0-ad4c-a120836c7844",
	"parcel_id": "5173233e-0aab-4f2c-ae14-c04349ba4b89",
	"location": "Centro de distribuição",
	"description": "pacote despachado",
	"created_at": "2025-05-20T09:56:16"
}
```


**GET** `/logitrack/api/parcels/{id}/history`  
Lista todos os eventos relacionados ao pacote

#### Resposta:
```json
{
	"events": [
		{
			"id": "361bfccc-c8e4-4eb7-8ae9-e2d3db365bc9",
			"parcel_id": "01eb936b-205f-4622-9941-4598b6ac6dcf",
			"location": "Centro de distribuição",
			"description": "pacote despachado",
			"created_at": "2025-05-19T20:21:22"
		},
		{
			"id": "fb384694-dddf-428b-813a-6783bc174313",
			"parcel_id": "01eb936b-205f-4622-9941-4598b6ac6dcf",
			"location": "Centro de distribuição",
			"description": "pacote despachado",
			"created_at": "2025-05-19T20:23:13"
		},
		{
			"id": "33550c3e-0894-427c-a4a4-8aa835e3a9a3",
			"parcel_id": "01eb936b-205f-4622-9941-4598b6ac6dcf",
			"location": "Centro de distribuição",
			"description": "pacote despachado",
			"created_at": "2025-05-19T20:24:02"
		}
	]
}
```