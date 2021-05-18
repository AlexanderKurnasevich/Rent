package me.ride.service;

import me.ride.entity.car.Car;
import me.ride.entity.system.RentPrice;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface PriceService {

    void save(RentPrice rentPrice);

    Double getPrice(Car car, Date firstDay, Date lastDay);

    RentPrice getRentPriceByCar(Car car);
}
