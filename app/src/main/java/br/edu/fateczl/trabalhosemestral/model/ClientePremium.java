package br.edu.fateczl.trabalhosemestral.model;

public class ClientePremium extends Cliente {

    public FormaPagamentoClube getPagamento() {
        return pagamento;
    }

    public void setPagamento(FormaPagamentoClube pagamento) {
        this.pagamento = pagamento;
    }

    private FormaPagamentoClube pagamento;
    private String streaming;


    public ClientePremium() {
        super();
    }

    public String getStreaming() {
        return streaming;
    }

    public void setStreaming(String streaming) {
        this.streaming = streaming;
    }

    @Override
    public String condicaoFrete() {
        return "Frete grátis em compras a partir de R$39,90";
    }

    @Override
    public String streaming() {
            return "Vocês possui acesso a plataforman " + streaming;
    }

    @Override
    public String toString() {
        return super.toString() + " Frete: " + condicaoFrete() + " Streaming: " + streaming;
    }
}
