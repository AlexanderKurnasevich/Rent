package me.ride.service;

import me.ride.controller.OrderRequest;
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

    @Autowired
    private EmailService emailService;

    public void save(Order order){
        orderRepository.save(order);
    }

    public void saveRefuseNote(Long orderId, String message){
        refuseNoteRepository.save(new RefuseNote(orderRepository.getOrderById(orderId), message));
    }

    public RefuseNote findRefuseNote(Order order){
        return refuseNoteRepository.findRefuseNoteByOrder(order);
    }

    public void updateStatus(Long orderId, OrderStatus status){
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

    /*@Transactional
    public void processOrder(Long id, OrderStatus orderStatus) throws OrderNotFoundException {
        orderRepository.updateStatus(id, orderStatus);
        switch (action) {
            case "refuse":
                updateStatus(orderId, OrderStatus.REFUSED);
                if (message != null)
                    refuseNoteRepository.save()
                    orderService.saveRefuseNote(orderId, message);
                break;
            case "accept":
                orderService.updateStatus(orderId, OrderStatus.ACCEPTED);
                break;
            case "damaged":
                orderService.updateStatus(orderId, OrderStatus.CAR_DAMAGED);
                if(damage != null) {
                    try {
                        orderService.saveDamage(new Damage(orderService.show(orderId).getCar(), orderService.show(orderId), damage));
                    } catch (OrderNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
                break;
            case "return":
                orderService.updateStatus(orderId, OrderStatus.RETURNED);
                break;
        }

        Order order = show(id);
        order.setOrderStatus(orderStatus);
        save(order);
    }*/

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

    public boolean isCarFreeByOrders(Long carId, Date dateFrom, Date dateTo){
        return orderRepository.findOrderByCarBetween(carId, dateFrom, dateTo).isEmpty();
    }

    public void saveDamage(Damage damage){
        damageRepository.save(damage);
    }

    @Transactional
    public void processOrder(OrderRequest orderRequest) {
        switch (orderRequest.getStatus()) {
            case REFUSED:
                if (orderRequest.getMessage() != null) saveRefuseNote(orderRequest.getId(), orderRequest.getMessage());
                break;
            case CAR_DAMAGED:
                if (orderRequest.getDamage() != null) {
                    try {
                        saveDamage(new Damage(show(orderRequest.getId()).getCar(), show(orderRequest.getId()), orderRequest.getDamage()));
                    } catch (OrderNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
        }
        updateStatus(orderRequest.getId(), orderRequest.getStatus());
        emailService.processOrderRequest(orderRequest);
    }
}
