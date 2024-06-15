package br.edu.fateczl.trabalhosemestral.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.PagamentoCredito;
import br.edu.fateczl.trabalhosemestral.model.PagamentoDebitoConta;

public class PagamentoCeditoDao implements  ICRUDDao<PagamentoCredito>, IPagamentoCreditoDao{

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;


    public PagamentoCeditoDao(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(PagamentoCredito dc) {
        ContentValues content = new ContentValues();
        content.put("idPagamento", dc.getNomeTitular());
        content.put("numCartao", dc.getNumeroCartao());
        content.put("cvv", dc.getCvv());
        content.put("vencimento", dc.getVencimento());
        return content;
    }

    private static ContentValues getContentValuesPagamento(PagamentoCredito dc) {
        ContentValues content = new ContentValues();
        content.put("idTipo", dc.getTipo());
        content.put("Cliente", dc.getNomeTitular());
        return content;
    }

    @Override
    public void inserir(PagamentoCredito pagamentoCredito) throws SQLException, ClassNotFoundException {
        open();
        ContentValues content2 = getContentValues(pagamentoCredito);
        ContentValues content = getContentValuesPagamento(pagamentoCredito);
        db.insert("Pagamento", null, content);
        db.insert("Credito", null, content2);
        close();
    }

    @Override
    public void atualizar(PagamentoCredito pagamentoCredito) throws SQLException, ClassNotFoundException {
        open();
        ContentValues content2 = getContentValues(pagamentoCredito);
        ContentValues content = getContentValuesPagamento(pagamentoCredito);
        db.update("Pagamento", content, "Cliente = " + pagamentoCredito.getNomeTitular(), null);
        db.update("Credito", content2, "idPagamento = " + pagamentoCredito.getNomeTitular(), null);
        close();
    }

    @Override
    public void excluir(PagamentoCredito pagamentoCredito) throws SQLException, ClassNotFoundException {
        open();
        ContentValues content2 = getContentValues(pagamentoCredito);
        ContentValues content = getContentValuesPagamento(pagamentoCredito);
        db.delete("Pagamento", "Cliente = " + pagamentoCredito.getNomeTitular(), null);
        db.delete("Credito", "idPagamento = " + pagamentoCredito.getNomeTitular(), null);
        close();
    }

    @SuppressLint("Range")
    @Override
    public PagamentoCredito buscar(PagamentoCredito pagamentoCredito) throws SQLException, ClassNotFoundException {
        open();
        String sql = "SELECT p.idTipo as Tipo, p.Cliente as Cliente, c.idPagamento as idPagamento, c.numCartao as cartao, " +
                "c.cvv as cvv, c.vencimento as vencimento FROM Credito c " +
                "LEFT JOIN Pagamento p ON c.idPagamento = p.Cliente "+
                "WHERE p.Cliente = " + pagamentoCredito.getNomeTitular();

        Cursor cursor =  db.rawQuery(sql, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        if (!cursor.isAfterLast()){
            pagamentoCredito.setNomeTitular(cursor.getString(cursor.getColumnIndex("Cliente")));
            pagamentoCredito.setTipo(cursor.getString(cursor.getColumnIndex("Tipo")));
            pagamentoCredito.setCvv(cursor.getInt(cursor.getColumnIndex("cvv")));
            pagamentoCredito.setNumeroCartao(cursor.getInt(cursor.getColumnIndex("cartao")));
            pagamentoCredito.setVencimento(cursor.getString(cursor.getColumnIndex("vencimento")));
            cursor.moveToNext();
        }
        cursor.close();
        return pagamentoCredito;
    }


    @Override
    public PagamentoCeditoDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() throws SQLException {
        gDao.close();
    }
}
