package br.edu.fateczl.trabalhosemestral.persistence;

import java.sql.SQLException;
public interface ICRUDDao <T> {

    public void inserir (T t) throws SQLException, ClassNotFoundException;
    public void atualizar(T t) throws SQLException, ClassNotFoundException;
    public void excluir(T t) throws SQLException, ClassNotFoundException;
    public T buscar (T t)  throws SQLException, ClassNotFoundException;

}
