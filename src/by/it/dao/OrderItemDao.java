package by.it.dao;

import by.it.dao.dto.OrderItemDto;
import by.it.entities.OrderItem;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao extends DAO<OrderItem> {

    List<OrderItem> selectByIdOrder(Serializable id) throws SQLException;
    List<OrderItem> selectAll() throws SQLException;

    List<OrderItemDto> selectOrderItemDtoByIdOrder(Serializable id) throws SQLException;
}
