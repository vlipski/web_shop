package by.it.dao;

import by.it.dao.dto.BasketDto;
import by.it.entities.Basket;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BasketDao extends DAO<Basket> {
    List<Basket> selectByIdUser(Serializable id) throws SQLException;

    Basket selectByIdUserIdProduct(Serializable idUser, Serializable idProduct) throws SQLException;

    int deleteByIdUser(Serializable id) throws SQLException;

    BasketDto populatDto(ResultSet result) throws SQLException;

    List<BasketDto> selectBasketDtoByIdUser(Serializable id) throws SQLException;
}
