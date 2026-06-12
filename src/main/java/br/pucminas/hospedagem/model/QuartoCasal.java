package br.pucminas.hospedagem.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CASAL")
public class QuartoCasal extends Quarto {

    private static final double TAXA_BERCO = 30.0;

    private boolean temBerco = false;

    @Enumerated(EnumType.STRING)
    private TipoCama tipoCama = TipoCama.CASAL;

    public QuartoCasal() {
        super();
    }

    public QuartoCasal(double valorBase, boolean possuiAr, boolean possuiHidro) {
        super(null, valorBase, possuiAr, possuiHidro);
    }

    public QuartoCasal(double valorBase, boolean possuiAr, boolean possuiHidro, boolean temBerco) {
        super(null, valorBase, possuiAr, possuiHidro);
        this.temBerco = temBerco;
    }

    public QuartoCasal(double valorBase, boolean possuiAr, boolean possuiHidro, boolean temBerco, TipoCama tipoCama) {
        super(null, valorBase, possuiAr, possuiHidro);
        this.temBerco = temBerco;
        this.tipoCama = tipoCama;
    }

    @Override
    public double calcularValorDiaria(int qtdHospedes) {
        double valor = getValorBase()
                + (isPossuiAr() ? 50.0 : 0)
                + (isPossuiHidro() ? 80.0 : 0)
                + tipoCama.getAdicionalConforto();

        if (temBerco && qtdHospedes > 2) {
            valor += TAXA_BERCO;
        }

        return valor;
    }

    @Override
    public int getCapacidadeMaxima() {
        return temBerco ? 3 : 2;
    }

    public boolean isTemBerco() { return temBerco; }
    public void setTemBerco(boolean temBerco) { this.temBerco = temBerco; }
    public TipoCama getTipoCama() { return tipoCama; }
    public void setTipoCama(TipoCama tipoCama) { this.tipoCama = tipoCama; }
}

