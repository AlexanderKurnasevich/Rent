package me.ride.service;

import me.ride.entity.car.Car;
import me.ride.repository.CarRepository;
import me.ride.exception.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public List<Car> index() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) throws CarNotFoundException {
        Car car = carRepository.getCarById(id);
        if (car == null) {
            throw new CarNotFoundException("Car not found");
        }
        return car;
    }

    public void save(Car car) {
        carRepository.save(car);
    }

    public List<Car> findAvailableCarsBetween(Date firstDay, Date lastDay) {
        return carRepository.findAvailableCarsBetween(firstDay, lastDay);
    }

}
