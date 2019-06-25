package by.it.services.impl;

import by.it.dao.BasketDao;
import by.it.dao.OrderDao;
import by.it.dao.OrderItemDao;
import by.it.dao.ProductTvDao;
import by.it.dao.dto.OrderItemDto;
import by.it.dao.impl.BasketDaoImpl;
import by.it.dao.impl.OrderDaoImpl;
import by.it.dao.impl.OrderItemDaoImpl;
import by.it.dao.impl.ProductTvDaoImpl;
import by.it.entities.Basket;
import by.it.entities.Order;
import by.it.entities.OrderItem;
import by.it.entities.ProductTv;
import by.it.services.OrderService;
import by.it.services.ServiceException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl extends AbstractServices implements OrderService {

    private static final Logger log = Logger.getLogger(BasketServiceImpl.class);

    private static volatile OrderService INSTANCE = null;
    private BasketDao basketDao = BasketDaoImpl.getInstance();
    private OrderDao orderDao = OrderDaoImpl.getInstance();
    private ProductTvDao productTvDao = ProductTvDaoImpl.getInstance();
    private OrderItemDao orderItemDao = OrderItemDaoImpl.getInstance();
    private PaymentService paymentService = PaymentService.getInstance();

    public static OrderService getInstance() {
        OrderService orderService = INSTANCE;
        if (orderService == null) {
            synchronized (OrderServiceImpl.class) {
                orderService = INSTANCE;
                if (orderService == null) {
                    INSTANCE = orderService = new OrderServiceImpl();
                }
            }
        }
        return orderService;
    }

    private double checkingBalancesProduct(List<Basket> listBasket) throws SQLException {
        boolean flag = true;
        double summa = 0;
        for (Basket basket : listBasket) {
            ProductTv productTv = productTvDao.selectById(basket.getIdProduct());
            if (basket.getQuantity() > productTv.getBalans()) {
                flag = false;
                System.out.println("в наличии осталось: " + productTv.getBalans() + " " + productTv.getFabricator() + " " + productTv.getModel());
                throw new ServiceException("basket.noGoods");
            } else {
                productTv.setBalans(productTv.getBalans() - basket.getQuantity());
                productTvDao.update(productTv);
            }
            summa += basket.getQuantity() * productTv.getPrice();
        }
        if (flag) {
            return summa;
        } else return 0;
    }

    private boolean checekingBasket(List<Basket> listBasket) throws SQLException {
        if (listBasket.size() == 0) {
            System.out.println("корзина пуста");
            throw new ServiceException("basket.empty");
        }
        return true;
    }

    @Override
    public Order createOrder(long idUser) {
        List<Basket> listBasket;
        Order order = null;
        try {
            startTransaction(4);
            listBasket = basketDao.selectByIdUser(idUser);
            if (checekingBasket(listBasket)) {
                double summa = checkingBalancesProduct(listBasket);
                if (summa > 0 && paymentService.payment()) {
                    order = orderDao.insert(new Order(idUser, summa));
                    for (Basket basket : listBasket) {
                        orderItemDao.insert(new OrderItem(order.getIdOrder(), basket.getIdProduct(), basket.getQuantity()));
                    }
                    basketDao.deleteByIdUser(idUser);
                    commit(4);
                }
            }
            rollback(4);
        } catch (SQLException e) {
            rollback(4);
            log.error(e);
            throw new ServiceException("Error create Order");
        } catch (ServiceException e) {
            rollback(4);
            throw new ServiceException(e.getMessage());
        }
        return order;
    }

    @Override
    public List<Order> selectOrderByIdUser(long idUser) {
        try {
            startTransaction(2);
            List<Order> listOrder = orderDao.selectByIdUser(idUser);
            for (Order order : listOrder) {
                List<OrderItemDto> listItemDto = orderItemDao.selectOrderItemDtoByIdOrder(order.getIdOrder());
                order.setOrderItemsDto(listItemDto);
            }
            commit(2);
            return listOrder;
        } catch (SQLException e) {
            rollback(2);
            log.error(e);
            throw new ServiceException("Error celect orderByIdUser" + idUser);
        }
    }
}
