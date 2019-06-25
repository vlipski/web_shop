package dao;

import by.it.connection.ConnectionManager;
import by.it.dao.BasketDao;
import by.it.dao.impl.BasketDaoImpl;
import by.it.entities.Basket;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class BasketDaoTest {

    BasketDao basketDao = BasketDaoImpl.getInstance();
    Basket basket = new Basket(160L,8653L,1);

    @Test
    public void basketTest() throws SQLException {
        Connection connection = ConnectionManager.getConnection(2);
        connection.setAutoCommit(false);
        int before = basketDao.selectByIdUser(160L).size();
        Basket newBasket = basketDao.insert(basket);
        int after = basketDao.selectByIdUser(160L).size();
        Assert.assertEquals("inser не корректен",before,after - 1);

        newBasket.setQuantity(2);
        basketDao.update(newBasket);
        basket = basketDao.selectById(newBasket.getIdBasket());
        Assert.assertEquals("update не корректен",2,2);

        basketDao.delete(newBasket.getIdBasket());
        after = basketDao.selectByIdUser(160L).size();
        Assert.assertEquals("delete не корректен",before,after);

        connection.rollback();
    }
}
