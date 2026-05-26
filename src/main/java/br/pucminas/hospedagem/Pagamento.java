package br.pucminas.hospedagem;
import java.time.LocalDateTime;

public class Pagamento {
    private double valor;
    private LocalDateTime dataPagamento;
    private boolean processado;
    public Pagamento(double valor, LocalDateTime dataPagamento, boolean processado) {
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.processado = processado;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }
    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    public boolean isProcessado() {
        return processado;
    }
    public void setProcessado(boolean processado) {
        this.processado = processado;
    }

    
}
