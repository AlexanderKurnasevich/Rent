package me.ride.service;

import me.ride.entity.system.Order;
import me.ride.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void save(Order order){
        orderRepository.save(order);
    }

    public boolean isOrderAllowed(Order order){
        return orderRepository.findOrderByCarBetween(order.getCar().getId(), order.getFirstDay(), order.getLastDay()).size() == 0;
    }
}
