package br.pucminas.hospedagem.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("FAMILIA")
public class QuartoFamilia extends Quarto {

    private static final double PERCENTUAL_POR_HOSPEDE = 0.05;

    private int qtdCamasSolteiro = 0;
    private int qtdCamasCasal = 0;
    private int qtdCamasQueenKing = 0;
    private int qtdAmbientes = 1;

    public QuartoFamilia() {
        super();
    }

    public QuartoFamilia(double valorBase, boolean possuiAr, boolean possuiHidro,
                         int qtdCamasSolteiro, int qtdCamasCasal, int qtdCamasQueenKing, int qtdAmbientes) {
        super(null, valorBase, possuiAr, possuiHidro);
        this.qtdCamasSolteiro = qtdCamasSolteiro;
        this.qtdCamasCasal = qtdCamasCasal;
        this.qtdCamasQueenKing = qtdCamasQueenKing;
        this.qtdAmbientes = qtdAmbientes;
    }

    @Override
    public double calcularValorDiaria(int qtdHospedes) {
        double base = getValorBase()
                + (isPossuiAr() ? 50.0 : 0)
                + (isPossuiHidro() ? 80.0 : 0);

        double valorComHospedes = base * (1 + PERCENTUAL_POR_HOSPEDE * qtdHospedes);

        double desconto = calcularDescontoGrupo(qtdHospedes);
        return valorComHospedes * (1 - desconto);
    }

    // Desconto progressivo: grupos maiores pagam menos por pessoa do que alugar quartos separados
    private double calcularDescontoGrupo(int qtdHospedes) {
        if (qtdHospedes >= 7) return 0.15;
        if (qtdHospedes >= 5) return 0.10;
        if (qtdHospedes >= 3) return 0.05;
        return 0.0;
    }

    @Override
    public int getCapacidadeMaxima() {
        return qtdCamasSolteiro + qtdCamasCasal * 2 + qtdCamasQueenKing * 2;
    }

    public int getQtdCamasSolteiro() { return qtdCamasSolteiro; }
    public void setQtdCamasSolteiro(int qtdCamasSolteiro) { this.qtdCamasSolteiro = qtdCamasSolteiro; }
    public int getQtdCamasCasal() { return qtdCamasCasal; }
    public void setQtdCamasCasal(int qtdCamasCasal) { this.qtdCamasCasal = qtdCamasCasal; }
    public int getQtdCamasQueenKing() { return qtdCamasQueenKing; }
    public void setQtdCamasQueenKing(int qtdCamasQueenKing) { this.qtdCamasQueenKing = qtdCamasQueenKing; }
    public int getQtdAmbientes() { return qtdAmbientes; }
    public void setQtdAmbientes(int qtdAmbientes) { this.qtdAmbientes = qtdAmbientes; }
}
