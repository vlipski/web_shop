
import by.it.connection.ConnectionManager;
import by.it.dao.ProductTvDao;
import by.it.dao.impl.ProductTvDaoImpl;
import by.it.entities.ProductTv;
import org.junit.Test;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimeRecordTest {
    ProductTvDao productTvDao = ProductTvDaoImpl.getInstance();
    PreparedStatement prstInsert;
    ProductTv newProductTv;

    String[] fabricator = {"HORIZONT", "LG", "PANASONIC", "SAMSUNG", "SONY", "BBK", "JVC", "PHILIPS", "TOSHIBA", "THOMSON"};
    String[] model = {"20MT48VF-PZ", "24LE7911D", "32S2855EC", "LT-24M440W", "UE24H4070AU", "32PHS4012/12", "22MT58VF-PZ", "TX-32DR300ZZ", "LT-32M355", "TX-32FR250W"};
    int[] diagonal = {19, 22, 27, 32, 40, 42, 45, 50, 55, 60, 65, 70};

    @Test
    public void hashCod() throws SQLException {
        Set<ProductTv> productTvs =  new HashSet<>();
        ProductTv productTv1 = new ProductTv();
        ProductTv productTv2 = new ProductTv();
        productTv1.setDiagonal(1);
        productTv2.setDiagonal(2);
        System.out.println(productTv1.hashCode());
        System.out.println(productTv2.hashCode());
        productTvs.add(productTv1);
        productTvs.add(productTv2);
        System.out.println(productTvs);
        productTv2.setDiagonal(1);
        System.out.println(productTv2.hashCode());
        System.out.println(productTvs);
        productTvs.remove(productTv2);
        System.out.println(productTvs);
        System.out.println(productTv1.hashCode());
        System.out.println(productTv2.hashCode());
        productTvs.add(productTv2);
        System.out.println(productTvs);
    }


    @Test
    public void insertProductTv() throws SQLException {
        Connection connection = ConnectionManager.getConnection(2);
        connection.setAutoCommit(false);
        for (; productTvDao.selectAll().size() < 15; ) {
            ProductTv productTv = new ProductTv();
            productTv.setFabricator(fabricator[(int) (Math.random() * 10)]);
            productTv.setModel(model[(int) (Math.random() * 10)]);
            productTv.setDiagonal(diagonal[(int) (Math.random() * 12)]);
            productTv.setPrice((500 + Math.random() * 2000));
            productTv.setBalans((int) (1 + Math.random() * 20));
            newProductTv = productTvDao.selectByFabricatorAndModel(productTv.getFabricator(), productTv.getModel(), productTv.getDiagonal());
            if (newProductTv != null) {
                productTv.setBalans(productTv.getBalans() + newProductTv.getBalans());
                productTv.setIdProduct(newProductTv.getIdProduct());
                productTvDao.update(productTv);
            } else {
                productTvDao.insert(productTv);
            }
        }
        connection.commit();
    }


    @Test
    public void recordTest() throws SQLException {
        long startBuild = System.currentTimeMillis();
        //     record1();         //без commit executUpdate        5800
//        record2();      //commit сразу всех executUpdate   4800
        //      record3();         //commit через сто executUpdate 3800
        record4();       //commit сразу всех executBatch 3600
        long finishBuild = System.currentTimeMillis() - startBuild;
        System.out.println(finishBuild);
    }

    public void record1() throws SQLException {
        for (int i = 0; i < 1000; i++) {
            productTvDao.insert(new ProductTv("sony", "smart65", 32, 900.));
        }
    }

    public void record2() throws SQLException {
        Connection connection = ConnectionManager.getConnection(2);
        connection.setAutoCommit(false);
        for (int i = 0; i < 1000; i++) {
            productTvDao.insert(new ProductTv("sony", "smart65", 32, 900.));
        }
        connection.commit();
    }

    public void record3() throws SQLException {
        for (int i = 0; i < 10; i++) {
            Connection connection = ConnectionManager.getConnection(2);
            connection.setAutoCommit(false);
            insert(new ProductTv("sony", "smart65", 32, 900.));
            connection.commit();
        }
    }

    public void record4() throws SQLException {
        Connection connection = ConnectionManager.getConnection(2);
        connection.setAutoCommit(false);
        for (int i = 0; i < 10; i++) {
            insert(new ProductTv("sony", "smart65", 32, 900.));
        }
        connection.commit();
    }


    public void insert(ProductTv productTv) throws SQLException {
        Connection connection = ConnectionManager.getConnection(2);
        prstInsert = connection.prepareStatement("INSERT INTO PRODUCTTV (FABRICATOR, MODEL, DIAGONAL,PRICE) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < 100; i++) {
            prstInsert.setString(1, productTv.getFabricator());
            prstInsert.setString(2, productTv.getModel());
            prstInsert.setInt(3, productTv.getDiagonal());
            prstInsert.setDouble(4, productTv.getPrice());
            prstInsert.addBatch();
        }
        prstInsert.executeBatch();
    }
}
