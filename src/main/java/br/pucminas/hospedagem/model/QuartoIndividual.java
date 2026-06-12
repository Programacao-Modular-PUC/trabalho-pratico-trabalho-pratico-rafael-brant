package br.pucminas.hospedagem.model;

import br.pucminas.hospedagem.exception.RecursoNaoPermitidoException;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("INDIVIDUAL")
public class QuartoIndividual extends Quarto {

    // Quarto individual não permite berço

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

    @Override
    public int getCapacidadeMaxima() {
        return 1; // Apenas uma pessoa
    }

    /**
     * Valida se berço pode ser solicitado neste quarto.
     * Quarto individual não permite berço.
    **/
    public void validarBerco() {
        throw new RecursoNaoPermitidoException("Quarto individual não permite berço.");
    }
}

