package me.ride.entity.system;

import lombok.Getter;
import lombok.Setter;
import me.ride.entity.car.Car;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_damage")
public class Damage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Car car;

    @OneToOne
    private Order order;

    private Double damageCost;

    public Damage() {
    }

    public Damage(Car car, Order order, Double damageCost) {
        this.car = car;
        this.order = order;
        this.damageCost = damageCost;
    }
}
