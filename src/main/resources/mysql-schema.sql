CREATE DATABASE IF NOT EXISTS hospedagem_marau
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE hospedagem_marau;

-- Cliente cadastra e autentica os usuários do sistema.
CREATE TABLE IF NOT EXISTS cliente (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255),
  cpf VARCHAR(20),
  endereco VARCHAR(255),
  telefone VARCHAR(20),
  email VARCHAR(255),
  senha VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Pagamento associado ao aluguel.
CREATE TABLE IF NOT EXISTS pagamento (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  valor DOUBLE,
  data_pagamento DATETIME,
  processado BOOLEAN
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Quarto pode ser individual ou casal com herança em uma só tabela.
CREATE TABLE IF NOT EXISTS quarto (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  valor_base DECIMAL(10,2),
  possui_ar BOOLEAN,
  possui_hidro BOOLEAN,
  tipo_quarto VARCHAR(31)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Residência que contém quartos e histórico de aluguéis.
CREATE TABLE IF NOT EXISTS residencia (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  endereco VARCHAR(255),
  numero VARCHAR(50),
  bairro VARCHAR(255),
  cep VARCHAR(20),
  telefone VARCHAR(20),
  email VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Aluguel com vínculo a cliente, quarto e pagamento.
CREATE TABLE IF NOT EXISTS aluguel (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  data_hora_entrada DATETIME,
  data_hora_saida DATETIME,
  qtd_diarias INT,
  qtd_hospedes INT,
  valor_total DECIMAL(10,2),
  status VARCHAR(20),
  quarto_id BIGINT,
  cliente_id BIGINT,
  pagamento_id BIGINT,
  CONSTRAINT fk_aluguel_quarto FOREIGN KEY (quarto_id) REFERENCES quarto(id),
  CONSTRAINT fk_aluguel_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id),
  CONSTRAINT fk_aluguel_pagamento FOREIGN KEY (pagamento_id) REFERENCES pagamento(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Reserva futura para controle de disponibilidade.
CREATE TABLE IF NOT EXISTS reserva (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  residencia_id BIGINT,
  quarto_id BIGINT,
  cliente_id BIGINT,
  data_hora_entrada DATETIME,
  data_hora_saida DATETIME,
  qtd_hospedes INT,
  status VARCHAR(20),
  criado_em DATETIME DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_reserva_residencia FOREIGN KEY (residencia_id) REFERENCES residencia(id),
  CONSTRAINT fk_reserva_quarto FOREIGN KEY (quarto_id) REFERENCES quarto(id),
  CONSTRAINT fk_reserva_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Relacionamento entre residência e quartos disponíveis.
CREATE TABLE IF NOT EXISTS residencia_quartos (
  residencia_id BIGINT NOT NULL,
  quartos_id BIGINT NOT NULL,
  CONSTRAINT fk_residencia_quartos_residencia FOREIGN KEY (residencia_id) REFERENCES residencia(id),
  CONSTRAINT fk_residencia_quartos_quarto FOREIGN KEY (quartos_id) REFERENCES quarto(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Histórico de aluguéis por residência.
CREATE TABLE IF NOT EXISTS residencia_historico_alugueis (
  residencia_id BIGINT NOT NULL,
  historico_alugueis_id BIGINT NOT NULL,
  CONSTRAINT fk_residencia_historico_residencia FOREIGN KEY (residencia_id) REFERENCES residencia(id),
  CONSTRAINT fk_residencia_historico_aluguel FOREIGN KEY (historico_alugueis_id) REFERENCES aluguel(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
