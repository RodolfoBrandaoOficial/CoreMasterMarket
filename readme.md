# 🛠️ Teste Prático Desenvolvimento Java Backend - VR Software

Este repositório contém o projeto desenvolvido como parte do teste prático de desenvolvimento Java Backend para a VR Software. O projeto está dividido em duas partes:

- **Application-Frontend**
- **Application-Backend**

## 🧰 Tecnologias Utilizadas

- **🔑 Autenticação JWT (JSON Web Token)**
- **🗄️ Banco de Dados PostgreSQL**
- **🌱 Spring Boot com JPARepository e queries personalizadas**
- **🧪 Testes Unitários e de Integração usando JUnit 5 e Mockito**
- **📄 Auto documentação com Swagger**

## ⚙️ Configurações da API Backend

- **🔌 Porta da API Backend**: `8081`
- **📑 URL da Documentação Swagger**: [http://localhost:8081/swagger-ui/index.html#/](http://localhost:8081/swagger-ui/index.html#/)

## 🚀 Funcionalidades Implementadas

- **🔐 Níveis de Permissões**: Integrados com seleção de usuário para determinadas rotas.
- **🗃️ Manipulação de Tabelas**: Auto-criação de tabelas pelo Spring Boot JPA.
- **📦 PreLoaderDataBase**: Carregamento automático de dados nas tabelas, como usuários, produtos, marcas e tipos de vendas.
- **🔍 Verificações de Dados**:
  - Retorno automático de produto excluído para a venda.
  - Atributo para desativação de usuário ao invés de exclusão.
- **👤 Autenticação (Auth)**:
  - Criação de novos usuários.
  - Retorno de dados via `authinfo` para uso em sistemas reais.
- **🛠️ Manipulação de ResponsePagination e Generics no código.

## 🖥️ Frontend

No frontend, foram demonstrados a autenticação e outros recursos avançados.

---

É um prazer receber essa oportunidade da VR Software de poder exercer meu conhecimento em um teste magnífico como esse.

---
## ▶️ Executando o Projeto

Para executar o projeto, é necessário ter o Docker e o Docker Compose instalados na sua máquina.

Executar o Docker
1. Clone o repositório:

```bash
git clone https://github.com/RodolfoBrandaoOficial/CoreMasterMarket.git

```
Navegue até o diretório do projeto:
```bash
cd CoreMasterMarket/docker
```
Execute o comando Docker Compose para iniciar os contêineres:

```bash
docker-compose up --build
```

## 🏃‍♂️ Como Rodar o Projeto

### 📋 Pré-requisitos

- JDK 17+
- Gradle Repository
- PostgreSQL
- [Postman Doc](https://documenter.getpostman.com/view/17799534/2sA3kdAxud)
- Docker

### 🛠️ Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL:
```sql
   CREATE DATABASE vrsoftware_db;
```

Configure o application.properties para conectar ao banco de dados:
properties
Copiar código
spring.datasource.url=jdbc:postgresql://localhost:5432/vrsoftware_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
🚀 Rodando a Aplicação
Clone o repositório:


Copiar código
```sh
git clone https://github.com/seu_usuario/seu_repositorio.git
cd seu_repositorio/Application-Backend
```
Compile e rode a aplicação:

sh
Copiar código
```sh
mvn clean install
mvn spring-boot:run
```
🌐 Acessando a Documentação Swagger
Abra o navegador e acesse:
http://localhost:8081/swagger-ui/index.html#/
ou Postman
https://documenter.getpostman.com/view/17799534/2sA3kdAxud

🔍 Executando Testes
Para rodar os testes unitários e de integração, use o seguinte comando:

```sh
mvn test
```

Se tiver alguma dúvida ou encontrar problemas, por favor, abra uma issue no repositório.

Agradeço pela oportunidade e espero que este projeto atenda às expectativas da VR Software.






