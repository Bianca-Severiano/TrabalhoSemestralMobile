package br.edu.fateczl.trabalhosemestral.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {


    private static final String DATABASE = "ECommerceABC";
    private static final int DATABASE_VER = 2;

    private static final String CREATE_TABLE_CLIENTE = "Create table Cliente ( " +
            "CPF varchar(13) NOT NULL primary key, " +
            "nome varchar(100) NOT NULL, " +
            "email varchar(80) NOT NULL," +
            "senha varchar(80) NOT NULL " +
            ");";

    private static final String CREATE_TABLE_PREMIUM = "Create table Premium (" +
            "idCliente varchar(13) NOT NULL, " +
            "idPagamento int(100) NOT NULL, " +
            "Stream varchar(50) NOT NULL, " +
            "PRIMARY KEY (idCliente), " +
            "FOREIGN KEY (idCliente) REFERENCES Cliente (CPF), " +
            "FOREIGN KEY (idPagamento) REFERENCES Pagamento (idTipo) " +
            ");";

    private static final String CREATE_TABLE_PAGAMENTO= "Create table Pagamento (" +
            "idTipo varchar(10) NOT NULL , " +
            "Cliente int (13) NOT NULL, " +
            "PRIMARY KEY (Cliente), " +
            "FOREIGN KEY (Cliente) REFERENCES Cliente (CPF) " +
            ");";

    private static final String CREATE_TABLE_CREDITO = "Create table Credito (" +
            "idPagamento varchar (100) NOT NULL, " +
            "numCartao int(20) NOT NULL, " +
            "cvv int(8) NOT NULL, " +
            "vencimento varchar(10) NOT NULL, " +
            "FOREIGN KEY (idPagamento) REFERENCES Pagamento (Cliente) " +
            ");";

    private static final String CREATE_TABLE_DEBITO = "Create table Debito (" +
            "idPagamento varchar (23) NOT NULL, " +
            "conta int(20) NOT NULL, " +
            "agencia int(8) NOT NULL, " +
            "banco varchar(10) NOT NULL, " +
            "FOREIGN KEY (idPagamento) REFERENCES Pagamento (Cliente) " +
            ");";


    public GenericDao(Context context){
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENTE);
        db.execSQL(CREATE_TABLE_PREMIUM);
        db.execSQL(CREATE_TABLE_PAGAMENTO);
        db.execSQL(CREATE_TABLE_CREDITO);
        db.execSQL(CREATE_TABLE_DEBITO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS cliente");
            db.execSQL("DROP TABLE IF EXISTS premium");
            db.execSQL("DROP TABLE IF EXISTS pagamento");
            db.execSQL("DROP TABLE IF EXISTS credito");
            db.execSQL("DROP TABLE IF EXISTS debito");
            onCreate(db);
        }
    }
}
