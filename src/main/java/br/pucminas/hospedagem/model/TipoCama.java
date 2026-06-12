package br.pucminas.hospedagem.model;

public enum TipoCama {
    CASAL(20.0),
    QUEEN(50.0),
    KING(80.0);

    private final double adicionalConforto;

    TipoCama(double adicionalConforto) {
        this.adicionalConforto = adicionalConforto;
    }

    public double getAdicionalConforto() {
        return adicionalConforto;
    }
}
