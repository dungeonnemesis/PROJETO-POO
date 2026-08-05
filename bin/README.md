# Sistema Escolar

Projeto acadêmico desenvolvido em Java com Spring Boot e persistência JPA em PostgreSQL.

Atualmente, o projeto contém as entidades `Pessoa`, `Disciplina` e `Turma`. Uma turma possui vínculo com uma disciplina por meio de uma associação JPA `ManyToOne`.

## Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven Wrapper
- JUnit 5

## O que precisa ser instalado

### 1. Java 17

Instale um JDK 17, como Eclipse Temurin ou Oracle JDK.

Depois da instalação, confirme no terminal:

```powershell
java -version
```

O resultado deve indicar a versão 17. Caso o comando não seja reconhecido, configure as variáveis de ambiente `JAVA_HOME` e `Path`.

Exemplo de configuração temporária no PowerShell:

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

O caminho deve ser ajustado conforme o local onde o JDK foi instalado.

### 2. PostgreSQL

Instale o PostgreSQL. O pgAdmin é opcional, mas facilita a criação e visualização do banco.

Não é necessário instalar o Maven separadamente, pois o projeto possui o Maven Wrapper (`mvnw` e `mvnw.cmd`).

## Criação do banco de dados

A configuração atual espera os seguintes dados:

| Propriedade | Valor |
| --- | --- |
| Servidor | `localhost` |
| Porta | `5432` |
| Banco | `escola` |
| Usuário | `igor` |
| Senha | `1234` |

Execute os comandos abaixo usando o usuário administrador do PostgreSQL:

```sql
CREATE USER igor WITH PASSWORD '1234';
CREATE DATABASE escola OWNER igor;
```

Se o usuário `igor` já existir, execute somente a criação do banco:

```sql
CREATE DATABASE escola OWNER igor;
```

Esses valores são destinados apenas ao ambiente local de desenvolvimento. A configuração pode ser alterada no arquivo `src/main/resources/application.yml`.

## Como executar

Abra o PowerShell na raiz do projeto e execute:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS, use:

```bash
./mvnw spring-boot:run
```

A aplicação será iniciada na porta `8080`. Neste estágio ainda não existem controladores REST, portanto não há uma página ou endpoint para acessar pelo navegador. O início sem erros confirma que a aplicação conectou ao PostgreSQL e carregou o contexto do Spring.

O Hibernate está configurado com `ddl-auto: create`. Por isso, as tabelas são recriadas sempre que a aplicação inicia, apagando dados anteriores. Essa configuração é adequada somente durante o desenvolvimento inicial.

## Como executar os testes

O PostgreSQL deve estar iniciado e o banco `escola` deve existir. Na raiz do projeto, execute:

```powershell
.\mvnw.cmd test
```

Para executar somente um teste:

```powershell
.\mvnw.cmd -Dtest=InterfaceRepositorioTurmaTest test
```

Os testes de repositório verificam:

- persistência e consulta de disciplinas;
- persistência e consulta de pessoas por CPF e e-mail;
- persistência de uma turma vinculada a uma disciplina.

Cada teste utiliza uma transação com rollback, evitando que os registros criados pelo teste permaneçam no banco.

## Estrutura principal

```text
src/
├── main/
│   ├── java/br/edu/ufape/poo/escola/
│   │   ├── EscolaApplication.java
│   │   ├── dados/
│   │   │   ├── InterfaceRepositorioDisciplina.java
│   │   │   ├── InterfaceRepositorioPessoa.java
│   │   │   └── InterfaceRepositorioTurma.java
│   │   └── negocio/basica/
│   │       ├── Disciplina.java
│   │       ├── Pessoa.java
│   │       └── Turma.java
│   └── resources/
│       ├── application.properties
│       └── application.yml
└── test/java/br/edu/ufape/poo/escola/dados/
    ├── InterfaceRepositorioDisciplinaTest.java
    ├── InterfaceRepositorioPessoaTest.java
    └── InterfaceRepositorioTurmaTest.java
```

O diagrama de classes está disponível no arquivo `diagrama-classes.png`, na raiz do projeto.

## Problemas comuns

### `JAVA_HOME environment variable is not defined correctly`

Configure `JAVA_HOME` apontando para a pasta do JDK 17, e não para a pasta `bin`.

### `Connection refused` ou falha ao conectar ao PostgreSQL

Verifique se:

- o serviço do PostgreSQL está iniciado;
- o PostgreSQL está usando a porta `5432`;
- o banco `escola` foi criado;
- o usuário e a senha correspondem ao `application.yml`.

### Porta `8080` em uso

Altere a propriedade `server.port` no arquivo `application.yml` ou encerre o programa que já está utilizando a porta.
