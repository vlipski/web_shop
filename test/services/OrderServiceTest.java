package services;

import by.it.connection.ConnectionManager;
import by.it.entities.Basket;
import by.it.entities.Order;
import by.it.services.BasketService;
import by.it.services.OrderService;

import by.it.services.impl.BasketServiceImpl;
import by.it.services.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;


public class OrderServiceTest {

    OrderService orderService = OrderServiceImpl.getInstance();
    BasketService basketService = BasketServiceImpl.getInstance();
    Basket basket;

    @Test
    public void orderTest() throws SQLException {
        Connection connection1 = ConnectionManager.getConnection(2);
        connection1.setAutoCommit(false);
        int before = orderService.selectOrderByIdUser(159L).size();
        basket = basketService.createBasket(new Basket(159L, 8653, 1));
        orderService.createOrder(159L);
        int after = orderService.selectOrderByIdUser(159L).size();
        Assert.assertEquals("createOrder incorrect", before,after - 1);
        connection1.rollback();
    }
}
