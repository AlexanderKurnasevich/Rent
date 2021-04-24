package me.ride.controller;

import me.ride.entity.system.Order;
import me.ride.entity.User;
import me.ride.entity.car.Car;
import me.ride.exceptions.CarNotFoundException;
import me.ride.service.CarService;
import me.ride.service.OrderService;
import me.ride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @GetMapping("/new/{carId}")
    public String newOrder(@ModelAttribute("order") Order order, @PathVariable("carId") Long carId, Model model){
        Car car = null;
        try {
            car = carService.show(carId);
        } catch (CarNotFoundException throwables) {
            throwables.printStackTrace();
            return "redirect:/cars";
        }
        model.addAttribute("car", car);
        return "orders/new";
    }

    @PostMapping("/new/{carId}")
    public String create(@ModelAttribute("order") @Valid Order order, @PathVariable("carId") Long carId, BindingResult bindingResult, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserDetails user = userService.loadUserByUsername(username);
        order.setUser((User) user);
        Car car = null;
        try {
            car = carService.show(carId);
        } catch (CarNotFoundException throwables) {
            throwables.printStackTrace();
            return "redirect:/cars";
        }
        order.setCar(car);
        orderService.save(order);
        return "redirect:/cars";
    }
}
