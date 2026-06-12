package br.pucminas.hospedagem.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CASAL")
public class QuartoCasal extends Quarto {

    private boolean temBerco = false; // Indica se o quarto possui berço

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

    @Override
    public double calcularValorDiaria(int qtdHospedes) {
        double valor = getValorBase()
                + (isPossuiAr() ? 50.0 : 0)
                + (isPossuiHidro() ? 80.0 : 0);

        // Adiciona taxa extra se tiver berço e mais de 2 hóspedes
        if (temBerco && qtdHospedes > 2) {
            valor += 30.0; // Taxa adicional por berço
        }

        return valor;
    }

    @Override
    public int getCapacidadeMaxima() {
        // Quarto de casal: 2 pessoas + 1 bebê (com berço) = 3 no máximo
        return temBerco ? 3 : 2;
    }

    public boolean isTemBerco() {
        return temBerco;
    }

    public void setTemBerco(boolean temBerco) {
        this.temBerco = temBerco;
    }
}

