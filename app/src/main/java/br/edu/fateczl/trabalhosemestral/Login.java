package br.edu.fateczl.trabalhosemestral;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.controller.ClienteController;
import br.edu.fateczl.trabalhosemestral.model.Cliente;
import br.edu.fateczl.trabalhosemestral.model.ClientePadrao;
import br.edu.fateczl.trabalhosemestral.persistence.ClienteDao;


public class Login extends Fragment {

    private Button btnVoltarInicio;
    private Button btnCadastro;
    private Button btnLogin;
    private EditText cpf;
    private EditText senha;
    private View view;
    private Fragment fragment;
    private ClienteController cCont;
    private TextView SaidaErroLogin;

    public Login() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        btnVoltarInicio = view.findViewById(R.id.btnVoltarLogin);
        btnCadastro = view.findViewById(R.id.CriarConta);
        btnLogin = view.findViewById(R.id.btnLogin);
        cpf = view.findViewById(R.id.loginEmail);
        senha = view.findViewById(R.id.loginSenha);
        SaidaErroLogin = view.findViewById(R.id.SaidaErroLogin);

        btnVoltarInicio.setOnClickListener(op -> voltarInicio());
        btnCadastro.setOnClickListener(op -> cadastro());
        btnLogin.setOnClickListener(op -> logar());

        cCont = new ClienteController(new ClienteDao(view.getContext()));

        return view;
    }

    private void logar() {
        String i = validaCampos();

        if (i == "") {
            try {
                ClientePadrao cliente = CriaClientePadrao();
                boolean c = cCont.login(cliente);
                if (c == true) {
                    SaidaErroLogin.setText("");
                    ClientePadrao clienteLogado = cCont.buscar(cliente);
                    carregarTelaCliente(clienteLogado);
                } else {
                    SaidaErroLogin.setText("CPF ou senha incorretos");
                }
            } catch (SQLException | ClassNotFoundException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        } else {
            SaidaErroLogin.setText(i);
        }
    }

    private String validaCampos() {
        if (cpf.getText().toString().isEmpty() || senha.getText().toString().isEmpty()) {
            return "Por favor, preencha todos os campos";
        }
        return "";
    }

    private void cadastro() {
        fragment = new CadastroCliente();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Fragment, fragment);
        fragmentTransaction.commit();
    }

    private void voltarInicio() {
        Intent i = new Intent(view.getContext(), MainActivity.class);
        this.startActivity(i);
        getActivity().finish();
    }

    private ClientePadrao CriaClientePadrao() {
        ClientePadrao c = new ClientePadrao();
        c.setCPF(cpf.getText().toString());
        c.setSenha(senha.getText().toString());
        return c;
    }

    private void carregarTelaCliente(Cliente cliente) {

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