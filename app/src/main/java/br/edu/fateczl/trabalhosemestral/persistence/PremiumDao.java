package br.edu.fateczl.trabalhosemestral.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.ClientePadrao;
import br.edu.fateczl.trabalhosemestral.model.ClientePremium;
import br.edu.fateczl.trabalhosemestral.model.FormaPagamentoClube;
import br.edu.fateczl.trabalhosemestral.model.PagamentoCredito;
import br.edu.fateczl.trabalhosemestral.model.PagamentoDebitoConta;

public class PremiumDao implements ICRUDDao<ClientePremium>, IPremiumDao{

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public PremiumDao(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(ClientePremium c) {
        ContentValues content = new ContentValues();
        content.put("idCliente", c.getCPF());
        content.put("idPagamento", c.getPagamento().getTipo());
        content.put("Stream", c.getStreaming());
        return content;
    }

    @Override
    public void inserir(ClientePremium clientePremium) throws SQLException, ClassNotFoundException {
        open();
        ContentValues content = getContentValues(clientePremium);
        db.insert("Premium", null, content);
        close();
    }

    @Override
    public void atualizar(ClientePremium clientePremium) throws SQLException, ClassNotFoundException {
        open();
        ContentValues content = getContentValues(clientePremium);
        db.update("Premium", content, "idCliente = " + clientePremium.getCPF(), null);
        close();
    }

    @Override
    public void excluir(ClientePremium clientePremium) throws SQLException, ClassNotFoundException {
        open();
        ContentValues content = getContentValues(clientePremium);
        db.delete("Credito", "idPagamento = " + clientePremium.getCPF(), null);
        db.delete("Debito", "idPagamento = " + clientePremium.getCPF(), null);
        db.delete("Pagamento", "Cliente = " + clientePremium.getCPF(), null);
        db.delete("Premium", "idCliente = " + clientePremium.getCPF(), null);
        close();
    }

    @SuppressLint("Range")
    @Override
    public ClientePremium buscar(ClientePremium clientePremium) throws SQLException, ClassNotFoundException {
        open();
        String sql = "SELECT cl.nome as nome, cl.email as email, cl.senha as senha, p.Stream as Stream,  p.idPagamento as idPagamento, c.numCartao as numeroCartao, c.cvv as CVV, " +
        "c.vencimento as vencimento, d.conta as conta, d.agencia as agencia, d.banco as banco FROM Premium p " +
        " LEFT JOIN Credito c ON c.idPagamento = p.idCliente LEFT JOIN Debito d ON d.idPagamento = p.idCliente " +
                "LEFT JOIN Cliente cl ON cl.CPF = p.idCliente "+
        "WHERE p.idCliente =" + clientePremium.getCPF() ;

        Cursor cursor =  db.rawQuery(sql, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()){
            clientePremium.setStreaming(cursor.getString(cursor.getColumnIndex("Stream")));
            clientePremium.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            clientePremium.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            clientePremium.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            String tipoPagamento = cursor.getString(cursor.getColumnIndex("idPagamento"));
            if (tipoPagamento.equals("1")){
                PagamentoCredito pagamento = new PagamentoCredito();
                pagamento.setTipo(tipoPagamento);
                pagamento.setNomeTitular(clientePremium.getCPF());
                pagamento.setVencimento(cursor.getString(cursor.getColumnIndex("vencimento")));
                pagamento.setCvv(cursor.getInt(cursor.getColumnIndex("CVV")));
                pagamento.setNumeroCartao(cursor.getInt(cursor.getColumnIndex("numeroCartao")));
                clientePremium.setPagamento(pagamento);
            } else {
                PagamentoDebitoConta pagamento = new PagamentoDebitoConta();
                pagamento.setTipo(tipoPagamento);
                pagamento.setNomeTitular(clientePremium.getCPF());
                pagamento.setAgencia(cursor.getColumnIndex("agencia"));
                pagamento.setConta(cursor.getColumnIndex("conta"));
                pagamento.setBanco(cursor.getString(cursor.getColumnIndex("banco")));
                clientePremium.setPagamento(pagamento);
            }
        }
        cursor.close();
        return clientePremium;
    }

    @Override
    public PremiumDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() throws SQLException {
        gDao.close();
    }
}
