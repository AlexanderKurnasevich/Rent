package me.ride.controller;

import me.ride.entity.system.Order;
import me.ride.entity.User;
import me.ride.entity.car.Car;
import me.ride.exception.CarNotFoundException;
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

    @GetMapping("/new")
    public String newOrder(@ModelAttribute("order") Order order, @RequestParam(value = "carId") Long carId, Model model){
        Car car = null;
        try {
            car = carService.show(carId);
        } catch (CarNotFoundException throwable) {
            throwable.printStackTrace();
            return "redirect:/cars";
        }
        model.addAttribute("car", car);
        order.setCar(car);
        return "orders/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "orders/new";
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserDetails user = userService.loadUserByUsername(username);
        order.setUser((User) user);
        orderService.save(order);
        return "redirect:/cars";
    }
}
