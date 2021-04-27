package me.ride.controller;

import me.ride.entity.User;
import me.ride.entity.car.Car;
import me.ride.entity.system.Order;
import me.ride.service.OrderService;
import me.ride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }

    @GetMapping("/admin/order_test_res")
    public String ordTest(Model model) {
        Order order = new Order(100L, new User(), new Car(), new Date(), new Date());
        boolean b = orderService.isOrderAllowed(order);
        return "admin/order_test_res";
    }

    @PostMapping("/admin/order_test")
    public String ordShow(@ModelAttribute("order") Order order, Model model) {

        return "admin/order_test_res";
    }
}
