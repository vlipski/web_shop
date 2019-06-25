package by.it.dao.impl;

import by.it.dao.BasketDao;
import by.it.dao.dto.BasketDto;
import by.it.dao.dto.OrderItemDto;
import by.it.entities.Basket;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BasketDaoImpl extends AbstractDao implements BasketDao {

    private static volatile BasketDao INSTANCE = null;

    private static final String insertSql = "INSERT INTO basket (ID_USER, ID_PRODUCT, QUANTITY) VALUES (?, ?, ?)";
    private static final String selectByIdSql = "SELECT * FROM BASKET WHERE ID_BASKET = ?";
    private static final String selectByIdUserSql = "SELECT * FROM basket WHERE ID_USER = ?";
    private static final String selectBasketDtoByIdUserSql = "SELECT basket.ID_USER, basket.ID_BASKET, producttv.FABRICATOR," +
            " producttv.MODEL,producttv.diagonal, producttv.PRICE, basket.QUANTITY \n" +
            " FROM myshop.basket\n" +
            " JOIN myshop.PRODUCTTV\n" +
            " ON basket.ID_PRODUCT = producttv.ID_PRODUCT\n" +
            " WHERE basket.ID_USER = ?";
    private static final String selectByIdUserIdProductSql = "SELECT * FROM basket WHERE ID_USER = ? AND ID_PRODUCT = ?";
    private static final String updateSql = "UPDATE BASKET SET ID_USER = ?, ID_PRODUCT = ?, QUANTITY = ?  WHERE ID_BASKET = ?";
    private static final String deleteSql = "DELETE FROM basket WHERE ID_BASKET = ?";
    private static final String deleteByIdUserSql = "DELETE FROM basket WHERE ID_USER = ?";

    private PreparedStatement prstInsert;
    private PreparedStatement prstSelect;
    private PreparedStatement prstSelectBtIdUser;
    private PreparedStatement prstByIdUserIdProduct;
    private PreparedStatement prstSelectBasketDtoByIdUser;
    private PreparedStatement prstDelete;
    private PreparedStatement prstDeleteByIdUser;
    private PreparedStatement prstUpdate;


    public static BasketDao getInstance() {
        BasketDao basketDao = INSTANCE;
        if (basketDao == null) {
            synchronized (OrderItemDaoImpl.class) {
                basketDao = INSTANCE;
                if (basketDao == null) {
                    INSTANCE = basketDao = new BasketDaoImpl();
                }
            }
        }
        return basketDao;
    }

    @Override
    protected Basket populatEntity(ResultSet result) throws SQLException {
        Basket basket = new Basket();
        basket.setIdBasket(result.getLong(1));
        basket.setIdUser(result.getLong(2));
        basket.setIdProduct(result.getLong(3));
        basket.setQuantity(result.getInt(4));
        return basket;
    }

    @Override
    public Basket insert(Basket basket) throws SQLException {
        prstInsert = preStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        prstInsert.setLong(1, basket.getIdUser());
        prstInsert.setLong(2, basket.getIdProduct());
        prstInsert.setInt(3, basket.getQuantity());
        prstInsert.executeUpdate();
        ResultSet results = prstInsert.getGeneratedKeys();
        if (results.next()) {
            basket.setIdBasket(results.getLong(1));
        }
        close(results);
        return basket;
    }

    @Override
    public Basket selectById(Serializable id) throws SQLException {
        return selectOne(prstSelect, selectByIdSql, id);
    }

    @Override
    public List<Basket> selectByIdUser(Serializable id) throws SQLException {
        return selectList(prstSelectBtIdUser, selectByIdUserSql, id);
    }
    @Override
    public Basket selectByIdUserIdProduct(Serializable idUser, Serializable idProduct) throws SQLException {
        prstByIdUserIdProduct = preStatement(selectByIdUserIdProductSql);
        prstByIdUserIdProduct.setLong(1, (long) idUser);
        prstByIdUserIdProduct.setLong(2, (long) idProduct);
        prstByIdUserIdProduct.executeQuery();
        ResultSet result = prstByIdUserIdProduct.getResultSet();
        if (result.next()) {
            return populatEntity(result);
        }
        close(result);
        return null;
    }

    @Override
    public void update(Basket basket) throws SQLException {
        prstUpdate = preStatement(updateSql);
        prstUpdate.setLong(1, basket.getIdUser());
        prstUpdate.setLong(2, basket.getIdProduct());
        prstUpdate.setInt(3, basket.getQuantity());
        prstUpdate.setLong(4, basket.getIdBasket());
        prstUpdate.executeUpdate();
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        prstDelete = preStatement(deleteSql);
        prstDelete.setLong(1, (long) id);
        return prstDelete.executeUpdate();
    }

    @Override
    public int deleteByIdUser(Serializable id) throws SQLException {
        prstDeleteByIdUser = preStatement(deleteByIdUserSql);
        prstDeleteByIdUser.setLong(1, (long) id);
        return prstDeleteByIdUser.executeUpdate();
    }

    @Override
    public BasketDto populatDto(ResultSet result) throws SQLException {
        BasketDto basketDto = new BasketDto();
        basketDto.setIdUser(result.getLong(1));
        basketDto.setIdBasket(result.getLong(2));
        basketDto.setFabricator(result.getString(3));
        basketDto.setModel(result.getString(4));
        basketDto.setDiagonal(result.getInt(5));
        basketDto.setPrice(result.getDouble(6));
        basketDto.setQuantity(result.getInt(7));
        return basketDto;
    }

    @Override
    public List<BasketDto> selectBasketDtoByIdUser(Serializable id) throws SQLException {
        List<BasketDto> list = new ArrayList<>();
        prstSelectBasketDtoByIdUser = preStatement(selectBasketDtoByIdUserSql);
        prstSelectBasketDtoByIdUser.setLong(1, (long) id);
        prstSelectBasketDtoByIdUser.execute();
        ResultSet result = prstSelectBasketDtoByIdUser.getResultSet();
        while (result.next()) {
            list.add(populatDto(result));
        }
        close(result);
        return list;
    }
}
