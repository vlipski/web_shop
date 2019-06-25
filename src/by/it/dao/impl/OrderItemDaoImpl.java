package by.it.dao.impl;

import by.it.dao.OrderItemDao;
import by.it.dao.dto.OrderItemDto;
import by.it.entities.OrderItem;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDaoImpl extends AbstractDao implements OrderItemDao {
    private static volatile OrderItemDao INSTANCE = null;

    private static final String insertSql = "INSERT INTO ORDER_ITEM (ID_ORDER, ID_PRODUCT, QUANTITY) VALUES (?, ?, ?)";
    private static final String selectIdSql = "SELECT * FROM ORDER_ITEM WHERE ID_ORDER_ITEM = ?";
    private static final String selectIdOrderSql = "SELECT * FROM ORDER_ITEM WHERE ID_ORDER = ?";
    private static final String selectAllSql = "SELECT * FROM ORDER_ITEM";
    private static final String updateSql = "UPDATE ORDER_ITEM SET ID_ORDER = ?, ID_PRODUCT = ?, QUANTITY = ?  WHERE ID_ORDER_ITEM = ?";
    private static final String deleteSql = "DELETE FROM ORDER_ITEM WHERE ID_ORDER_ITEM = ?";
    private static final String selectOrderItemDtoByIdOrderSql =
            "SELECT order_item.ID_ORDER, producttv.FABRICATOR, producttv.MODEL, producttv.PRICE, order_item.QUANTITY \n" +
                    "FROM myshop.ORDER_ITEM\n" +
                    "JOIN myshop.PRODUCTTV\n" +
                    "ON order_item.ID_PRODUCT = producttv.ID_PRODUCT\n" +
                    "WHERE order_item.ID_ORDER = ?";

    private PreparedStatement prstInsert;
    private PreparedStatement prstSelectId;
    private PreparedStatement prstSelectIdOrder;
    private PreparedStatement prstSelectAll;
    private PreparedStatement prstUpdate;
    private PreparedStatement prstDelete;
    private PreparedStatement prstSelectOrderItemDtoByIdOrder;


    public static OrderItemDao getInstance() {
        OrderItemDao orderItemDao = INSTANCE;
        if (orderItemDao == null) {
            synchronized (OrderItemDaoImpl.class) {
                orderItemDao = INSTANCE;
                if (orderItemDao == null) {
                    INSTANCE = orderItemDao = new OrderItemDaoImpl();
                }
            }
        }
        return orderItemDao;
    }

    @Override
    protected OrderItem populatEntity(ResultSet result) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setIdOrderItem(result.getLong(1));
        orderItem.setIdOrder(result.getLong(2));
        orderItem.setIdProduct(result.getLong(3));
        orderItem.setQuantity(result.getInt(4));
        return orderItem;
    }

    @Override
    public OrderItem insert(OrderItem orderItem) throws SQLException {
        prstInsert = preStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        prstInsert.setLong(1, orderItem.getIdOrder());
        prstInsert.setLong(2, orderItem.getIdProduct());
        prstInsert.setInt(3, orderItem.getQuantity());
        prstInsert.executeUpdate();
        ResultSet results = prstInsert.getGeneratedKeys();
        if (results.next()) {
            orderItem.setIdOrderItem(results.getLong(1));
        }
        close(results);
        return orderItem;
    }

    @Override
    public OrderItem selectById(Serializable id) throws SQLException {
        return selectOne(prstSelectId, selectIdSql, id);
        /*prstSelectId = preStatement(selectIdSql);
        prstSelectId.setLong(1, (long) id);
        prstSelectId.executeQuery();
        ResultSet result = prstSelectId.getResultSet();
        if (result.next()) {
            return populatEntity(result);
        }
        close(result);
        return null;*/
    }

    @Override
    public List<OrderItem> selectByIdOrder(Serializable id) throws SQLException {
        return selectList(prstSelectIdOrder, selectIdOrderSql, id);
    }

    @Override
    public List<OrderItem> selectAll() throws SQLException {
        return selectList(prstSelectAll, selectAllSql);
    }

    @Override
    public void update(OrderItem orderItem) throws SQLException {
        prstUpdate = preStatement(updateSql);
        prstUpdate.setLong(1, orderItem.getIdOrder());
        prstUpdate.setLong(2, orderItem.getIdProduct());
        prstUpdate.setInt(3, orderItem.getQuantity());
        prstUpdate.setLong(4, orderItem.getIdOrderItem());
        prstUpdate.executeUpdate();
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        prstDelete = preStatement(deleteSql);
        prstDelete.setLong(1, (long) id);
        return prstDelete.executeUpdate();
    }

    private OrderItemDto populatDto(ResultSet result) throws SQLException {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setIdOrder(result.getLong(1));
        orderItemDto.setFabricaeor(result.getString(2));
        orderItemDto.setModel(result.getString(3));
        orderItemDto.setPrice(result.getDouble(4));
        orderItemDto.setQuantity(result.getInt(5));
        return orderItemDto;
    }

    @Override
    public List<OrderItemDto> selectOrderItemDtoByIdOrder(Serializable id) throws SQLException {
        List<OrderItemDto> list = new ArrayList<>();
        prstSelectOrderItemDtoByIdOrder = preStatement(selectOrderItemDtoByIdOrderSql);
        prstSelectOrderItemDtoByIdOrder.setLong(1, (long) id);
        prstSelectOrderItemDtoByIdOrder.execute();
        ResultSet result = prstSelectOrderItemDtoByIdOrder.getResultSet();
        while (result.next()) {
            list.add(populatDto(result));
        }
        close(result);
        return list;
    }
}
