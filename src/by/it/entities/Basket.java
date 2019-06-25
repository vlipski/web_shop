package by.it.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Basket {
    private long idBasket;
    private long idUser;
    private long idProduct;
    private int quantity;

    public Basket(long idUser, long idProduct, int quantity) {
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }
}
