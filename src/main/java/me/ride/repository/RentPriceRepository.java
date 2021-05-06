package me.ride.repository;

import me.ride.entity.car.Car;
import me.ride.entity.system.RentPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentPriceRepository extends JpaRepository<RentPrice, Long> {

    RentPrice getRentPriceByCar(Car car);
}
