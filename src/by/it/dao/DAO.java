package by.it.dao;

import java.io.Serializable;
import java.sql.SQLException;


public interface DAO<T> {
    T insert(T t) throws SQLException;
    T selectById(Serializable id) throws SQLException;
    void update(T t) throws SQLException;
    int delete(Serializable id) throws SQLException;
}
