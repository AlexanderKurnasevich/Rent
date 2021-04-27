package me.ride.repository;

import me.ride.entity.system.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Convert;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o where o.car.id=?1 AND ( o.firstDay between ?2 and ?3 or o.lastDay between ?2 and ?3)")
    List<Order> findOrderByCarBetween(Long carId, Date dateFrom, Date dateTo);
}
