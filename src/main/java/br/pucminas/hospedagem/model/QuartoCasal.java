package br.pucminas.hospedagem.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CASAL")
public class QuartoCasal extends Quarto {

    public QuartoCasal() {
        super();
    }

    public QuartoCasal(double valorBase, boolean possuiAr, boolean possuiHidro) {
        super(null, valorBase, possuiAr, possuiHidro);
    }

    @Override
    public double calcularValorDiaria(int qtdHospedes) {
        return getValorBase()
                + (isPossuiAr() ? 50.0 : 0)
                + (isPossuiHidro() ? 80.0 : 0);
    }
}
