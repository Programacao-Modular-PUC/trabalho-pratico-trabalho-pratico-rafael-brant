# Sistema de Hospedagem — Maraú, BA

Trabalho Prático da disciplina **Programação Modular** — Engenharia de Software, PUC Minas.

Sistema de gerenciamento de hospedagens residenciais para a Península de Maraú, desenvolvido com Spring Boot, API REST, JPA e frontend web integrado.

---

## Sumário

- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação dos Pré-requisitos](#instalação-dos-pré-requisitos)
- [Clonando o Projeto](#clonando-o-projeto)
- [Como Executar](#como-executar)
  - [Modo Desenvolvimento (H2 — sem MySQL)](#modo-desenvolvimento-h2--sem-mysql)
  - [Modo Produção (MySQL)](#modo-produção-mysql)
- [Acessando o Frontend](#acessando-o-frontend)
- [Rodando os Testes](#rodando-os-testes)
- [Endpoints da API](#endpoints-da-api)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Regras de Negócio](#regras-de-negócio)

---

## Tecnologias

| Tecnologia       | Versão   |
|------------------|----------|
| Java             | 17       |
| Spring Boot      | 3.2.5    |
| Spring Data JPA  | —        |
| H2 Database      | (dev)    |
| MySQL            | 8+ (prod)|
| Apache Maven     | 3.8+     |
| Bootstrap        | 5.3      |

---

## Pré-requisitos

Para executar o projeto você precisa ter instalado na sua máquina:

- **Java Development Kit (JDK) 17** — obrigatório
- **Apache Maven 3.8+** — obrigatório
- **MySQL 8+** — apenas para o modo produção (opcional para desenvolvimento)

---

## Instalação dos Pré-requisitos

### 1. Java 17 (JDK)

**Windows — via winget:**

```bash
winget install Microsoft.OpenJDK.17
```

Após a instalação, verifique:

```bash
java -version
```

A saída esperada é algo como:

```
openjdk version "17.x.x" ...
```

> Caso o comando não seja reconhecido, reinicie o terminal ou adicione o caminho do JDK à variável de ambiente `JAVA_HOME` e ao `PATH`.

---

### 2. Apache Maven

**Windows — instalação manual:**

1. Acesse [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi) e baixe o arquivo `apache-maven-X.X.X-bin.zip`
2. Extraia para uma pasta de sua preferência (ex.: `C:\tools\maven\`)
3. Adicione a pasta `bin` do Maven ao `PATH` do sistema:
   - Painel de Controle → Sistema → Variáveis de Ambiente
   - Em **Variáveis do Sistema**, edite `Path` e adicione: `C:\tools\maven\apache-maven-X.X.X\bin`

Verifique a instalação:

```bash
mvn -version
```

A saída esperada:

```
Apache Maven 3.x.x ...
Java version: 17.x.x ...
```

---

### 3. MySQL (apenas para modo produção)

**Windows — via winget:**

```bash
winget install MySQL.MySQL
```

Após instalar, inicie o serviço e crie o banco de dados:

```sql
CREATE DATABASE hospedagem_marau;
```

---

## Clonando o Projeto

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

---

## Como Executar

O projeto possui dois perfis de execução:

| Perfil | Banco de dados | Quando usar |
|--------|----------------|-------------|
| `dev`  | H2 em memória  | Desenvolvimento e testes manuais — não precisa de MySQL |
| padrão | MySQL          | Produção |

---

### Modo Desenvolvimento (H2 — sem MySQL)

Este é o modo mais simples. O banco de dados é criado automaticamente em memória ao iniciar e descartado ao encerrar. Não é necessário instalar nem configurar o MySQL.

**Execute o comando abaixo na raiz do projeto:**

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Aguarde a mensagem de confirmação no terminal:

```
Started HospedagemApplication in X.XXX seconds
```

A aplicação estará disponível em: **http://localhost:8080**

> **Atenção:** Como o banco H2 é em memória, todos os dados são perdidos ao reiniciar a aplicação. Para persistência entre sessões, use o modo produção com MySQL.

**Console do banco H2 (opcional):**

Durante o modo `dev`, é possível inspecionar o banco diretamente pelo navegador em:

```
http://localhost:8080/h2-console
```

Configurações de acesso:
- **JDBC URL:** `jdbc:h2:mem:hospedagem_dev`
- **Usuário:** `sa`
- **Senha:** *(deixar em branco)*

---

### Modo Produção (MySQL)

**1.** Certifique-se de que o MySQL está rodando e o banco `hospedagem_marau` foi criado:

```sql
CREATE DATABASE hospedagem_marau;
```

**2.** Abra o arquivo `src/main/resources/application.properties` e ajuste as credenciais:

```properties
spring.datasource.username=root
spring.datasource.password=sua_senha
```

**3.** Execute a aplicação sem especificar perfil:

```bash
mvn spring-boot:run
```

O Hibernate criará automaticamente as tabelas no banco de dados na primeira execução.

---

## Acessando o Frontend

Com a aplicação rodando, acesse pelo navegador:

| Página       | URL                                      |
|--------------|------------------------------------------|
| Dashboard    | http://localhost:8080                    |
| Clientes     | http://localhost:8080/clientes.html      |
| Quartos      | http://localhost:8080/quartos.html       |
| Aluguéis     | http://localhost:8080/alugueis.html      |
| Residências  | http://localhost:8080/residencias.html   |

### Fluxo básico de uso

Para registrar um aluguel, siga esta ordem:

1. **Residências** → Cadastre uma residência (endereço da propriedade)
2. **Quartos** → Cadastre um quarto (Individual, Casal ou Família)
3. **Clientes** → Cadastre o hóspede
4. **Aluguéis** → Crie o aluguel selecionando cliente, quarto e período

---

## Rodando os Testes

Os testes utilizam banco H2 em memória e **não exigem MySQL nem servidor rodando**.

```bash
mvn test
```

O relatório de testes é gerado em:

```
src/test/resources/relatorio/test-report.txt
```

Para executar e já exibir o relatório no terminal:

```bash
mvn test -Dsurefire.failIfNoSpecifiedTests=false
```

---

## Endpoints da API

### Clientes

| Método | Endpoint           | Descrição               |
|--------|--------------------|-------------------------|
| GET    | `/clientes`        | Listar todos            |
| GET    | `/clientes/{id}`   | Buscar por ID           |
| POST   | `/clientes`        | Cadastrar               |
| PUT    | `/clientes/{id}`   | Atualizar               |
| DELETE | `/clientes/{id}`   | Excluir                 |

### Quartos

| Método | Endpoint                  | Descrição                    |
|--------|---------------------------|------------------------------|
| GET    | `/quartos`                | Listar todos                 |
| GET    | `/quartos/{id}`           | Buscar por ID                |
| POST   | `/quartos/individual`     | Cadastrar quarto individual  |
| POST   | `/quartos/casal`          | Cadastrar quarto casal       |
| POST   | `/quartos/familia`        | Cadastrar quarto família     |
| DELETE | `/quartos/{id}`           | Excluir                      |

### Aluguéis

| Método | Endpoint                      | Descrição                         |
|--------|-------------------------------|-----------------------------------|
| GET    | `/alugueis`                   | Listar todos                      |
| GET    | `/alugueis/{id}`              | Buscar por ID                     |
| GET    | `/alugueis/{id}/recibo`       | Gerar recibo de hospedagem        |
| GET    | `/alugueis/tipo?tipoQuarto=X` | Filtrar por tipo de quarto        |
| GET    | `/alugueis/cliente/{id}`      | Histórico por cliente             |
| POST   | `/alugueis`                   | Criar aluguel                     |
| PUT    | `/alugueis/{id}/cancelar`     | Cancelar aluguel                  |
| DELETE | `/alugueis/{id}`              | Excluir                           |

### Residências

| Método | Endpoint              | Descrição     |
|--------|-----------------------|---------------|
| GET    | `/residencias`        | Listar todas  |
| GET    | `/residencias/{id}`   | Buscar por ID |
| POST   | `/residencias`        | Cadastrar     |
| PUT    | `/residencias/{id}`   | Atualizar     |
| DELETE | `/residencias/{id}`   | Excluir       |

---

## Arquitetura do Projeto

```
src/
├── main/
│   ├── java/br/pucminas/hospedagem/
│   │   ├── controller/         # Endpoints REST
│   │   ├── service/            # Regras de negócio
│   │   ├── repository/         # Acesso a dados (JpaRepository)
│   │   ├── model/              # Entidades JPA
│   │   │   ├── Quarto.java         (abstrata)
│   │   │   ├── QuartoIndividual.java
│   │   │   ├── QuartoCasal.java
│   │   │   ├── QuartoFamilia.java
│   │   │   ├── Cliente.java
│   │   │   ├── Aluguel.java
│   │   │   ├── Pagamento.java
│   │   │   └── Residencia.java
│   │   ├── dto/                # Objetos de transferência (AluguelRequest)
│   │   └── exception/          # Exceções customizadas e GlobalExceptionHandler
│   └── resources/
│       ├── application.properties          # Configuração produção (MySQL)
│       ├── application-dev.properties      # Configuração dev (H2)
│       └── static/                         # Frontend web
│           ├── index.html
│           ├── clientes.html
│           ├── quartos.html
│           ├── alugueis.html
│           ├── residencias.html
│           └── style.css
└── test/
    └── java/br/pucminas/hospedagem/
        └── service/            # Testes unitários (JUnit 5 + Mockito)
```

---

## Regras de Negócio

- **Cálculo de diárias:** baseado na diferença de datas de calendário. Saída após 12h adiciona uma diária extra.
- **Disponibilidade:** um quarto não pode ter dois aluguéis ativos com períodos sobrepostos.
- **Quarto Individual:** capacidade = número de camas. Cada cama extra adiciona R$30 ao valor da diária.
- **Quarto Casal:** capacidade de 2 hóspedes (3 com berço). Adicional por tipo de cama: Casal +R$20, Queen +R$50, King +R$80. Berço +R$30.
- **Quarto Família:** capacidade = solteiro + casal×2 + queen/king×2. Desconto progressivo de grupo: 5% (4-5 hóspedes), 10% (6-7) e 15% (8+).
- **Pagamento:** gerado automaticamente ao criar o aluguel, com o valor total calculado.
- **Exceções:** datas inválidas, quarto indisponível e capacidade excedida retornam mensagens de erro ao frontend.

---

**Aluno:** Rafael Brant  
**Disciplina:** Programação Modular — PUC Minas
