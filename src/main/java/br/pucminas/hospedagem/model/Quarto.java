package br.pucminas.hospedagem.model;

import br.pucminas.hospedagem.exception.CapacidadeExcedidaException;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_quarto")
public abstract class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double valorBase;
    private boolean possuiAr;
    private boolean possuiHidro;

    protected Quarto() {}

    public Quarto(Long id, double valorBase, boolean possuiAr, boolean possuiHidro) {
        this.id = id;
        this.valorBase = valorBase;
        this.possuiAr = possuiAr;
        this.possuiHidro = possuiHidro;
    }

    //Calcula o valor da diária para o quarto baseado no tipo e quantidade de hóspedes

    public abstract double calcularValorDiaria(int qtdHospedes);

    //Retorna a capacidade máxima de hóspedes permitida neste quarto
    public abstract int getCapacidadeMaxima();

    //Valida se a quantidade de hóspedes está dentro da capacidade máxima.
    public void validarCapacidade(int qtdHospedes) {
        if (qtdHospedes > getCapacidadeMaxima()) {
            throw new CapacidadeExcedidaException(
                    "Capacidade excedida. Máximo de hóspedes: " + getCapacidadeMaxima() +
                    ". Solicitado: " + qtdHospedes
            );
        }
        if (qtdHospedes <= 0) {
            throw new CapacidadeExcedidaException("Quantidade de hóspedes deve ser maior que zero.");
        }
    }

    // Verifica a disponibilidade do quarto para as datas informadas
    public boolean estaDisponivel(LocalDateTime inicio, LocalDateTime fim) {
        return true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getValorBase() { return valorBase; }
    public void setValorBase(double valorBase) { this.valorBase = valorBase; }
    public boolean isPossuiAr() { return possuiAr; }
    public void setPossuiAr(boolean possuiAr) { this.possuiAr = possuiAr; }
    public boolean isPossuiHidro() { return possuiHidro; }
    public void setPossuiHidro(boolean possuiHidro) { this.possuiHidro = possuiHidro; }
}
