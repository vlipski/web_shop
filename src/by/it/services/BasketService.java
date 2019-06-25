package by.it.services;

import by.it.dao.dto.BasketDto;
import by.it.entities.Basket;

import java.io.Serializable;
import java.util.List;

public interface BasketService {
    Basket createBasket(Basket basket);

    int deleteItemBasket(Serializable id);

    int deleteByIdUserBasket(Serializable id);

    List<BasketDto> selectBasketDtoByIdUser(long idUser);
}
