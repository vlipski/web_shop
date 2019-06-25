package by.it.dao;

import by.it.entities.Order;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends DAO<Order> {

    List<Order> selectByIdUser(Serializable id) throws SQLException;

    List<Order> selectAll() throws SQLException;

}
