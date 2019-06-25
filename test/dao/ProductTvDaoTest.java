package dao;


import by.it.connection.ConnectionManager;
import by.it.dao.ProductTvDao;
import by.it.dao.impl.ProductTvDaoImpl;
import by.it.entities.ProductTv;
import org.junit.Assert;
import org.junit.Test;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductTvDaoTest {

    ProductTvDao productTvDao = ProductTvDaoImpl.getInstance();

    @Test
    public void generalTest() throws SQLException {
        Connection connection = ConnectionManager.getConnection(2);
        connection.setAutoCommit(false);
        int start = productTvDao.selectAll().size();
        productTvDao.insert(new ProductTv("sony", "smart65", 32, 900.));
        productTvDao.insert(new ProductTv("sony", "smart65", 42, 1200.));
        int finish = productTvDao.selectAll().size();
        Assert.assertEquals("метод selectAll и insert не корректен", start, finish - 2);

        ProductTv productTv = productTvDao.insert(new ProductTv("sony", "smart65", 32, 900.));
        ProductTv newProductTv = productTvDao.selectById(productTv.getIdProduct());
        Assert.assertEquals("метод selectID не корректен", productTv, newProductTv);

        productTv.setFabricator("filips");
        productTvDao.update(productTv);
        newProductTv = productTvDao.selectById(productTv.getIdProduct());
        Assert.assertEquals("метод update не корретен", "filips", newProductTv.getFabricator());

        productTvDao.delete(productTv.getIdProduct());
        Assert.assertNull("метод delete не корректен",productTvDao.selectById(productTv.getIdProduct()));
        connection.rollback();
    }

    @Test
    public void sort() throws SQLException {
        Statement stat = ConnectionManager.getConnection(2).createStatement();
        stat.execute("SELECT * FROM PRODUCTTV");
        ResultSet result = stat.getResultSet();
        System.out.println(result);
        while (result.next()) {
            System.out.println((result));
        }


    }
}
