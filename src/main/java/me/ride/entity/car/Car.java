package me.ride.entity.car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {

    private int id;
    private CarType carType;
    private String name;
    private int bags;
    private int passengers;
    private int doors;

    public Car() {}

    public Car(int id, CarType carType, String name, int bags, int passengers, int doors) {
        this.id = id;
        this.carType = carType;
        this.name = name;
        this.bags = bags;
        this.passengers = passengers;
        this.doors = doors;
    }
}
