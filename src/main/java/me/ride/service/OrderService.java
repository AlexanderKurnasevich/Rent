package me.ride.service;

import me.ride.entity.User;
import me.ride.entity.car.Car;
import me.ride.entity.system.Damage;
import me.ride.entity.system.Order;
import me.ride.entity.system.OrderStatus;
import me.ride.entity.system.RefuseNote;
import me.ride.exception.CarNotFoundException;
import me.ride.exception.OrderNotFoundException;
import me.ride.repository.DamageRepository;
import me.ride.repository.OrderRepository;
import me.ride.repository.RefuseNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RefuseNoteRepository refuseNoteRepository;

    @Autowired
    private DamageRepository damageRepository;

    public void save(Order order){
        orderRepository.save(order);
    }

    public void saveRefuseNote(Long orderId, String message){
        refuseNoteRepository.save(new RefuseNote(orderRepository.getOrderById(orderId), message));
    }

    public RefuseNote findRefuseNote(Order order){
        return refuseNoteRepository.findRefuseNoteByOrder(order);
    }

    @Transactional
    public void updateStatus(Long orderId, OrderStatus status){  //если не найду как вырубить валидатор
        orderRepository.updateStatus(orderId, status);
    }

    public Order show(Long id) throws OrderNotFoundException {
        Order order = orderRepository.getOrderById(id);
        if (order == null) {
            throw new OrderNotFoundException("Order not found");
        }
        return order;
    }

    public List<Order> getListOfOrders(User user){
        return orderRepository.findAllByUser(user);
    }

    public List<Order> getListOfAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getListOfOrdersDateBetween(Date date1, Date date2){
        return orderRepository.findOrdersByFirstDayBetweenOrLastDayBetween(date1, date2);
    }

    public void processOrder(Long id, OrderStatus orderStatus) throws OrderNotFoundException {
        Order order = show(id);
        order.setOrderStatus(orderStatus);
        save(order);
    }

    public void refuseOrder(Long id) throws OrderNotFoundException {
        Order order = show(id);
        order.setOrderStatus(OrderStatus.REFUSED);
        save(order);
    }

    public void acceptOrder(Long id) throws OrderNotFoundException {
        Order order = show(id);
        order.setOrderStatus(OrderStatus.ACCEPTED);
        save(order);
    }

    public boolean isOrderAllowed(Order order){
//        System.out.println(order.getCar().getId());
//        System.out.println(order.getFirstDay());
//        System.out.println(order.getLastDay());
//        System.out.println(orderRepository.findOrderByCarBetween(order.getCar().getId(), order.getFirstDay(), order.getLastDay()));
        return orderRepository.findOrderByCarBetween(order.getCar().getId(), order.getFirstDay(), order.getLastDay()).size() == 0;
    }

    public void saveDamage(Damage damage){
        damageRepository.save(damage);
    }

}
