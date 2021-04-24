package me.ride.service;

import me.ride.entity.car.Car;
import me.ride.repository.CarRepository;
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

    public Car show(Long id) {
        return carRepository.getOne(id);
    }

    public void save(Car car) {
        carRepository.save(car);
    }
}
