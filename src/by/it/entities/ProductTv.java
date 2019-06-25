package by.it.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductTv {
    private long idProduct;
    private String fabricator;
    private String model;
    private int diagonal;
    private Double price;
    private int balans;

    public ProductTv(String fabricator, String model,int diagonal, Double price) {
        this.fabricator = fabricator;
        this.model = model;
        this.diagonal = diagonal;
        this.price = price;
    }
}
