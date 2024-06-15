package br.edu.fateczl.trabalhosemestral.persistence;

import java.sql.SQLException;

public interface IPagamentoDebitoDao {
    public PagamentoDebitoDao open() throws SQLException;
    public void close() throws SQLException;
}
