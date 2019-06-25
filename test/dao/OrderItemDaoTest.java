package dao;


import by.it.connection.ConnectionManager;
import by.it.dao.OrderItemDao;
import by.it.dao.impl.OrderItemDaoImpl;
import by.it.entities.OrderItem;
import org.junit.Assert;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderItemDaoTest {

    OrderItemDao orderItemDao = OrderItemDaoImpl.getInstance();

    @Test
    public void generalTest() throws SQLException {
        Connection connection = ConnectionManager.getConnection(2);
        connection.setAutoCommit(false);
        int start = orderItemDao.selectAll().size();
        int startID = orderItemDao.selectByIdOrder(34L).size();
        orderItemDao.insert(new OrderItem(34, 7050, 3));
        orderItemDao.insert(new OrderItem(34, 7050, 2));
        int finishID = orderItemDao.selectByIdOrder(34L).size();
        Assert.assertEquals("метод selectIdOrder не корректен",startID,finishID - 2);

        int finish = orderItemDao.selectAll().size();
        Assert.assertEquals("метод selectAll и insert не корректен", start, finish - 2);

        OrderItem orderItem = orderItemDao.insert(new OrderItem(54, 7067, 1));
        OrderItem newOrderItem = orderItemDao.selectById(orderItem.getIdOrderItem());
        Assert.assertEquals("метод selectID не корректен", orderItem, newOrderItem);

        orderItem.setQuantity(2);
        orderItemDao.update(orderItem);
        newOrderItem = orderItemDao.selectById(orderItem.getIdOrderItem());
        Assert.assertEquals("метод update не корретен", 2, newOrderItem.getQuantity());

        orderItemDao.delete(orderItem.getIdOrderItem());
        Assert.assertNull("метод delete не корректен",orderItemDao.selectById(orderItem.getIdOrderItem()));
        connection.rollback();
    }

}
