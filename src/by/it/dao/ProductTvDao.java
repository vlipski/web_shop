package by.it.dao;

import by.it.entities.ProductTv;

import java.sql.SQLException;
import java.util.List;

public interface ProductTvDao extends DAO<ProductTv> {

    ProductTv selectByFabricatorAndModel(String fabricator, String model, int diagonal) throws SQLException;

    List<ProductTv> selectByBalans(int min, int max) throws SQLException;

    List<ProductTv> selectByPrice(int min, int max) throws SQLException;

    List<ProductTv> selectAll() throws SQLException;

    List<String> selectNameFabricator() throws SQLException;

    List<ProductTv> selectByFabricator(String fabricator) throws SQLException;


    List<ProductTv> sort(String minMax, String[] fabricator, String price, String diagonal) throws SQLException;
}
