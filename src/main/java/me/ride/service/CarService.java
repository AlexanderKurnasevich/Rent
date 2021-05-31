package me.ride.service;

import me.ride.entity.car.Car;
import me.ride.exception.CarNotFoundException;

import java.util.Date;
import java.util.List;

public interface CarService {

    List<Car> index();

    Car getCarById(Long id) throws CarNotFoundException;

    void save(Car car);

    List<Car> findAvailableCarsBetween(Date firstDay, Date lastDay);

}
