package br.edu.fateczl.trabalhosemestral.model;

import java.io.Serializable;

public abstract class Cliente implements Serializable {

    private String CPF;
    private String nome;
    private String email;
    private String senha;

    public Cliente() {
        super();
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public abstract String condicaoFrete();
    public abstract String streaming ();


    @Override
    public String toString() {
        return "Cliente =>" +
                " CPF:" + CPF +
                ", Nome:" + nome +
                ", Email:" + email;
    }
}
