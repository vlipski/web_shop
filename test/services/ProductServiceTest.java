package services;

import by.it.connection.ConnectionManager;
import by.it.dao.ProductTvDao;
import by.it.dao.impl.ProductTvDaoImpl;
import by.it.entities.ProductTv;
import by.it.services.ProductService;
import by.it.services.impl.ProductServiceImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductServiceTest {

    ProductService productService = ProductServiceImpl.getInstance();
    ProductTvDao productTvDao = ProductTvDaoImpl.getInstance();

    @Test
    public void productService() throws SQLException {
        Connection connection1 = ConnectionManager.getConnection(2);
        connection1.setAutoCommit(false);
        int before = productService.selectAllProduct().size();
        connection1.rollback();
    }
}
