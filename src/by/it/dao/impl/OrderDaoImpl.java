package by.it.dao.impl;

import by.it.dao.OrderDao;
import by.it.dao.OrderItemDao;
import by.it.entities.Order;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OrderDaoImpl extends AbstractDao implements OrderDao {

    private OrderItemDao orderItemDao = OrderItemDaoImpl.getInstance();
    private static volatile OrderDao INSTANCE = null;

    private static final String insertSql = "INSERT INTO `order` (ID_USER, TOTAL, DATA_ORDER) VALUES (?, ?, now());";
    private static final String selectIdSql = "SELECT * FROM `order` WHERE ID_ORDER = ?";
    private static final String selectIdUserSql = "SELECT * FROM `ORDER` WHERE ID_USER = ?";
    private static final String selectAllSql = "SELECT * FROM `ORDER`";
    private static final String updateSql = "UPDATE `ORDER` SET  TOTAL = ?  WHERE ID_ORDER = ?";
    private static final String deleteSql = "DELETE FROM `ORDER` WHERE ID_ORDER = ?";

    private PreparedStatement prstInsert;
    private PreparedStatement prstSelectId;
    private PreparedStatement prstSelectIdUser;
    private PreparedStatement prstSelectAll;
    private PreparedStatement prstUpdate;
    private PreparedStatement prstDelete;


    public static OrderDao getInstance() {
        OrderDao orderDao = INSTANCE;
        if (orderDao == null) {
            synchronized (OrderItemDaoImpl.class) {
                orderDao = INSTANCE;
                if (orderDao == null) {
                    INSTANCE = orderDao = new OrderDaoImpl();
                }
            }
        }
        return orderDao;
    }

    @Override
    protected Order populatEntity(ResultSet results) throws SQLException {
        Order order = new Order();
        order.setIdOrder(results.getLong(1));
        order.setIdUser(results.getLong(2));
        order.setTotal(results.getDouble(3));
        order.setData(results.getTimestamp(4));
        return order;
    }

    @Override
    public Order insert(Order order) throws SQLException {
        prstInsert = preStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        prstInsert.setLong(1, order.getIdUser());
        prstInsert.setDouble(2, order.getTotal());
        prstInsert.executeUpdate();
        ResultSet results = prstInsert.getGeneratedKeys();
        if (results.next()) {
            order.setIdOrder(results.getLong(1));
        }
        close(results);
        return order;
    }

    @Override
    public Order selectById(Serializable id) throws SQLException {
        return selectOne(prstSelectId, selectIdSql, id);
       /* prstSelectId = preStatement(selectIdSql);
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
    public List<Order> selectByIdUser(Serializable id) throws SQLException {
        return selectList(prstSelectIdUser, selectIdUserSql, id);
    }

    @Override
    public List<Order> selectAll() throws SQLException {
        return selectList(prstSelectAll, selectAllSql);
    }

    @Override
    public void update(Order order) throws SQLException {
        prstUpdate = preStatement(updateSql);
        prstUpdate.setDouble(1, order.getTotal());
        prstUpdate.setLong(2, order.getIdOrder());
        prstUpdate.executeUpdate();
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        prstDelete = preStatement(deleteSql);
        prstDelete.setLong(1, (long) id);
        return prstDelete.executeUpdate();
    }


}
