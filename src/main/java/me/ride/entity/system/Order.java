package me.ride.entity.system;

import lombok.Getter;
import lombok.Setter;
import me.ride.entity.User;
import me.ride.entity.car.Car;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Car car;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Начиная с завтра")
    @NotNull
    private Date firstDay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    @NotNull
    private Date lastDay;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Double price;

    public Order() {
        orderStatus = OrderStatus.UNDER_CONSIDERATION;
    }

    public Order(Long id, User user, Car car, Date firstDay, Date lastDay, Double price) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.price = price;
        orderStatus = OrderStatus.UNDER_CONSIDERATION;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
