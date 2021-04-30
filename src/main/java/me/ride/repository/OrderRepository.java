package me.ride.repository;

import me.ride.entity.User;
import me.ride.entity.system.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o where o.car.id = ?1 AND " +
            "( ?2 between o.firstDay and o.lastDay or ?3 between o.firstDay and o.lastDay) AND " +
            "o.orderStatus <> me.ride.entity.system.OrderStatus.REFUSED")
    List<Order> findOrderByCarBetween(Long carId, Date dateFrom, Date dateTo);

    List<Order> findAllByUser(User user);
}
