package by.it.dao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketDto {

    private long idUser;
    private long idBasket;
    private String fabricator;
    private String model;
    private int diagonal;
    private Double price;
    private int quantity;
}
