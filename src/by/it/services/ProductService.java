package by.it.services;


import by.it.entities.ProductTv;

import java.util.List;

public interface ProductService {

    List<ProductTv> sort(String minMax, String[] fabricator, String price, String diagonal);

    List<ProductTv> selectAllProduct();

    List<ProductTv> selectByPrice(int min, int max);

    List<ProductTv> selectByFabricator(String fabricator);

    List<String> selectNameFabricator();

    ProductTv selectByFabricatorAndModel(String fabricator, String model, int diagonal);
}
