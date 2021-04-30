package me.ride.service;

import me.ride.entity.User;
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

    public List<Order> getListOfOrders(User user){
        return orderRepository.findAllByUser(user);
    }

    public boolean isOrderAllowed(Order order){
        System.out.println(order.getCar().getId());
        System.out.println(order.getFirstDay());
        System.out.println(order.getLastDay());
        System.out.println(orderRepository.findOrderByCarBetween(order.getCar().getId(), order.getFirstDay(), order.getLastDay()));
        return orderRepository.findOrderByCarBetween(order.getCar().getId(), order.getFirstDay(), order.getLastDay()).size() == 0;
    }
}
