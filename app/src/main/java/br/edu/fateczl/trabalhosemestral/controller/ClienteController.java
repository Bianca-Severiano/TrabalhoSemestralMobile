package br.edu.fateczl.trabalhosemestral.controller;

import java.sql.SQLException;


import br.edu.fateczl.trabalhosemestral.model.ClientePadrao;
import br.edu.fateczl.trabalhosemestral.persistence.ClienteDao;

public class ClienteController implements ICRUDDao<ClientePadrao>{

    private final ClienteDao cDao;

    public  ClienteController (ClienteDao cDao){
            this.cDao = cDao;
            }

    @Override
    public void inserir(ClientePadrao cliente) throws SQLException, ClassNotFoundException {
        if (cDao == null){
            cDao.open();
        }
        cDao.inserir(cliente);
        cDao.close();
    }

    @Override
    public void atualizar(ClientePadrao cliente) throws SQLException, ClassNotFoundException {
        if (cDao == null){
            cDao.open();
        }
        cDao.atualizar(cliente);
        cDao.close();
    }

    @Override
    public void deletar(ClientePadrao cliente) throws SQLException, ClassNotFoundException {
        if (cDao == null){
            cDao.open();
        }
        cDao.excluir(cliente);
        cDao.close();
    }

    @Override
    public ClientePadrao buscar(ClientePadrao clientePadrao) throws SQLException, ClassNotFoundException {
        if (cDao == null){
            cDao.open();
        }
        return cDao.buscar(clientePadrao);
    }

    public boolean login (ClientePadrao cliente) throws SQLException, ClassNotFoundException {
        if (cDao == null){
            cDao.open();
        }
        return cDao.login(cliente);
    }



}
