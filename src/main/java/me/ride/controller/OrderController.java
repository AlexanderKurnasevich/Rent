package me.ride.controller;

import lombok.extern.slf4j.Slf4j;
import me.ride.entity.User;
import me.ride.entity.car.Car;
import me.ride.entity.system.Order;
import me.ride.entity.system.OrderStatus;
import me.ride.exception.CarNotFoundException;
import me.ride.exception.OrderNotFoundException;
import me.ride.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private PriceService priceService;

    @GetMapping("/new")
    public String newOrder(@ModelAttribute("order") Order order, @RequestParam(value = "carId") Long carId, Model model) throws CarNotFoundException {
        Car car = carService.getCarById(carId);
        model.addAttribute("car", car);
        order.setCar(car);
        return "orders/new";
    }

    @ExceptionHandler(CarNotFoundException.class)
    public String handleException(CarNotFoundException ex){
        log.error("Машина не найдена",ex);
        return "redirect:/cars";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "orders/new";
        }
        if(order.getLastDay().before(order.getFirstDay())){
            bindingResult.addError(new FieldError("order","lastDay","Последний день должен быть позже первого"));
            return "orders/new";
        }
        if (!orderService.isCarFreeByOrders(order.getCar().getId(), order.getFirstDay(), order.getLastDay())
                || !maintenanceService.isCarFreeByMaintenance(order.getCar().getId(), order.getFirstDay(), order.getLastDay())){
            bindingResult.addError(new FieldError("order","lastDay","Машина занята на эти дни"));
            return "orders/new";
        }
        order.setUser(userService.getAuthorizedUser());
        order.setPrice(priceService.getPrice(order.getCar(), order.getFirstDay(), order.getLastDay()));
        orderService.save(order);
        return "redirect:/cars";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleException(UsernameNotFoundException ex){
        log.error("Пользователь не найден",ex);
        return "redirect:/orders";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) throws OrderNotFoundException { //вынести в service
        Order order = orderService.show(id);
        model.addAttribute("order", order);
        model.addAttribute("note", orderService.findRefuseNote(order));
        return "orders/show";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleException(OrderNotFoundException ex){
        log.error("Заказ не найден",ex);
        return "redirect:/client/profile";
    }

    @GetMapping("/pay/{id}")
    public String payPage(@PathVariable("id") Long id, Model model) throws OrderNotFoundException {
        Order order = orderService.show(id);
        model.addAttribute("order", order);
        return "orders/pay/pay";
    }

    @PostMapping("/pay")
    public String payProcess(@ModelAttribute("order") OrderRequest orderRequest) {
        orderRequest.setStatus(OrderStatus.PAID);
        orderService.processOrder(orderRequest);
        return "redirect:/client/profile";
    }
}
