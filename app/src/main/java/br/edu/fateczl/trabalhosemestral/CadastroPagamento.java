package br.edu.fateczl.trabalhosemestral;

import static android.view.View.GONE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Arrays;

import br.edu.fateczl.trabalhosemestral.controller.ClienteController;
import br.edu.fateczl.trabalhosemestral.controller.ClientePremiumController;
import br.edu.fateczl.trabalhosemestral.controller.PagamentoCreditoController;
import br.edu.fateczl.trabalhosemestral.controller.PagamentoDebitoController;
import br.edu.fateczl.trabalhosemestral.model.Cliente;
import br.edu.fateczl.trabalhosemestral.model.ClientePadrao;
import br.edu.fateczl.trabalhosemestral.model.ClientePremium;
import br.edu.fateczl.trabalhosemestral.model.FormaPagamentoClube;
import br.edu.fateczl.trabalhosemestral.model.PagamentoCredito;
import br.edu.fateczl.trabalhosemestral.model.PagamentoDebitoConta;
import br.edu.fateczl.trabalhosemestral.persistence.ClienteDao;
import br.edu.fateczl.trabalhosemestral.persistence.PagamentoCeditoDao;
import br.edu.fateczl.trabalhosemestral.persistence.PagamentoDebitoDao;
import br.edu.fateczl.trabalhosemestral.persistence.PremiumDao;


public class CadastroPagamento extends Fragment {
    private ClienteController cCont;

    private ClientePremiumController pCont;

    private PagamentoCreditoController pcCont;
    private PagamentoDebitoController pdCont;
    private Fragment fragment;
    private View view;
    private Cliente cliente;

    private EditText etValor1;
    private EditText etValor2;
    private EditText etString;

    private RadioButton credito;
    private RadioButton debito;

    private Spinner spStreaming;

   private Button voltarInicio;
   private Button salvar;
   private Button btnAtualizarPagamento;
    private RadioGroup rgPagamentos;

