package by.it.dao.impl;

import by.it.connection.ConnectionManager;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDao {

    protected PreparedStatement preStatement(String sql) throws SQLException {
        return ConnectionManager.getConnection(2).prepareStatement(sql);
    }

    protected PreparedStatement preStatement(String sql, int flag) throws SQLException {
        return ConnectionManager.getConnection(2).prepareStatement(sql, flag);
    }

    protected void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract <T> T populatEntity(ResultSet result) throws SQLException;

    protected <T> T selectOne(PreparedStatement preparedStatement, String sql, Serializable id) throws SQLException {
        preparedStatement = preStatement(sql);
        preparedStatement.setLong(1, (long) id);
        preparedStatement.executeQuery();
        ResultSet result = preparedStatement.getResultSet();
        if (result.next()) {
            return populatEntity(result);
        }
        close(result);
        return null;
    }

    protected <T> List<T> selectList(PreparedStatement preparedStatement, String sql) throws SQLException {
        List<T> list = new ArrayList<>();
        preparedStatement = preStatement(sql);
        preparedStatement.execute();
        ResultSet result = preparedStatement.getResultSet();
        while (result.next()) {
            list.add(populatEntity(result));
        }
        close(result);
        return list;
    }

    protected <T> List<T> selectList(PreparedStatement preparedStatement, String sql, Serializable id) throws SQLException {
        List<T> list = new ArrayList<>();
        preparedStatement = preStatement(sql);
        preparedStatement.setLong(1, (long) id);
        preparedStatement.executeQuery();
        ResultSet result = preparedStatement.getResultSet();
        while (result.next()) {
            list.add(populatEntity(result));
        }
        close(result);
        return list;
    }


    protected <T> List<T> selectList(PreparedStatement preparedStatement, String sql, int min, int max) throws SQLException {
        List<T> list = new ArrayList<>();
        preparedStatement = preStatement(sql);
        preparedStatement.setInt(1, min);
        preparedStatement.setInt(2, max);
        preparedStatement.executeQuery();
        ResultSet result = preparedStatement.getResultSet();
        while (result.next()) {
            list.add(populatEntity(result));
        }
        close(result);
        return list;
    }

    protected <T> List<T> selectList(PreparedStatement preparedStatement, String sql, String parametr) throws SQLException {
        List<T> list = new ArrayList<>();
        preparedStatement = preStatement(sql);
        preparedStatement.setString(1, parametr);
        preparedStatement.executeQuery();
        ResultSet result = preparedStatement.getResultSet();
        while (result.next()) {
            list.add(populatEntity(result));
        }
        close(result);
        return list;
    }

}
