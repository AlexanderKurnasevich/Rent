package me.ride.entity.system;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Long id;
    private OrderStatus status;
    private Double damage;
    private String message;

    public OrderRequest() {
    }

    public OrderRequest(Long id, OrderStatus status, Double damage, String message) {
        this.id = id;
        this.status = status;
        this.damage = damage;
        this.message = message;
    }
}
