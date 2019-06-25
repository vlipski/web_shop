package by.it.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItem {
    private long IdOrderItem;
    private long IdOrder;
    private long IdProduct;
    private int quantity;

    public OrderItem( long idOrder, long idProduct, int quantity) {
        IdOrder = idOrder;
        IdProduct = idProduct;
        this.quantity = quantity;
    }
}
