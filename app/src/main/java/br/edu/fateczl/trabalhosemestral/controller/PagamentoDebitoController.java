package br.edu.fateczl.trabalhosemestral.controller;

import android.util.Log;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.PagamentoDebitoConta;
import br.edu.fateczl.trabalhosemestral.persistence.PagamentoDebitoDao;

public class PagamentoDebitoController implements ICRUDDao<PagamentoDebitoConta>{

    private final PagamentoDebitoDao pdDao;

    public PagamentoDebitoController(PagamentoDebitoDao pDao) {
        this.pdDao = pDao;
    }


    @Override
    public void inserir(PagamentoDebitoConta pagamentoDebitoConta) throws SQLException, ClassNotFoundException {
        if (pdDao == null){
            pdDao.open();
        }
        pdDao.inserir(pagamentoDebitoConta);
        pdDao.close();
    }

    @Override
    public void atualizar(PagamentoDebitoConta pagamentoDebitoConta) throws SQLException, ClassNotFoundException {
        if (pdDao == null){
            pdDao.open();
        }
        pdDao.atualizar(pagamentoDebitoConta);
        pdDao.close();
    }

    @Override
    public void deletar(PagamentoDebitoConta pagamentoDebitoConta) throws SQLException, ClassNotFoundException {
        if (pdDao == null){
            pdDao.open();
        }
        pdDao.excluir(pagamentoDebitoConta);
        pdDao.close();
    }

    @Override
    public PagamentoDebitoConta buscar(PagamentoDebitoConta pagamentoDebitoConta) throws SQLException, ClassNotFoundException {
        if (pdDao == null){
            pdDao.open();
        }
        return pdDao.buscar(pagamentoDebitoConta);
    }
}
