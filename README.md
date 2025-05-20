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