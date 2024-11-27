<h1 align="center"> Sistema de Gerenciamento de Insumos Hospitalares </h1>


# Visão Geral

Este projeto é uma API robusta, desenvolvida com **Spring Boot** e **PostgreSQL**, para gerenciar o controle de insumos em um hospital. A aplicação oferece recursos como:

- **Acompanhamento de ordens de compra**.
- **Gerenciamento de usuários e fornecedores**.
- **Controle de estoque de produtos hospitalares**.

O sistema permite registrar insumos consumidos e criar ordens de compra com base nos dados cadastrados. Todas as operações requerem autenticação do usuário via **JWT**, garantindo segurança e controle nos processos. Além disso, a criação de ordens exige o ID do fornecedor, assegurando rastreabilidade e organização no gerenciamento dos insumos.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação.
- **Spring Boot**: Framework para desenvolvimento rápido de aplicações robustas.
- **JPA (Java Persistence API)**: Mapeamento entre objetos Java e banco de dados relacional.
- **Lombok**: Reduz o código boilerplate, como getters e setters.
- **Spring Security**: Autenticação e controle de acesso.
- **JWT (JSON Web Tokens)**: Autenticação segura baseada em tokens.
- **BCrypt**: Algoritmo de hashing para proteger senhas.
- **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
- **Docker**: Containerização para facilitar o deploy.
- **Docker Compose**: Gerenciamento de containers, usado para rodar a imagem do PostgreSQL.
- **Git Flow**: Metodologia para controle de versão em equipe.
- **DBeaver**: Ferramenta universal de gerenciamento de banco de dados.
- **Swagger**: Documentação interativa da API.
- **Fork**: Ferramenta para versionamento de código.
- **Insomnia**: Cliente para desenvolvimento e teste de APIs REST.
# Funcionalidades Principais

#### **CRUD de Insumos (Produtos)**
- **Criar Insumo**: Adiciona novos insumos ao sistema, registrando suas informações no banco de dados.
- **Listar Insumos**: Exibe todos os insumos cadastrados no sistema.
- **Atualizar Insumo**: Permite modificar os dados de um insumo existente.
- **Deletar Insumo**: Remove insumos cadastrados no banco de dados.
- **Filtrar Insumo por data de criação**: Filtrar insumos registrados no sistema por data de criação.

#### **Gerenciamento de Ordens de Compra**
- **Criar Ordem de Compra**: Registra uma nova ordem associada a um fornecedor e aos produtos cadastrados.
- **Listar Ordens de Compra**: Exibe todas as ordens registradas, facilitando o acompanhamento.
- **Atualizar Ordem de Compra**: Permite ajustes nas informações de uma ordem de compra existente.
- **Deletar Ordem de Compra**: Remove ordens de compra registradas no sistema.
- **Filtrar Ordem de Compra por data de criação**: Filtrar ordens de compra registradas no sistema por data de criação.

#### **Gerenciamento de Usuários**
- **Criar Usuário**: Registra um novo usuário no sistema.
- **Logar Usuário**: Autentica um usuário, permitindo acesso às funcionalidades da API.
- **Listar Usuários**: Exibe os usuários cadastrados.
- **Atualizar Usuário**: Permite modificar as informações de um usuário existente.
- **Deletar Usuário**: Remove um usuário do sistema.
- **Logar Usuário**: loga um usuário no sistema com authenticação JWT.
- **Filtrar usuários por data de criação**: Filtrar usuários registrados no sistema por data de criação.

#### **Gerenciamento de Fornecedor**
- **Criar Fornecedor**: Registra um novo fornecedor no sistema.


# Instruções de Instalação

``` 1. Clone o repositório> git clone (https://github.com/euthiagomota/Sistema-Controle-Insumos.git)```

``` 2. Navegue até o diretório do projeto> cd <nome-do-diretorio>```

``` 3. Execute o Docker Compose para iniciar os containers> docker-compose up```

``` 4. Acesse a documentação da API via Swagger em> http://localhost:8080/swagger-ui.html```

# Desenvolvedores

* Thiago José Pereira da Mota - 01587816
* Laryssa Rayanne Souza Martins - 01612424
* Rafael Ferreira dos anjos - 01579531
* Elizeu Leoncio Ferreira Junior - 01576238 
* Matheus de Oliveira Lins Mendes - 01618966
* Italo Jocemar Fernandes de Lira - 01629621
* Sara Cristina Brasileiro Sales - 01649079
