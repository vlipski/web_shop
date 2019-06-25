package by.it.entities;

import by.it.dao.dto.OrderItemDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class Order {
    private long IdOrder;
    private long IdUser;
    private List<OrderItemDto> orderItemsDto = new ArrayList<>();
    private double total;
    private Timestamp data;

    public Order(long idUser, double total) {
        this.IdUser = idUser;
        this.total = total;
    }
}
