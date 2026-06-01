package br.pucminas.hospedagem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    public int calcularDiarias() {
        return 0;
    }

    public double calcularValorFinal() {
        return 0.0;
    }

    public String imprimirRecibo() {
        return "";
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

    public Pagamento getPagamento() { return pagamento; }
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
}
