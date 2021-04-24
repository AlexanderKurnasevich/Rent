package me.ride.service;

import me.ride.entity.car.Car;
import me.ride.repository.CarRepository;
import me.ride.exceptions.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public List<Car> index() {
        return carRepository.findAll();
    }

    public Car show(Long id) throws CarNotFoundException {
        Car car = carRepository.getCarById(id);

        if (car == null) {
            throw new CarNotFoundException("Car not found");
        }

        return car;
    }

    public void save(Car car) {
        carRepository.save(car);
    }
}
