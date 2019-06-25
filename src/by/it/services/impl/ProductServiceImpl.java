package by.it.services.impl;

import by.it.dao.ProductTvDao;
import by.it.dao.impl.ProductTvDaoImpl;
import by.it.entities.ProductTv;
import by.it.services.ProductService;
import by.it.services.ServiceException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl extends AbstractServices implements ProductService {

    private static final Logger log = Logger.getLogger(BasketServiceImpl.class);
    private static volatile ProductService INSTANCE = null;
    ProductTvDao productTvDao = ProductTvDaoImpl.getInstance();


    public static ProductService getInstance() {
        ProductService productService = INSTANCE;
        if (productService == null) {
            synchronized (ProductServiceImpl.class) {
                productService = INSTANCE;
                if (productService == null) {
                    INSTANCE = productService = new ProductServiceImpl();
                }
            }
        }
        return productService;
    }

    @Override
    public List<ProductTv> sort(String minMax, String[] fabricator, String price, String diagonal) {
        try {
            startTransaction(2);
            List<ProductTv> productTvs = productTvDao.sort(minMax, fabricator, price, diagonal);
            commit(2);
            if (productTvs.size() == 0) {
                throw new ServiceException("error.sort");
            }
            return productTvs;
        } catch (SQLException e) {
            rollback(2);
            log.error(e);
            throw new ServiceException("error.sort");
        }
    }

    @Override
    public List<ProductTv> selectAllProduct() {
        try {
            startTransaction(2);
            List<ProductTv> list = productTvDao.selectAll();
            commit(2);
            return list;
        } catch (SQLException e) {
            rollback(2);
            log.error(e);
            throw new ServiceException("Error getting Products");
        }
    }

    @Override
    public List<ProductTv> selectByPrice(int min, int max) {
        try {
            startTransaction(2);
            List<ProductTv> list = productTvDao.selectByPrice(min, max);
            commit(2);
            return list;
        } catch (SQLException e) {
            rollback(2);
            log.error(e);
            throw new ServiceException("Error getting Products");
        }
    }

    @Override
    public List<ProductTv> selectByFabricator(String fabricator) {
        try {
            startTransaction(2);
            List<ProductTv> list = productTvDao.selectByFabricator(fabricator);
            commit(2);
            return list;
        } catch (SQLException e) {
            rollback(2);
            log.error(e);
            throw new ServiceException("Error getting Products");
        }
    }

    @Override
    public List<String> selectNameFabricator() {
        try {
            startTransaction(2);
            List<String> list = productTvDao.selectNameFabricator();
            commit(2);
            return list;
        } catch (SQLException e) {
            rollback(2);
            log.error(e);
            throw new ServiceException("Error getting Products");
        }
    }

    @Override
    public ProductTv selectByFabricatorAndModel(String fabricator, String model, int diagonal) {
        try {
            return productTvDao.selectByFabricatorAndModel(fabricator, model, diagonal);
        } catch (SQLException e) {
            log.error(e);
            throw new ServiceException("Error getting by Supplier:" + fabricator + " and Model:" + model);
        }
    }


}
