package me.ride.entity.system;

import lombok.Getter;
import lombok.Setter;
import me.ride.entity.car.Car;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "t_car_maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Car car;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date firstDay;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private Date lastDay;

    public Maintenance() {
    }

    public Maintenance(Car car, Date firstDay, Date lastDay) {
        this.car = car;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
    }
}
