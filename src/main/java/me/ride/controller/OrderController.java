package me.ride.controller;

import me.ride.entity.Order;
import me.ride.entity.User;
import me.ride.entity.client.Client;
import me.ride.repository.OrderRepository;
import me.ride.service.CarService;
import me.ride.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/new/{carId}")
    public String newOrder(@ModelAttribute("order") Order order, @ModelAttribute("client") User user, @PathVariable("carId") Long carId, Model model){
        model.addAttribute("car", carService.show(carId));
        return "orders/new";
    }

    @PostMapping
    public String create(@ModelAttribute("client") User user, @ModelAttribute("order") @Valid Order order, BindingResult bindingResult){
        String backPath = "redirect:orders/new/";
        backPath = backPath + order.getCar().getId();
        if(bindingResult.hasErrors()) return backPath;
        order.setUser(user);
        orderService.save(order);
        return "redirect:/cars";
    }
}
