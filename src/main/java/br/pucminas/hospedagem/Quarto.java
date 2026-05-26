package br.pucminas.hospedagem;

import java.time.LocalDateTime;

public abstract class Quarto {
    
    private int id;
    private double valorBase;
    private boolean possuiAr;
    private boolean possuiHidro;

    public Quarto(int id, double valorBase, boolean possuiAr, boolean possuiHidro) {
        this.id = id;
        this.valorBase = valorBase;
        this.possuiAr = possuiAr;
        this.possuiHidro = possuiHidro;
    }

    // Método abstrato
    public abstract double calcularValorDiaria(int qtdHospedes);

    // Método concreto
    public boolean estaDisponivel(LocalDateTime inicio, LocalDateTime fim) {
        return true; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public boolean isPossuiAr() {
        return possuiAr;
    }

    public void setPossuiAr(boolean possuiAr) {
        this.possuiAr = possuiAr;
    }

    public boolean isPossuiHidro() {
        return possuiHidro;
    }

    public void setPossuiHidro(boolean possuiHidro) {
        this.possuiHidro = possuiHidro;
    }
}