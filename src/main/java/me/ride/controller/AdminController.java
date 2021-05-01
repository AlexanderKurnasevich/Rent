package me.ride.controller;

import me.ride.entity.car.Car;
import me.ride.entity.system.Damage;
import me.ride.entity.system.Maintenance;
import me.ride.entity.system.Order;
import me.ride.entity.system.OrderStatus;
import me.ride.exception.CarNotFoundException;
import me.ride.exception.OrderNotFoundException;
import me.ride.service.CarService;
import me.ride.service.MaintenanceService;
import me.ride.service.OrderService;
import me.ride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CarService carService;

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin/index";
    }

    @GetMapping("/admin/orders")
    public String ordersShow(Model model) {
        model.addAttribute("orders", orderService.getListOfAllOrders());
        return "admin/orders/index";
    }

    @GetMapping("/admin/orders/filter")
    public String filterOrderForm(Model model) {
        return "admin/orders/filter_form";
    }

    @PostMapping("/admin/orders/filter")
    public String filterOrder(@RequestParam(defaultValue = "") String date1,
                              @RequestParam(defaultValue = "") String date2,
                              Model model) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate1 = null;
        Date parsedDate2 = null;
        try {
            parsedDate1 = format.parse(date1);
            parsedDate2 = format.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("orders", orderService.getListOfOrdersDateBetween(parsedDate1, parsedDate2));
        return "admin/orders/index";
    }

    @GetMapping("/admin/orders/{id}")
    public String orderShow(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("order", orderService.show(id));
        } catch (OrderNotFoundException throwable) {
            throwable.printStackTrace();
            return "redirect:/";
        }
        return "admin/orders/order";
    }

    @PatchMapping("/admin/orders")
    public String orderRequest(@RequestParam(defaultValue = "") Long orderId,
                               @RequestParam(defaultValue = "") String action,
                               @RequestParam(required = false, defaultValue = "") Double damage,
                               @RequestParam(required = false) String message) {
        switch (action) {
            case "refuse":
                orderService.updateStatus(orderId, OrderStatus.REFUSED);
                if (message != null) orderService.saveRefuseNote(orderId, message);
                break;
            case "accept":
                orderService.updateStatus(orderId, OrderStatus.ACCEPTED);
                break;
            case "damaged":
                orderService.updateStatus(orderId, OrderStatus.CAR_DAMAGED);
                if(damage != null) {
                    try {
                        orderService.saveDamage(new Damage(orderService.show(orderId).getCar(), orderService.show(orderId), damage));
                    } catch (OrderNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }
                break;
            case "return":
                orderService.updateStatus(orderId, OrderStatus.RETURNED);
                break;
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/users")
    public String usersShow(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin/users";
    }

    @GetMapping("/admin/cars")
    public String carsList(Model model) {
        model.addAttribute("cars", carService.index());
        return "admin/cars/index";
    }

    @GetMapping("/admin/cars/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Car car = getCar(id);
        if (car == null) return "redirect:/admin/cars";
        model.addAttribute("car", car);
        return "admin/cars/car";
    }

    @GetMapping("/admin/cars/maintenance/{id}")
    public String maintenanceForm(@PathVariable("id") Long id, Model model){
        Car car = getCar(id);
        Maintenance maintenance = new Maintenance();
        if (car == null) return "redirect:/admin/cars";
        maintenance.setCar(car);
        model.addAttribute("maintenance", maintenance);
        return "admin/cars/maintenance_form";
    }

    @PostMapping("/admin/cars/maintenance")
    public String maintenanceNew(@ModelAttribute("maintenance") @Valid Maintenance maintenance, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "admin/cars/maintenance_form";
        }
        if(maintenance.getLastDay().before(maintenance.getFirstDay())){
            bindingResult.addError(new FieldError("maintenance","lastDay","Последний день должен быть позже первого"));
            return "admin/cars/maintenance_form";
        }
        maintenanceService.
    }

    private Car getCar(Long id) {
        Car car = null;
        try {
            car = carService.show(id);
        } catch (CarNotFoundException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return car;
    }

//    @PostMapping("/admin/cars/{id}")
//    public String processCar(@PathVariable("id") Long id,
//                             @RequestParam(required = false, defaultValue = "") Integer damage,
//                             @RequestParam(defaultValue = "") String action,
//                             Model model) {
//
//        return "redirect:/admin/cars";
//    }



    /*@PostMapping("/admin")
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
    }*/

}
