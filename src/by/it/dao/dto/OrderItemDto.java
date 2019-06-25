package by.it.dao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {

    private long IdOrder;
    private String fabricaeor;
    private String model;
    private Double price;
    private int quantity;

}
