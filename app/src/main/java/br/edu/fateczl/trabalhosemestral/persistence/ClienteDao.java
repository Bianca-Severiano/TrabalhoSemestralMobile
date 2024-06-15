package br.edu.fateczl.trabalhosemestral.persistence;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.Cliente;
import br.edu.fateczl.trabalhosemestral.model.ClientePadrao;
import br.edu.fateczl.trabalhosemestral.model.ClientePremium;
import br.edu.fateczl.trabalhosemestral.model.FormaPagamentoClube;

public class ClienteDao implements ICRUDDao<ClientePadrao>, IClienteDao {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public ClienteDao(Context context) {
        this.context = context;
    }

    private static ContentValues getContentValues(ClientePadrao c) {
        ContentValues content = new ContentValues();
        content.put("CPF", c.getCPF());
        content.put("nome", c.getNome());
        content.put("email", c.getEmail());
        content.put("senha", c.getSenha());
        return content;
    }

    @Override
    public void inserir(ClientePadrao cliente) throws SQLException, ClassNotFoundException {
        open();
        ContentValues content = getContentValues(cliente);
        db.insert("Cliente", null, content);
    }

    @Override
    public void atualizar(ClientePadrao cliente) throws SQLException, ClassNotFoundException {
        open();
        ContentValues content = getContentValues(cliente);
        db.update("Cliente", content, "CPF = '" + cliente.getCPF() + "'", null);
        close();
    }

    @Override
    public void excluir(ClientePadrao cliente) throws SQLException, ClassNotFoundException {
        open();
        ContentValues content = getContentValues(cliente);
        db.delete("Cliente", "CPF = " + cliente.getCPF(), null);
        close();
    }

    @SuppressLint("Range")
    @Override
    public ClientePadrao buscar(ClientePadrao cliente) throws SQLException, ClassNotFoundException {
        open();
        String sql = "SELECT CPF, nome, email, senha FROM Cliente WHERE CPF = " +  cliente.getCPF();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        if(!cursor.isAfterLast() ){
            cliente.setCPF(cursor.getString(cursor.getColumnIndex("CPF")));
            cliente.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            cliente.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            cliente.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
        }
        cursor.close();

        return cliente;
    }

    public boolean login(Cliente cliente) throws SQLException, ClassNotFoundException {
        open();
        String sql = "SELECT * FROM Cliente WHERE cpf = ? AND senha = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{cliente.getCPF(), cliente.getSenha()});
        boolean isValid = false;
        if (cursor != null) {
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                isValid = cursor.getInt(0) > 0;
                cursor.close();
                isValid = true;
            }
        }
        close();
        return isValid;
    }

    @Override
    public ClienteDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() throws SQLException {
        gDao.close();
    }
}
