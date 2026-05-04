## Cartões CRC

Os cartões a seguir detalham as responsabilidades de cada entidade e as suas interações para cumprir os requisitos de negócio.

### Classe: Residência
| Responsabilidades | Colaborações |
| :--- | :--- |
| Conhecer o seu endereço, número, bairro, CEP, telefone e e-mail | Quarto |
| Gerenciar a sua lista de quartos disponíveis para aluguel | Aluguel |
| Manter e disponibilizar o histórico de aluguéis realizados | |

### Classe: Quarto
| Responsabilidades | Colaborações |
| :--- | :--- |
| Conhecer o seu tipo (individual ou casal) e valor base da diária | |
| Conhecer a presença de itens adicionais como ar-condicionado ou hidromassagem | |
| Calcular o valor final da sua diária somando os adicionais ao valor base | |
| Informar a sua disponibilidade para um determinado período | |

### Classe: Cliente
| Responsabilidades | Colaborações |
| :--- | :--- |
| Conhecer o seu nome, CPF e endereço completo | |
| Conhecer os seus dados de contato (telefone e e-mail) | |

### Classe: Aluguel
| Responsabilidades | Colaborações |
| :--- | :--- |
| Conhecer a residência, o quarto e o cliente envolvidos na hospedagem | Residência |
| Conhecer as datas/horários de entrada e saída | Quarto |
| Calcular a quantidade de diárias aplicando a regra de virada das 12h | Cliente |
| Calcular o valor total a pagar e gerar o recibo formatado para impressão | Pagamento |

### Classe: Pagamento
| Responsabilidades | Colaborações |
| :--- | :--- |
| Conhecer o valor total da transação associada ao aluguel | Aluguel |
| Registrar a data de pagamento e o status do processamento | |
