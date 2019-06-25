package services;


import by.it.connection.ConnectionManager;
import by.it.dao.OrderDao;
import by.it.dao.ProductTvDao;
import by.it.dao.UserDao;
import by.it.dao.impl.OrderDaoImpl;
import by.it.dao.impl.ProductTvDaoImpl;
import by.it.dao.impl.UserDaoImpl;
import by.it.entities.Basket;
import by.it.entities.ProductTv;
import by.it.entities.User;
import by.it.services.BasketService;
import by.it.services.OrderService;
import by.it.services.impl.BasketServiceImpl;
import by.it.services.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class BasketSwrviceTest {

    BasketService basketService = BasketServiceImpl.getInstance();
    UserDao userDao = UserDaoImpl.getInstance();
    User user = new User();
    List<User> listUser;
    ProductTvDao productTvDao = ProductTvDaoImpl.getInstance();
    ProductTv productTv = new ProductTv();
    List<ProductTv> listProductTv;
    OrderService orderService = OrderServiceImpl.getInstance();
    OrderDao orderDao = OrderDaoImpl.getInstance();
    Basket basket = new Basket();


    @Test
    public void basketServiceTest() throws SQLException {
       /* Connection connection = ConnectionManager.getConnection();
        connection.setAutoCommit(false);
        listUser = userDao.selectAll();
        user = listUser.get((int) (Math.random() * listUser.size()));
        listProductTv = productTvDao.selectAll();
        for (int i = 0; i < 3; i++) {
            productTv = listProductTv.get((int) (Math.random() * listProductTv.size()));
            basketService.createBasket(new Basket(user.getIdUser(), productTv.getIdProduct(), (int) (Math.random() * 2)));
        }
        connection.commit();*/

        Connection connection1 = ConnectionManager.getConnection(2);
        connection1.setAutoCommit(false);
        int before = basketService.selectBasketDtoByIdUser(159L).size();
        basket = basketService.createBasket(new Basket(159L,8653,1));
        int after = basketService.selectBasketDtoByIdUser(159L).size();
        Assert.assertEquals("createBasket не корректен",before,after - 1);

        basketService.deleteItemBasket(basket.getIdBasket());
        after = basketService.selectBasketDtoByIdUser(159L).size();
        Assert.assertEquals("deleteItemBasket не корректен", before,after);

        basketService.createBasket(new Basket(159L,8653L,1));
        basketService.createBasket(new Basket(159L,8654L,1));
        basketService.deleteByIdUserBasket(159L);
        after = basketService.selectBasketDtoByIdUser(159L).size();
        Assert.assertEquals("deleteByIdUser не корректен" ,0,after );
        connection1.rollback();
    }
}
