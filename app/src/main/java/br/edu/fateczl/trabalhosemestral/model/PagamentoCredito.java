package br.edu.fateczl.trabalhosemestral.model;

public class PagamentoCredito extends FormaPagamentoClube{

    private int numeroCartao;
    private int cvv;
    private  String vencimento;

    public PagamentoCredito() {
        super();
    }

    public int getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(int numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    @Override
    public String toString() {
        return super.toString() +
            " Número do Cartão: " + numeroCartao +
                ", CVV: " + cvv +
                ", Vencimento: " + vencimento;
    }
}
