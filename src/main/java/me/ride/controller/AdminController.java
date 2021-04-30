package me.ride.controller;

import me.ride.entity.User;
import me.ride.entity.car.Car;
import me.ride.entity.system.Order;
import me.ride.entity.system.OrderStatus;
import me.ride.entity.system.RefuseNote;
import me.ride.exception.CarNotFoundException;
import me.ride.exception.OrderNotFoundException;
import me.ride.service.OrderService;
import me.ride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @GetMapping("/admin/orders")
    public String ordersShow(Model model) {
        model.addAttribute("orders", orderService.getListOfAllOrders());
        return "admin/orders";
    }

    @GetMapping("/admin/order/{id}")
    public String orderShow(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("order", orderService.show(id));
        } catch (OrderNotFoundException throwable) {
            throwable.printStackTrace();
            return "redirect:/";
        }
        return "admin/order";
    }

    @PatchMapping("/admin/order")
    public String orderRequest(@RequestParam(defaultValue = "") Long orderId,
                               @RequestParam(defaultValue = "") String action,
                               @RequestParam(required = false) String message,
                               Model model) {
        if (action.equals("refuse")) {
            orderService.updateStatus(orderId, OrderStatus.REFUSED);
            if(message != null) orderService.saveRefuseNote(orderId, message);
            return "redirect:/admin/orders";
        }
        if (action.equals("accept")) {
            orderService.updateStatus(orderId, OrderStatus.ACCEPTED);
            return "redirect:/admin/orders";
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/users")
    public String usersShow(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin/users";
    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }

}
