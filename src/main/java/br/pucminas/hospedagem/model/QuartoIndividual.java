package br.pucminas.hospedagem.model;

import br.pucminas.hospedagem.exception.RecursoNaoPermitidoException;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("INDIVIDUAL")
public class QuartoIndividual extends Quarto {

    private static final double ADICIONAL_POR_CAMA = 30.0;

    private int qtdCamas = 1;

    public QuartoIndividual() {
        super();
    }

    public QuartoIndividual(double valorBase, boolean possuiAr, boolean possuiHidro) {
        super(null, valorBase, possuiAr, possuiHidro);
    }

    public QuartoIndividual(double valorBase, boolean possuiAr, boolean possuiHidro, int qtdCamas) {
        super(null, valorBase, possuiAr, possuiHidro);
        if (qtdCamas < 1) throw new IllegalArgumentException("Quantidade de camas deve ser pelo menos 1.");
        this.qtdCamas = qtdCamas;
    }

    @Override
    public double calcularValorDiaria(int qtdHospedes) {
        double adicional = qtdCamas > 1 ? ADICIONAL_POR_CAMA * (qtdCamas - 1) : 0;
        return getValorBase()
                + (isPossuiAr() ? 50.0 : 0)
                + (isPossuiHidro() ? 80.0 : 0)
                + adicional;
    }

    @Override
    public int getCapacidadeMaxima() {
        return qtdCamas;
    }

    public void validarBerco() {
        throw new RecursoNaoPermitidoException("Quarto individual não permite berço.");
    }

    public int getQtdCamas() { return qtdCamas; }

    public void setQtdCamas(int qtdCamas) {
        if (qtdCamas < 1) throw new IllegalArgumentException("Quantidade de camas deve ser pelo menos 1.");
        this.qtdCamas = qtdCamas;
    }
}

