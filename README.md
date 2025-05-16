# LogiTrack API

## Visão Geral

LogiTrack API é uma aplicação backend desenvolvida utilizando Java 21, Spring Boot 3.4 e WebFlux para fornecer funcionalidades de rastreamento e gerenciamento de logística. A API utiliza um banco de dados MySQL para persistência dos dados e pode ser executada via Docker Compose.

## Tecnologias Utilizadas

* **Java:** 21
* **Spring Boot:** 3.4
* **WebFlux:** Para programação reativa e não-bloqueante
* **MySQL:** Banco de dados relacional
* **Docker:** Para conteinerização da aplicação
* **Docker Compose:** Para orquestração dos serviços Docker

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

* Java Development Kit (JDK) 21 ou superior
* Maven
* Docker
* Docker Compose (se desejar executar com Docker Compose)
* MySQL Server (opcional, se não usar o Docker Compose)

## Execução com Docker Compose

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