    public CadastroPagamento() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cadastro_pagamento, container, false);
        voltarInicio = view.findViewById(R.id.btnVoltarPagamento);
        salvar = view.findViewById(R.id.btnCadastrarPagamento);

        rgPagamentos = view.findViewById(R.id.radioGroup);
        spStreaming = view.findViewById(R.id.spinner);
        debito = view.findViewById(R.id.rbDebitoConta);
        credito = view.findViewById(R.id.rbCredito);
        credito.setChecked(true);

        btnAtualizarPagamento = view.findViewById(R.id.btnAtualizarPagamento);
        btnAtualizarPagamento.setVisibility(GONE);
        etValor1 = view.findViewById(R.id.etValor1);
        etValor2 = view.findViewById(R.id.etValor2);
        etString = view.findViewById(R.id.etString);

        Bundle bundle = getArguments();
        if (bundle != null) {
            cliente = (Cliente) bundle.getSerializable("cliente");
        }

        cCont = new ClienteController(new ClienteDao(view.getContext()));
        pCont = new ClientePremiumController(new PremiumDao(view.getContext()));
        pcCont = new PagamentoCreditoController(new PagamentoCeditoDao(view.getContext()));
        pdCont = new PagamentoDebitoController(new PagamentoDebitoDao(view.getContext()));

        debito.setOnCheckedChangeListener(((buttonView, isChecked) -> atualizaRg()));
        credito.setOnCheckedChangeListener(((buttonView, isChecked) -> atualizaRg()));
        atualizarView(cliente);
        preenceSpinner();
        voltarInicio.setOnClickListener(op -> carregarTelaClientePadrao(cliente) );
        salvar.setOnClickListener(op -> CadastrarPremium());
        btnAtualizarPagamento.setOnClickListener(op -> AtualizaPagamento());

        return view;
    }

    private void AtualizaPagamento() {
        ClientePremium cp = montaClientePremium(cliente); // Novo
        ClientePremium ca = new ClientePremium();
        ca.setCPF(cp.getCPF());
        try {
            ca = pCont.buscar(ca);
        } catch (SQLException  | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (ca.getPagamento().getTipo().equals("1")){
            PagamentoCredito pc = montaPagamentoCredito(cp);
            cp.setPagamento(pc);

            if (debito.isChecked()){
                try {
                    PagamentoDebitoConta pdebitoa = montaPagamentoDebito(cp);
                    cp.setPagamento(pdebitoa);
                    pcCont.deletar(pc);
                    pdCont.inserir(pdebitoa);
                    pCont.atualizar(cp);
                    carregarTelaClientePadrao(cp);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    pcCont.atualizar(pc);
                    pCont.atualizar(cp);
                    carregarTelaClientePadrao(cp);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        } else if (ca.getPagamento().getTipo().equals("2")) {
            PagamentoDebitoConta pd = montaPagamentoDebito(cp);
            cp.setPagamento(pd);

            if (credito.isChecked()){
                try {
                    PagamentoCredito pcredito = montaPagamentoCredito(cp);
                    cp.setPagamento(pcredito);
                    pdCont.deletar(pd);
                    pcCont.inserir(pcredito);
                    pCont.atualizar(cp);
                    carregarTelaClientePadrao(cp);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    pdCont.atualizar(pd);
                    pCont.atualizar(cp);
                    carregarTelaClientePadrao(cp);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void CadastrarPremium() {
        //Credito
        if (credito.isChecked()){
            //Cliente
            ClientePremium cp = montaClientePremium(cliente);
            PagamentoCredito pc = montaPagamentoCredito(cp);
            cp.setPagamento(pc);
            try {
                pcCont.inserir(pc);
                pCont.inserir(cp);
                carregarTelaClientePadrao(cp);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            ClientePremium cp2 = montaClientePremium(cliente);
            PagamentoDebitoConta pd = montaPagamentoDebito(cp2);
            cp2.setPagamento(pd);
            try {
                pdCont.inserir(pd);
                pCont.inserir(cp2);
                carregarTelaClientePadrao(cp2);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private PagamentoCredito montaPagamentoCredito(ClientePremium cp) {
        PagamentoCredito pc = new PagamentoCredito();
        pc.setNomeTitular(cp.getCPF());
        pc.setTipo("1");
        pc.setNumeroCartao(Integer.parseInt(etValor1.getText().toString()));
        pc.setVencimento(etString.getText().toString());
        pc.setCvv(Integer.parseInt(etValor2.getText().toString()));
        return pc;
    }

    private PagamentoDebitoConta montaPagamentoDebito(ClientePremium cp) {
        PagamentoDebitoConta pc2 = new PagamentoDebitoConta();
        pc2.setNomeTitular(cp.getCPF());
        pc2.setTipo("2");
        pc2.setConta(Integer.parseInt(etValor1.getText().toString()));
        pc2.setBanco(etString.getText().toString());
        pc2.setAgencia(Integer.parseInt(etValor2.getText().toString()));
        return pc2;
    }

    private ClientePremium montaClientePremium (Cliente c){
        ClientePremium cp = new ClientePremium();
        cp.setNome(c.getNome());
        cp.setCPF(c.getCPF());
        cp.setEmail(c.getEmail());
        cp.setSenha(c.getSenha());
        cp.setStreaming(spStreaming.getSelectedItem().toString());
        return cp;
    }

    private void atualizarView(Cliente c) {
        PagamentoCredito pagamentoC = new PagamentoCredito();
        pagamentoC.setNomeTitular(c.getCPF().toString());

        PagamentoDebitoConta pagamentoD = new PagamentoDebitoConta();
        pagamentoD.setNomeTitular(c.getCPF());
        try {
            pagamentoD = pdCont.buscar(pagamentoD);
            pagamentoC = pcCont.buscar(pagamentoC);
            if (pagamentoC.getVencimento() != null){ // É crédito
                btnAtualizarPagamento.setVisibility(View.VISIBLE);
                credito.setChecked(true);
                debito.setOnCheckedChangeListener(((buttonView, isChecked) -> atualizaRg()));
                String numero = String.valueOf(pagamentoC.getNumeroCartao());
                String cvv = String.valueOf(pagamentoC.getCvv());
                String vencimento = pagamentoC.getVencimento();
                etValor1.setText(numero);
                etValor2.setText(cvv);
                etString.setText(vencimento);
                preenceSpinner();
            } else if (pagamentoC.getVencimento() == null){
                if(pagamentoD.getBanco() == null){
                    atualizaRg();
                } else {
                    btnAtualizarPagamento.setVisibility(View.VISIBLE);
                    debito.setChecked(true);
                    credito.setOnCheckedChangeListener(((buttonView, isChecked) -> atualizaRg()));
                    String conta = String.valueOf(pagamentoD.getConta());
                    String agencia = String.valueOf(pagamentoD.getAgencia());
                    String banco = String.valueOf(pagamentoD.getBanco());
                    etValor1.setText(conta);
                    etValor2.setText(agencia);
                    etString.setText(banco);
                    preenceSpinner();
                }
            } else {
                atualizaRg();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void atualizaRg(){
        etValor1.setText("");
        etValor2.setText("");
        etString.setText("");
        if (credito.isChecked()){
            etValor1.setHint("Número do Cartão");
            etValor2.setHint("CVV");
            etString.setHint("Vencimento");
        } else {
            etValor1.setHint("Conta");
            etValor2.setHint("Agencia");
            etString.setHint("Banco");
        }
    }

    private void preenceSpinner(){
        String Array[] = {" Escolha um Streaming" ,"Disney+", "Globoplay"};
        ArrayAdapter ad = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_spinner_item,
                Array);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStreaming.setAdapter(ad);

        ClientePremium cp = new ClientePremium();
        cp.setCPF(cliente.getCPF());

        try {
            String stream = pCont.buscar(cp).getStreaming();
            int pos = Arrays.asList(Array).indexOf(stream);
            spStreaming.setSelection(pos);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void carregarTelaClientePadrao(Cliente cliente) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("cliente", cliente);
        TelaInicial telaClienteFragment = new TelaInicial();
        telaClienteFragment.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Fragment, telaClienteFragment);
        fragmentTransaction.commit();
    }

}