package br.edu.fateczl.trabalhosemestral.controller;

import android.util.Log;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.PagamentoCredito;
import br.edu.fateczl.trabalhosemestral.persistence.PagamentoCeditoDao;

public class PagamentoCreditoController implements ICRUDDao<PagamentoCredito> {
    private final PagamentoCeditoDao pDao;

    public PagamentoCreditoController(PagamentoCeditoDao pDao) {
        this.pDao = pDao;
    }


    @Override
    public void inserir(PagamentoCredito pagamentoCredito) throws SQLException, ClassNotFoundException {
        if (pDao == null){
            pDao.open();
        }
        pDao.inserir(pagamentoCredito);
        pDao.close();
    }

    @Override
    public void atualizar(PagamentoCredito pagamentoCredito) throws SQLException, ClassNotFoundException {
        if (pDao == null){
            pDao.open();
        }
        pDao.atualizar(pagamentoCredito);
        pDao.close();
    }

    @Override
    public void deletar(PagamentoCredito pagamentoCredito) throws SQLException, ClassNotFoundException {
        if (pDao == null){
            pDao.open();
        }
        pDao.excluir(pagamentoCredito);
        pDao.close();
    }

    @Override
    public PagamentoCredito buscar(PagamentoCredito pagamentoCredito) throws SQLException, ClassNotFoundException {
        if (pDao == null){
            pDao.open();
        }
        return pDao.buscar(pagamentoCredito);
    }
}
