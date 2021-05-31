package me.ride.service;

import me.ride.entity.system.OrderRequest;
import me.ride.entity.User;
import me.ride.entity.system.Damage;
import me.ride.entity.system.Order;
import me.ride.entity.system.OrderStatus;
import me.ride.entity.system.RefuseNote;
import me.ride.exception.OrderNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface OrderService {

    void save(Order order);

    void saveRefuseNote(Long orderId, String message);

    RefuseNote findRefuseNote(Order order);

    void updateStatus(Long orderId, OrderStatus status);

    Order show(Long id) throws OrderNotFoundException;

    List<Order> getListOfOrders(User user);

    List<Order> getListOfAllOrders();

    List<Order> getListOfOrdersDateBetween(Date date1, Date date2);

    boolean isCarFreeByOrders(Long carId, Date dateFrom, Date dateTo);

    void saveDamage(Damage damage);

    @Transactional
    void processOrder(OrderRequest orderRequest);

    Damage findDamageByOrder(Order order);
}
