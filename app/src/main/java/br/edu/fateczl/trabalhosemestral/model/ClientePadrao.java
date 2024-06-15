package br.edu.fateczl.trabalhosemestral.model;

public class ClientePadrao extends Cliente {

    public ClientePadrao() {
        super();
    }

    @Override
    public String condicaoFrete() {
        return "Sujeito a condições do vendedor";
    }


    @Override
    public String streaming() {
        return "Não possui direito a streaming";
    }

    @Override
    public String toString() {
        return super.toString() + " Frete: " + condicaoFrete();
    }
}
