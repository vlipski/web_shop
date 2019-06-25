package by.it.services;

import by.it.entities.Basket;
import by.it.entities.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    Order createOrder(long idUser);

    List<Order> selectOrderByIdUser(long idUser);
}
