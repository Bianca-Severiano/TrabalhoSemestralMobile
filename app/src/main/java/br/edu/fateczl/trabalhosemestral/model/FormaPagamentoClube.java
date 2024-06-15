package br.edu.fateczl.trabalhosemestral.model;

public abstract class FormaPagamentoClube {

    private String Titular;
    private String tipo;



    public FormaPagamentoClube() {
     super();
    }

    public String getNomeTitular() {
        return Titular;
    }

    public void setNomeTitular(String c) {
        this.Titular = c;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "FormaPagamento =>" +
                " Nome do Titular: " + Titular +
                " Tipo: " + tipo;
    }
}
