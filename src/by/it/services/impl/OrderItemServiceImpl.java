package by.it.services.impl;

import by.it.dao.OrderItemDao;
import by.it.dao.impl.OrderItemDaoImpl;
import by.it.services.OrderItemService;

public class OrderItemServiceImpl extends AbstractServices implements OrderItemService {
    private OrderItemDao orderItemDao = OrderItemDaoImpl.getInstance();


    /*public int createItemsOrder(List<Basket> listBasket) {

    }
*/
}
