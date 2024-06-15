package br.edu.fateczl.trabalhosemestral.persistence;
import java.sql.SQLException;

public interface IClienteDao {

    public ClienteDao open() throws SQLException;
    public void close() throws SQLException;

}
