package me.ride.repository;

import me.ride.entity.car.Car;
import me.ride.entity.car.CarType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarsDAO {
    private static int CARS_COUNT;
    private List<Car> cars;

    {
        cars = new ArrayList<>();
        cars.add(new Car(++CARS_COUNT, CarType.FULL_SIZE, "Audi A8", 2, 5, 4));
        cars.add(new Car(++CARS_COUNT,CarType.INTERMEDIATE, "Toyota Camry", 2, 5, 4));
        cars.add(new Car(++CARS_COUNT,CarType.MINIVAN, "VW Sharan", 3, 8, 4));
    }


    public List<Car> index() {
        return cars;
    }

    public Car show(int id) {
        return cars.stream().filter(car -> car.getId() == id).findAny().orElse(null);
    }

    public void save(Car car) {
        cars.add(car);
    }
}