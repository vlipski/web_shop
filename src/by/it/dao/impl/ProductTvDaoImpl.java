package by.it.dao.impl;

import by.it.connection.ConnectionManager;
import by.it.dao.ProductTvDao;
import by.it.entities.ProductTv;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductTvDaoImpl extends AbstractDao implements ProductTvDao {
    private static volatile ProductTvDao INSTANCE = null;

    private static final String insertSql = "INSERT INTO PRODUCTTV (FABRICATOR, MODEL, DIAGONAL,PRICE, BALANS) VALUES (?, ?, ?, ?, ?)";
    private static final String selectByIdSql = "SELECT * FROM PRODUCTTV WHERE ID_PRODUCT = ?";
    private static final String selectAllSql = "SELECT * FROM PRODUCTTV";
    private static final String selectByFabricatorAndModelSql = "SELECT * FROM PRODUCTTV WHERE FABRICATOR = ? AND MODEL = ? AND DIAGONAL = ?";
    private static final String selectNameFabricatorSql = "SELECT DISTINCT FABRICATOR FROM PRODUCTTV ";
    private static final String selectByBalansSql = "SELECT * FROM PRODUCTTV WHERE BALANS BETWEEN ? AND ?";
    private static final String selectByPriceSql = "SELECT * FROM PRODUCTTV WHERE PRICE BETWEEN ? AND ? order by PRICE";
    private static final String selectByFabricatorSql = "SELECT * FROM PRODUCTTV WHERE FABRICATOR = ?";
    private static final String updateSql = "UPDATE PRODUCTTV SET FABRICATOR =?, MODEL = ?, DIAGONAL = ?, PRICE = ?, BALANS = ? WHERE ID_PRODUCT = ?";
    private static final String deleteSql = "DELETE FROM PRODUCTTV WHERE ID_PRODUCT = ?";

    private PreparedStatement prstInsert;
    private PreparedStatement prstSelectById;
    private PreparedStatement prstSelectByFabricatorAndModel;
    private PreparedStatement prstSelectAll;
    private PreparedStatement prstSelectNameFabricator;
    private PreparedStatement prstSelectByBalans;
    private PreparedStatement prstSelectByPrice;
    private PreparedStatement prstSelectByFabricator;
    private PreparedStatement prstUpdate;
    private PreparedStatement prstDelete;
    private Statement stat;


    public static ProductTvDao getInstance() {
        ProductTvDao productTvDao = INSTANCE;
        if (productTvDao == null) {
            synchronized (ProductTvDaoImpl.class) {
                productTvDao = INSTANCE;
                if (productTvDao == null) {
                    INSTANCE = productTvDao = new ProductTvDaoImpl();
                }
            }
        }
        return productTvDao;
    }

    @Override
    protected ProductTv populatEntity(ResultSet result) throws SQLException {
        ProductTv productTv = new ProductTv();
        productTv.setIdProduct(result.getLong(1));
        productTv.setFabricator(result.getString(2));
        productTv.setModel(result.getString(3));
        productTv.setDiagonal(result.getInt(4));
        productTv.setPrice(result.getDouble(5));
        productTv.setBalans(result.getInt(6));
        return productTv;
    }

    @Override
    public ProductTv insert(ProductTv productTv) throws SQLException {
        prstInsert = preStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        prstInsert.setString(1, productTv.getFabricator());
        prstInsert.setString(2, productTv.getModel());
        prstInsert.setInt(3, productTv.getDiagonal());
        prstInsert.setDouble(4, productTv.getPrice());
        prstInsert.setInt(5, productTv.getBalans());
        prstInsert.executeUpdate();
        ResultSet results = prstInsert.getGeneratedKeys();
        if (results.next()) {
            productTv.setIdProduct(results.getLong(1));
        }
        close(results);
        return productTv;
    }

    @Override
    public ProductTv selectById(Serializable id) throws SQLException {
        return selectOne(prstSelectById, selectByIdSql, id);
        /*prstSelectById = preStatement(selectByIdSql);
        prstSelectById.setLong(1, (long) id);
        prstSelectById.executeQuery();
        ResultSet result = prstSelectById.getResultSet();
        if (result.next()) {
            return populatEntity(result);
        }
        close(result);
        return null;*/
    }

    @Override
    public ProductTv selectByFabricatorAndModel(String fabricator, String model, int diagonal) throws SQLException {
        prstSelectByFabricatorAndModel = preStatement(selectByFabricatorAndModelSql);
        prstSelectByFabricatorAndModel.setString(1, fabricator);
        prstSelectByFabricatorAndModel.setString(2, model);
        prstSelectByFabricatorAndModel.setInt(3, diagonal);
        prstSelectByFabricatorAndModel.executeQuery();
        ResultSet result = prstSelectByFabricatorAndModel.getResultSet();
        if (result.next()) {
            return populatEntity(result);
        }
        close(result);
        return null;
    }

    @Override
    public List<ProductTv> selectByBalans(int min, int max) throws SQLException {
        return selectList(prstSelectByBalans, selectByBalansSql, min, max);
    }

    @Override
    public List<ProductTv> selectByPrice(int min, int max) throws SQLException {
        return selectList(prstSelectByPrice, selectByPriceSql, min, max);
    }

    @Override
    public List<ProductTv> selectAll() throws SQLException {
        return selectList(prstSelectAll, selectAllSql);
    }

    @Override
    public List<String> selectNameFabricator() throws SQLException {
        List<String> list = new ArrayList<>();
        prstSelectNameFabricator = preStatement(selectNameFabricatorSql);
        prstSelectNameFabricator.execute();
        ResultSet result = prstSelectNameFabricator.getResultSet();
        while (result.next()) {
            list.add(result.getString(1));
        }
        close(result);
        return list;
    }

    @Override
    public List<ProductTv> selectByFabricator(String fabricator) throws SQLException {
        return selectList(prstSelectByFabricator, selectByFabricatorSql, fabricator);
    }

    @Override
    public void update(ProductTv productTv) throws SQLException {
        prstUpdate = preStatement(updateSql);
        prstUpdate.setString(1, productTv.getFabricator());
        prstUpdate.setString(2, productTv.getModel());
        prstUpdate.setInt(3, productTv.getDiagonal());
        prstUpdate.setDouble(4, productTv.getPrice());
        prstUpdate.setInt(5, productTv.getBalans());
        prstUpdate.setLong(6, productTv.getIdProduct());
        prstUpdate.executeUpdate();
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        prstDelete = preStatement(deleteSql);
        prstDelete.setLong(1, (long) id);
        return prstDelete.executeUpdate();
    }

    @Override
    public List<ProductTv> sort(String minMax, String[] fabricator, String price, String diagonal) throws SQLException {
        List<ProductTv> list = new ArrayList<>();
        Statement stat = ConnectionManager.getConnection(2).createStatement();
        stat.executeQuery(doSQL(minMax, fabricator, price, diagonal));
        ResultSet result = stat.getResultSet();
        while (result.next()) {
            list.add(populatEntity(result));
        }
        close(result);
        return list;
    }

    private String doSQL(String sortPrice, String[] fabricator, String price, String diagonal) {
        boolean flag = false;
        StringBuilder build = new StringBuilder("SELECT * FROM PRODUCTTV WHERE");
        if (fabricator != null) {
            build.append(" ( ");
            for (int i = 0; i < fabricator.length; i++) {
                build.append("FABRICATOR = " + "'" + fabricator[i] + "'" + (i != fabricator.length - 1 ? " OR " : ") "));
            }
            flag = true;
        }
        if (price != null) {
            String[] minMaxArray = minMax(price);
            build.append((fabricator != null ? " AND " : "") + "  PRICE BETWEEN " + (minMaxArray[0] != "" ? minMaxArray[0] : "500")
                    + " AND " + (minMaxArray.length == 2 ? minMaxArray[1] : "2000"));
            flag = true;
        }
        if (diagonal != null) {
            String[] minMaxArray = minMax(diagonal);
            build.append((price != null ? " AND " : "") + " DIAGONAL BETWEEN " + (minMaxArray[0] != "" ? minMaxArray[0] : "19")
                    + " AND " + (minMaxArray.length == 2 ? minMaxArray[1] : "70"));
            flag = true;
        }
        if (sortPrice != null) {
            build.append(" order by PRICE " + sortPrice);
        }
        if (flag) {
            return String.valueOf(build);
        } else return "SELECT * FROM PRODUCTTV";
    }

    private String[] minMax(String minMax) {
        String delimeter = ";";
        return minMax.split(delimeter);
    }


}
