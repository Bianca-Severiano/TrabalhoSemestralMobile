package br.edu.fateczl.trabalhosemestral.controller;
import java.sql.SQLException;
public interface ICRUDDao <T>{

    public void inserir (T t) throws SQLException, ClassNotFoundException;
    public void atualizar (T t) throws SQLException, ClassNotFoundException;
    public void deletar(T t) throws SQLException, ClassNotFoundException;
    public T buscar(T t) throws SQLException, ClassNotFoundException;
}
