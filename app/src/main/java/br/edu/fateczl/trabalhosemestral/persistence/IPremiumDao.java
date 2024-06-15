package br.edu.fateczl.trabalhosemestral.persistence;

import java.sql.SQLException;

public interface IPremiumDao {

    public PremiumDao open() throws SQLException;
    public void close() throws SQLException;
}
