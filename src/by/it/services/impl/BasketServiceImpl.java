package by.it.services.impl;

import by.it.dao.BasketDao;
import by.it.dao.ProductTvDao;
import by.it.dao.dto.BasketDto;
import by.it.dao.dto.OrderItemDto;
import by.it.dao.impl.BasketDaoImpl;
import by.it.dao.impl.ProductTvDaoImpl;
import by.it.entities.Basket;
import by.it.entities.Order;
import by.it.entities.ProductTv;
import by.it.services.BasketService;
import by.it.services.ServiceException;
import org.apache.log4j.Logger;
import sun.util.logging.PlatformLogger;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;


public class BasketServiceImpl extends AbstractServices implements BasketService {

    private static final Logger log = Logger.getLogger(BasketServiceImpl.class);

    private static volatile BasketService INSTANCE = null;
    private BasketDao basketDao = BasketDaoImpl.getInstance();
    private ProductTvDao productTvDao = ProductTvDaoImpl.getInstance();


    public static BasketService getInstance() {
        BasketService basketService = INSTANCE;
        if (basketService == null) {
            synchronized (BasketServiceImpl.class) {
                basketService = INSTANCE;
                if (basketService == null) {
                    INSTANCE = basketService = new BasketServiceImpl();
                }
            }
        }
        return basketService;
    }

    @Override
    public Basket createBasket(Basket basket) {
        int balans;
        ProductTv productTv;
        if (basket.getQuantity() < 1) {
            basket.setQuantity(1);
        }
        try {
            startTransaction(2);
            productTv = productTvDao.selectById(basket.getIdProduct());
            if (productTv == null) {
                basket = null;
                commit(4);
                return basket;
            }
            balans = productTv.getBalans();
            if (basket.getQuantity() > balans) {
                basket = null;
            } else {
                if (updateBasket(basket)) {
                    basket = basketDao.insert(basket);
                }
            }
            commit(2);
            return basket;
        } catch (SQLException e) {
            rollback(2);
            log.error(e);
            throw new ServiceException("Error creating Item" + basket);
        }
    }

    private boolean updateBasket(Basket basket) {
        Basket itemBasket;
        try {
            startTransaction(2);
            itemBasket = basketDao.selectByIdUserIdProduct(basket.getIdUser(), basket.getIdProduct());
            if (itemBasket == null) {
                commit(2);
                return true;
            } else {
                itemBasket.setQuantity(itemBasket.getQuantity() + basket.getQuantity());
                basketDao.update(itemBasket);
                commit(2);
                return false;
            }
        } catch (SQLException e) {
            rollback(2);
            log.error(e);
            throw new ServiceException("Error creating Basket" + basket);
        }
    }

    @Override
    public int deleteItemBasket(Serializable id) {
        try {
            startTransaction(2);
            Basket basket = basketDao.selectById(id);
            if (basket == null) {
                commit(2);
                return 0;
            }
            if (basket.getQuantity() > 1) {
                basket.setQuantity(basket.getQuantity() - 1);
                basketDao.update(basket);
                commit(2);
                return 1;
            }
            return basketDao.delete(id);
        } catch (SQLException e) {
            log.error(e);
            throw new ServiceException("Error deleting itemBasket by id" + id);
        }
    }

    @Override
    public int deleteByIdUserBasket(Serializable id) {
        try {
            return basketDao.deleteByIdUser(id);
        } catch (SQLException e) {
            log.error(e);
            throw new ServiceException("Error deleting Basket by idUser" + id);
        }
    }

    @Override
    public List<BasketDto> selectBasketDtoByIdUser(long idUser) {
        try {
            startTransaction(2);
            List<BasketDto> listBasketDto = basketDao.selectBasketDtoByIdUser(idUser);
            commit(2);
            return listBasketDto;
        } catch (SQLException e) {
            log.error(e);
            rollback(2);
            throw new ServiceException("Error select basketDtoByIdUser" + idUser);
        }
    }

}
