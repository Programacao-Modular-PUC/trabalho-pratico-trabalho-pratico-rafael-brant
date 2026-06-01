package br.pucminas.hospedagem.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("INDIVIDUAL")
public class QuartoIndividual extends Quarto {

    public QuartoIndividual() {
        super();
    }

    public QuartoIndividual(double valorBase, boolean possuiAr, boolean possuiHidro) {
        super(null, valorBase, possuiAr, possuiHidro);
    }

    @Override
    public double calcularValorDiaria(int qtdHospedes) {
        return getValorBase()
                + (isPossuiAr() ? 50.0 : 0)
                + (isPossuiHidro() ? 80.0 : 0);
    }
}
