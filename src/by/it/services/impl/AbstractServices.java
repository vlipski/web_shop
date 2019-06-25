package by.it.services.impl;

import by.it.connection.ConnectionException;
import by.it.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractServices {

    public void startTransaction(int level) throws SQLException {
        ConnectionManager.getConnection(level).setAutoCommit(false);
    }

    public void commit(int level) throws SQLException {
        ConnectionManager.getConnection(level).commit();
    }

    public Connection getConnection(int level) {
        return ConnectionManager.getConnection(level);
    }

    public void rollback(int level) {
        try {
            getConnection(level).rollback();
        } catch (SQLException e) {
            throw new ConnectionException("rollback error");
        }
    }
}
