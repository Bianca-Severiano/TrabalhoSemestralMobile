package br.edu.fateczl.trabalhosemestral;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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


public class CadastroCliente extends Fragment {

    private Fragment fragment;
    private View view;
    private Button btnVoltarLogin;
    private Button btnSeguirCadastro;

    private EditText nome;
    private EditText CPF;
    private EditText email;
    private EditText senha;
    private EditText confSenha;

    private TextView Saidaerro;

    private ClienteController cCont;

    public CadastroCliente() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cadastro_cliente, container, false);
        btnVoltarLogin = view.findViewById(R.id.btnVoltarCadastro);
        btnSeguirCadastro = view.findViewById(R.id.btnProsseguir);

        nome = view.findViewById(R.id.etNomeCliente);
        CPF = view.findViewById(R.id.etCPF);
        email = view.findViewById(R.id.etEmailCliente);
        senha = view.findViewById(R.id.etSenha1);
        confSenha = view.findViewById(R.id.etSenha2);
        Saidaerro = view.findViewById(R.id.erro);

        btnVoltarLogin.setOnClickListener(op -> voltarInicio());
        btnSeguirCadastro.setOnClickListener(op -> cadastro());

        cCont = new ClienteController(new ClienteDao(view.getContext()));
        return view;
    }


    private ClientePadrao CriaClientePadrao() {
        ClientePadrao c = new ClientePadrao();
        c.setNome(nome.getText().toString());
        c.setCPF(CPF.getText().toString());
        c.setEmail(email.getText().toString());
        c.setSenha(senha.getText().toString());

        return c;
    }

    private void cadastro() {
        String i = validaCampos();
        if (i == ""){
            try {
                ClientePadrao cliente = CriaClientePadrao();
                cCont.inserir(cliente);
                voltarLogin();
            } catch (SQLException | ClassNotFoundException e){
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else{
            Saidaerro.setText(i);
        }
    }

    private String validaCampos() {
        if (!senha.getText().toString().equals(confSenha.getText().toString()) && (!senha.getText().toString().isEmpty())) {
            return "As senhas não conferem e/ou são nulas";
        }

        if (nome.getText().toString().isEmpty() || CPF.getText().toString().isEmpty() || email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
            return  "Um ou mais campos podem não ter sido preenchidos";
        }

        return "";
    }

    private void voltarInicio() {
        Intent i = new Intent(view.getContext(), MainActivity.class);
        this.startActivity(i);
        getActivity().finish();
    }

    private void voltarLogin() {
        fragment = new Login();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Fragment, fragment);
        fragmentTransaction.commit();
    }

}

