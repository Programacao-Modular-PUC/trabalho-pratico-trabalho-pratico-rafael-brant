package br.pucminas.hospedagem;
import java.util.ArrayList;
import java.util.List;

public class Residencia {
    private String endereco;
    private String numero; 
    private String bairro;
    private String cep;
    private String telefone;
    private String email;
    private List<Quarto> quartos; 
    private List<Aluguel> historicoAlugueis; 

  
    public Residencia() {
        this.quartos = new ArrayList<>();
        this.historicoAlugueis = new ArrayList<>(); 
    }

    public Residencia(String endereco, String numero, String bairro, String cep, String telefone, String email) {
        this();
        this.endereco = endereco;  
        this.numero = numero;  
        this.bairro = bairro;  
        this.cep = cep;  
        this.telefone = telefone;  
        this.email = email; 
    }

    public void adicionarQuarto(Quarto quarto) {
        if (quarto != null) {
            this.quartos.add(quarto);
        }
    }

    public List<Aluguel> obterHistorico() {
        return this.historicoAlugueis;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }

    public void setQuartos(List<Quarto> quartos) {
        this.quartos = quartos;
    }

    public void setHistoricoAlugueis(List<Aluguel> historicoAlugueis) {
        this.historicoAlugueis = historicoAlugueis;
    }
}