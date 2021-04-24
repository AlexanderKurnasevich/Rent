package me.ride.repository;

import me.ride.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.annotation.Native;
import java.util.Date;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    Car getCarById(Long id);

//    @Query("")
//    List<Car> findAvailableCarsBetween(Date dateFrom, Date dateTo);
}
