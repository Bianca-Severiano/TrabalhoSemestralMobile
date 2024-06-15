package br.edu.fateczl.trabalhosemestral.model;

public class PagamentoDebitoConta extends FormaPagamentoClube {

    private String banco;
    private int agencia;
    private int conta;

    public PagamentoDebitoConta() {
        super();
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Banco: " + banco +
                ", Conta: " + conta +
                ", Agência: " + agencia;
    }
}
