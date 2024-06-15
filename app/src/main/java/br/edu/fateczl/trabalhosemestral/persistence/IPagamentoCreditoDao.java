package br.edu.fateczl.trabalhosemestral.persistence;

import java.sql.SQLException;

public interface IPagamentoCreditoDao {
    public PagamentoCeditoDao open() throws SQLException;
    public void close() throws SQLException;
}
