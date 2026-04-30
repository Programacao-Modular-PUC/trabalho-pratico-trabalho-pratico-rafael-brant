# Sistema de Hospedagem

Trabalho Prático da disciplina **Programação Modular** — Engenharia de Software, PUC Minas.

Sistema de informação modular com API REST para gerenciamento de hospedagens residenciais, desenvolvido com Spring Boot e arquitetura em camadas.

---

## Funcionalidades

- Gerenciamento de residências e quartos.
- Cadastro e consulta de clientes.
- Realização de reservas e aluguéis com verificação de disponibilidade.
- Cálculo automático de diárias (regra das 12h).
- Emissão de formulário de aluguel.
- Histórico de hospedagens por residência.
- Geração de pagamento associado ao aluguel.

## Regras de Negócio

- Diárias iniciam às 12h; entrada após 12h conta como diária completa.
- Saída após 12h adiciona uma nova diária.
- Valor da diária = valor base + adicionais (ar condicionado e/ou hidromassagem).
- Um quarto não pode ser alugado se já estiver ocupado no período solicitado.
- Todo aluguel gera um pagamento associado.

## Tecnologias

| Tecnologia       | Versão  |
|------------------|---------|
| Java             | 17      |
| Spring Boot      | 3.2.5   |
| Spring Data JPA  | —       |
| MySQL            | 8+      |

## Arquitetura

```
src/main/java/br/pucminas/hospedagem/
├── controller/     # Endpoints REST (@RestController)
├── service/        # Regras de negócio (@Service)
├── repository/     # Acesso a dados (@Repository / JpaRepository)
├── model/          # Entidades JPA (@Entity)
├── dto/            # Objetos de transferência de dados
├── exception/      # Exceções customizadas e handler global
└── config/         # Configurações (CORS, beans, etc.)
```

## Modelos de Domínio

```
Residencia
  ├── endereco, numero, bairro, cep, telefone, email
  └── quartos: List<Quarto>

Quarto
  ├── tipo: SOLTEIRO | CASAL
  ├── valorBase: BigDecimal
  ├── possuiArCondicionado: boolean
  └── possuiBanheiraHidromassagem: boolean

Cliente
  └── nome, cpf, endereco, telefone, email

Aluguel
  ├── residencia, quarto, cliente
  ├── dataEntrada, dataSaida
  ├── quantidadeDiarias: int
  └── valorFinal: BigDecimal

Pagamento
  └── aluguel, valor, dataPagamento
```

## Endpoints Planejados

| Método | Endpoint                              | Descrição                        |
|--------|---------------------------------------|----------------------------------|
| POST   | `/residencias`                        | Cadastrar residência             |
| GET    | `/residencias/{id}`                   | Buscar residência                |
| GET    | `/residencias/{id}/historico`         | Histórico de aluguéis            |
| POST   | `/residencias/{id}/quartos`           | Adicionar quarto                 |
| GET    | `/quartos/{id}/disponibilidade`       | Verificar disponibilidade        |
| POST   | `/clientes`                           | Cadastrar cliente                |
| POST   | `/alugueis`                           | Realizar aluguel/reserva         |
| GET    | `/alugueis/{id}/formulario`           | Emitir formulário de aluguel     |

## Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+
- MySQL 8+

### Configuração do banco de dados

```sql
CREATE DATABASE hospedagem_marau;
```

Ajuste as credenciais em `src/main/resources/application.properties`:

```properties
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Rodando a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

### Rodando os testes

```bash
./mvnw test
```

Os testes utilizam banco H2 em memória — não é necessário ter o MySQL rodando.

---

**Aluno:** Rafael Brant  
**Disciplina:** Programação Modular — PUC Minas
