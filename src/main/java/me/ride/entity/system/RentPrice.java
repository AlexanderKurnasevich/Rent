package me.ride.entity.system;

import lombok.Getter;
import lombok.Setter;
import me.ride.entity.User;
import me.ride.entity.car.Car;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_price")
public class RentPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Car car;

    @NonNull
    private Double pricePerDay;

    public RentPrice() {
    }

    public RentPrice(Long id, Car car, Double pricePerDay) {
        this.id = id;
        this.car = car;
        this.pricePerDay = pricePerDay;
    }
}
