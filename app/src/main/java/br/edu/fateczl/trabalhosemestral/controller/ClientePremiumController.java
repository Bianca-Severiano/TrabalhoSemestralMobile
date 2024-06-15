package br.edu.fateczl.trabalhosemestral.controller;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.ClientePremium;
import br.edu.fateczl.trabalhosemestral.persistence.PremiumDao;

public class ClientePremiumController implements ICRUDDao<ClientePremium>{

    private final PremiumDao pDao;

    public ClientePremiumController(PremiumDao cDao) {
        this.pDao = cDao;
    }

    @Override
    public void inserir(ClientePremium clientePremium) throws SQLException, ClassNotFoundException {
        if (pDao == null){
            pDao.open();
        }
        pDao.inserir(clientePremium);
        pDao.close();
    }

    @Override
    public void atualizar(ClientePremium clientePremium) throws SQLException, ClassNotFoundException {
        if (pDao == null){
            pDao.open();
        }
        pDao.atualizar(clientePremium);
        pDao.close();
    }

    @Override
    public void deletar(ClientePremium clientePremium) throws SQLException, ClassNotFoundException {
        if (pDao == null){
            pDao.open();
        }
        pDao.excluir(clientePremium);
        pDao.close();
    }

    @Override
    public ClientePremium buscar(ClientePremium clientePremium) throws SQLException, ClassNotFoundException {
        if (pDao == null){
            pDao.open();
        }
        return pDao.buscar(clientePremium);
    }
}
