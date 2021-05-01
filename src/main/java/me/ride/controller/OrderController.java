package me.ride.controller;

import me.ride.entity.system.Order;
import me.ride.entity.User;
import me.ride.entity.car.Car;
import me.ride.entity.system.OrderStatus;
import me.ride.entity.system.RefuseNote;
import me.ride.exception.CarNotFoundException;
import me.ride.exception.OrderNotFoundException;
import me.ride.service.CarService;
import me.ride.service.MaintenanceService;
import me.ride.service.OrderService;
import me.ride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @Autowired
    private MaintenanceService maintenanceService;

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
    public String create(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "orders/new";
        }
        if(order.getLastDay().before(order.getFirstDay())){
            bindingResult.addError(new FieldError("order","lastDay","Последний день должен быть позже первого"));
            return "orders/new";
        }
        boolean b1 = orderService.isOrderAllowed(order);
        boolean b2 = maintenanceService.isCarFreeFindByOrder(order);
        if (!orderService.isOrderAllowed(order) || !maintenanceService.isCarFreeFindByOrder(order)){
            bindingResult.addError(new FieldError("order","lastDay","Машина занята на эти дни"));
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

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = (User) userService.loadUserByUsername(username);
        Order order = null;
        try {
            order = orderService.show(id);
        } catch (OrderNotFoundException throwables) {
            throwables.printStackTrace();
            return "redirect:/client/profile";
        }
        if(!user.equals(order.getUser())){
            return "/403";
        }
        model.addAttribute("order", order);
        try {
            model.addAttribute("note", orderService.findRefuseNote(order));
        } catch (Throwable throwable){
            return "orders/show";
        }
        return "orders/show";
    }

    @GetMapping("/pay/{id}")
    public String payPage(@PathVariable("id") Long id, Model model) {
        Order order = null;
        try {
            order = orderService.show(id);
        } catch (OrderNotFoundException throwables) {
            throwables.printStackTrace();
            return "redirect:/client/profile";
        }
        model.addAttribute("order", order);
        return "orders/pay/pay";
    }

    @PostMapping("/pay")
    public String payProcess(@ModelAttribute("order") Order order) {
        orderService.updateStatus(order.getId(), OrderStatus.PAID);
        return "redirect:/client/profile";
    }
}
