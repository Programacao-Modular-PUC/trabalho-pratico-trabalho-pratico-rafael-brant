package br.pucminas.hospedagem.model;

import br.pucminas.hospedagem.exception.DataInvalidaException;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Entity
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private int qtdDiarias;
    private int qtdHospedes;
    private double valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusAluguel status;

    @ManyToOne
    private Quarto quarto;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Residencia residencia;

    @OneToOne(cascade = CascadeType.ALL)
    private Pagamento pagamento;

    public Aluguel() {}

    public Aluguel(LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, int qtdHospedes,
                   StatusAluguel status, Quarto quarto, Cliente cliente) {
        this.dataHoraEntrada = dataHoraEntrada;
        this.dataHoraSaida = dataHoraSaida;
        this.qtdHospedes = qtdHospedes;
        this.status = status;
        this.quarto = quarto;
        this.cliente = cliente;
    }

    // Regra: diárias iniciam às 12h.
    // Entrada após 12h → conta como diária completa.
    // Saída após 12h → adiciona nova diária.
    public int calcularDiarias() {
        validarDatas();
        int dias = (int) ChronoUnit.DAYS.between(
                dataHoraEntrada.toLocalDate(),
                dataHoraSaida.toLocalDate());
        if (dataHoraSaida.getHour() >= 12) {
            dias++;
        }
        return Math.max(1, dias);
    }

    public double calcularValorFinal() {
        if (quarto == null) {
            throw new IllegalStateException("Quarto não pode ser nulo.");
        }
        int diarias = calcularDiarias();
        double valorDiaria = quarto.calcularValorDiaria(qtdHospedes);
        return diarias * valorDiaria;
    }

    public void validarDatas() {
        if (dataHoraEntrada == null || dataHoraSaida == null) {
            throw new DataInvalidaException("Datas de entrada e saída não podem ser nulas.");
        }

        if (dataHoraEntrada.isAfter(dataHoraSaida)) {
            throw new DataInvalidaException(
                    "Data de entrada (" + dataHoraEntrada + ") não pode ser após a data de saída (" +
                    dataHoraSaida + ")."
            );
        }

        if (dataHoraEntrada.equals(dataHoraSaida)) {
            throw new DataInvalidaException("Data de entrada e saída não podem ser iguais.");
        }

        if (dataHoraEntrada.isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("Data de entrada não pode ser no passado.");
        }
    }

    public String imprimirRecibo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
                "Data e horário de entrada: %s%n" +
                "Data e horário de saída: %s%n" +
                "Número de diárias: %d%n" +
                "Total à pagar: R$ %.2f",
                dataHoraEntrada != null ? dataHoraEntrada.format(fmt) : "—",
                dataHoraSaida   != null ? dataHoraSaida.format(fmt)   : "—",
                qtdDiarias,
                valorTotal
        );
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataHoraEntrada() { return dataHoraEntrada; }
    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) { this.dataHoraEntrada = dataHoraEntrada; }

    public LocalDateTime getDataHoraSaida() { return dataHoraSaida; }
    public void setDataHoraSaida(LocalDateTime dataHoraSaida) { this.dataHoraSaida = dataHoraSaida; }

    public int getQtdDiarias() { return qtdDiarias; }
    public void setQtdDiarias(int qtdDiarias) { this.qtdDiarias = qtdDiarias; }

    public int getQtdHospedes() { return qtdHospedes; }
    public void setQtdHospedes(int qtdHospedes) { this.qtdHospedes = qtdHospedes; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public StatusAluguel getStatus() { return status; }
    public void setStatus(StatusAluguel status) { this.status = status; }

    public Quarto getQuarto() { return quarto; }
    public void setQuarto(Quarto quarto) { this.quarto = quarto; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Residencia getResidencia() { return residencia; }
    public void setResidencia(Residencia residencia) { this.residencia = residencia; }

    public Pagamento getPagamento() { return pagamento; }
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
}
