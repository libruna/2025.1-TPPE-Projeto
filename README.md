<h2 align="center">Smart Manage: Sistema para Gerenciamento de Vendas</h2>
<br>

## Sumário
- [Visão geral](#visão-geral)
- [Documentação técnica](#documentação-técnica)
- [Definições de variáveis de ambiente](#como-executar-o-projeto)
- [Como executar o projeto](#como-executar-o-projeto)

<br>

## Visão geral
Este repositório contém o desenvolvimento do projeto da disciplina Técnicas de Programação em Plataformas Emergentes do 1º semestre de 2025.

O projeto consiste na reestruturação e evolução de um sistema previamente desenvolvido na disciplina de Orientação a Objetos, com foco na adoção de novas tecnologias e na aplicação de boas práticas de engenharia de software.

- O código-fonte original está disponível [neste repositório](https://gitlab.com/liander/ep1).
- A proposta detalhada do projeto pode ser consultada [aqui](https://gitlab.com/oofga/eps/eps_2019_2/ep1).

<br>

## Documentação técnica

A documentação completa com diagrama de classes, diagrama físico, protótipos de alta fidelidade e demais detalhes pode ser encontrada [aqui](./docs/documentation.md).

<br>

## Definições de variáveis de ambiente

| Variável | Descrição | Tipo | Exemplo |
| -------- | --------- | ---- | ------ |
| `POSTGRES_DB` | Nome do banco de dados utilizado pela aplicação. | string | `database` |
| `POSTGRES_USER` | Usuário do banco de dados. | string | `user` |
| `POSTGRES_PASSWORD` | Senha do usuário do banco de dados. | string | `pass` |
| `POSTGRES_URL` | URL de conexão JDBC com o banco PostgreSQL. | string | `jdbc:postgresql://localhost:5432/database` |

<br>

## Como executar o projeto

Esse projeto pode ser executado localmente com ou sem o uso de Docker. 

> ⚠️ Certifique-se de editar o arquivo **.env** na pasta "code" da aplicação com valores válidos para as variáveis de ambiente descritas neste documento. Cada linha do arquivo deve seguir o formato `CHAVE=VALOR`.


<details>
  <summary><b>Com Docker</b></summary>

### Pré-requisitos

Cerifique-se de que possui o Docker instalado.

### Executando a aplicação

1. Usando o terminal, navegar até o diretório "code" da aplicação.

2. Execute a aplicação via docker compose com o seguinte comando:

```shell
    docker compose up --build
```
Ao executar esse comando,  containers são criados: Banco de dados PostgreSQL e Backend

Durante a inicialização, as migrações do banco de dados são executadas automaticamente via Flyway, criando o esquema completo das tabelas. Além disso, um conjunto de dados iniciais é inserido no banco.

3. A aplicação estará disponível no endereço local [`https://127.0.0.1:8080`](http://127.0.0.1:8080)

4. A aplicação pode ser encerrada utilizando o seguinte comando:

```shell
    docker compose down
```
</details>

<details>
  <summary><b>Localmente</b></summary>

### Pré-requisitos

Cerifique-se de que possui instalado:

- Java 21
- PostgreSQL 15
- Maven 3.9

### Configuração do banco de dados

1. Instale e configure o PostgreSQL local
2. Crie um banco de dados para a aplicação
3. Configure as variáveis de ambiente no arquivo `.env` com os dados do seu banco local
4. **Recomendação**: Instale uma IDE de banco como [DBeaver](https://dbeaver.io/) ou [pgAdmin](https://www.pgadmin.org/) para facilitar a visualização e gerenciamento dos dados.

### Executando o Backend

1. Navegue até o diretório do backend:
```shell
cd code/smart-manage-api
```

2. Inicie a aplicação Spring Boot:
```shell
mvn spring-boot:run
```

O backend estará disponível em `http://localhost:8080`

3. Executar lint (checkstyle):
```shell
mvn checkstyle:check
```

4. Executar os testes:
```shell
mvn test
```

5. A aplicação pode ser encerrada utilizando `Ctrl+C` no terminal onde a aplicação está executando.

</details>

<br>

A documentação OpenAPI/Swagger estará disponível em [`http://localhost:8080/smart-manage/swagger-ui/index.html`](http://localhost:8080/smart-manage/swagger-ui/index.html).