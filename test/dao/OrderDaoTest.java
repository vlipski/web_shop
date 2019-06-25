package dao;

import by.it.connection.ConnectionManager;
import by.it.dao.OrderDao;
import by.it.dao.impl.OrderDaoImpl;
import by.it.entities.Order;
import org.junit.Assert;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderDaoTest {
    OrderDao orderDao = OrderDaoImpl.getInstance();
    Order order = new Order();


    @Test
    public void generalTest() throws SQLException {
        order.setIdUser(34L);
        order.setTotal(345.);

        Connection connection = ConnectionManager.getConnection(2);
        connection.setAutoCommit(false);
        int start = orderDao.selectAll().size();
        System.out.println(orderDao.selectAll());
        order = orderDao.insert(order);
        int finish = orderDao.selectAll().size();
        System.out.println(orderDao.selectAll());
        System.out.println(finish);
        Assert.assertEquals("метод insert не корректен",start,finish - 1);

        Order newOrder = orderDao.selectById(order.getIdOrder());
        Assert.assertEquals("метод selectID не корректен",order.getIdOrder(),newOrder.getIdOrder());

        start = orderDao.selectByIdUser(10L).size();
        order.setIdUser(10L);
        orderDao.insert(order);
        finish = orderDao.selectByIdUser(10L).size();
        Assert.assertEquals("метод selectIdUser не корректен", start, finish - 1);

        order = orderDao.selectById(order.getIdOrder());
        order.setTotal(500.);
        orderDao.update(order);
        newOrder = orderDao.selectById(order.getIdOrder());
        Assert.assertEquals("метод update не корректен", order,newOrder);

        orderDao.delete(order.getIdOrder());
        Assert.assertNull("метод delete не корректен",orderDao.selectById(order.getIdOrder()));
        connection.rollback();

    }
}
