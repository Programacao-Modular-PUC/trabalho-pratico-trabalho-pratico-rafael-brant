package br.pucminas.hospedagem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double valor;
    private LocalDateTime dataPagamento;
    private boolean processado;

    public Pagamento() {}

    public Pagamento(double valor, LocalDateTime dataPagamento, boolean processado) {
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.processado = processado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    public boolean isProcessado() { return processado; }
    public void setProcessado(boolean processado) { this.processado = processado; }
}
