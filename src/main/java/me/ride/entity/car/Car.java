package me.ride.entity.car;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CarType carType;
    private String name;
    private int bags;
    private int passengers;
    private int doors;

    public Car() {
    }

    public Car(Long id, CarType carType, String name, int bags, int passengers, int doors) {
        this.id = id;
        this.carType = carType;
        this.name = name;
        this.bags = bags;
        this.passengers = passengers;
        this.doors = doors;
    }
}
