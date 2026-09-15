# biblioteca

API REST para gestão de acervo de biblioteca, construída em Java com Spring Boot. O projeto cobre o núcleo de um sistema de biblioteca: cadastro de livros, usuários e controle de empréstimos, com persistência relacional e autenticação via Spring Security.

> Status: em desenvolvimento inicial. O esqueleto da aplicação e as dependências já estão configurados; os endpoints e regras de negócio estão sendo implementados de forma incremental.

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 25 |
| Framework | Spring Boot 4.1.1 |
| Persistência | Spring Data JPA + Spring Data JDBC |
| Segurança | Spring Security |
| Banco (dev) | H2 (em memória, com console habilitado) |
| Banco (prod) | MySQL |
| Build | Maven (via wrapper `mvnw`) |
| Boilerplate | Lombok |

## Arquitetura

O projeto segue uma arquitetura em camadas, padrão para APIs Spring Boot, separando a exposição HTTP da regra de negócio e do acesso a dados. A ideia é manter os controllers finos, a lógica concentrada nos services e o acesso ao banco isolado nos repositories, para que trocar H2 por MySQL (ou incluir um novo tipo de persistência) não afete o restante da aplicação.

```
                         ┌──────────────────────────┐
                         │        Cliente HTTP       │
                         │  (Postman, front-end...)  │
                         └────────────┬───────────────┘
                                      │ JSON / REST
                                      ▼
                         ┌──────────────────────────┐
                         │     Security Filter Chain  │
                         │   (autenticação/sessão)    │
                         └────────────┬───────────────┘
                                      ▼
                         ┌──────────────────────────┐
                         │        Controllers         │
                         │  (mapeamento de rotas, DTO) │
                         └────────────┬───────────────┘
                                      ▼
                         ┌──────────────────────────┐
                         │          Services           │
                         │   (regras de negócio)        │
                         └────────────┬───────────────┘
                                      ▼
                         ┌──────────────────────────┐
                         │        Repositories          │
                         │ (Spring Data JPA / JDBC)      │
                         └────────────┬───────────────┘
                                      ▼
                         ┌──────────────────────────┐
                         │      Banco de dados           │
                         │   H2 (dev) · MySQL (prod)      │
                         └──────────────────────────┘
```

Domínio previsto para as próximas etapas:

- **Livro** — catálogo, disponibilidade, autor, ISBN.
- **Usuário** — cadastro e autenticação de quem usa a biblioteca.
- **Empréstimo** — vincula livro e usuário, com datas de retirada e devolução.

## Estrutura de pastas

```
biblioteca/
├── src/
│   ├── main/
│   │   ├── java/com/api/book/
│   │   │   └── BookApplication.java   # classe de entrada da aplicação
│   │   └── resources/                 # configurações (application.properties, não versionado)
│   └── test/
│       └── java/com/api/book/
│           └── BookApplicationTests.java
├── .mvn/wrapper/
├── mvnw / mvnw.cmd
└── pom.xml
```

## Executando localmente

### Pré-requisitos

- Java 25 (JDK)
- Não é necessário ter o Maven instalado — o projeto usa o Maven Wrapper

### Passos

```bash
git clone https://github.com/soareslevi566-lgtm/biblioteca.git
cd biblioteca

# Linux/macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

### Configuração do banco

O arquivo `src/main/resources/application.properties` não é versionado (está no `.gitignore`), então cada ambiente precisa do seu próprio. Para desenvolvimento com H2, um ponto de partida:

```properties
spring.datasource.url=jdbc:h2:mem:biblioteca
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=update
```

Para produção com MySQL, troque a `datasource.url` e as credenciais pelo seu servidor:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

### Testes

```bash
./mvnw test
```

## Roadmap

- [x] Estrutura do projeto e dependências (Spring Web, Data JPA, Data JDBC, Security)
- [ ] Modelagem das entidades (Livro, Usuário, Empréstimo)
- [ ] Endpoints CRUD de livros e usuários
- [ ] Regras de empréstimo e devolução
- [ ] Autenticação e autorização por perfil de usuário
- [ ] Documentação da API (OpenAPI/Swagger)

## Contribuindo

Sugestões e pull requests são bem-vindos. Para mudanças maiores, abra uma issue antes descrevendo o que pretende alterar.

## Licença

Ainda não definida.
