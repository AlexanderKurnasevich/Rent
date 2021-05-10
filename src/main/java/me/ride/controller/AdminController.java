package me.ride.controller;

import me.ride.entity.car.Car;
import me.ride.entity.system.Maintenance;
import me.ride.entity.system.OrderStatus;
import me.ride.entity.system.RentPrice;
import me.ride.exception.CarNotFoundException;
import me.ride.exception.OrderNotFoundException;
import me.ride.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private UtilService utilService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CarService carService;

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private EmailService emailService;

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
        model.addAttribute("orders", orderService.getListOfOrdersDateBetween(utilService.parseStringToDate(date1), utilService.parseStringToDate(date2)));
        return "admin/orders/index";
    }

    @GetMapping("/admin/orders/{id}")
    public String orderShow(@PathVariable("id") Long id, Model model) throws OrderNotFoundException {
        model.addAttribute("order", orderService.show(id));
        return "admin/orders/order";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleException(OrderNotFoundException e) {
        return "redirect:/";
    }

    @PostMapping( "/admin/orders")
    public String orderRequest(@RequestParam(defaultValue = "") Long orderId,
                               @RequestParam(defaultValue = "") String action,
                               @RequestParam(required = false, defaultValue = "") Double damage,
                               @RequestParam(required = false) String message) {
        /*Map<OrderStatus, OrderStatusHandler> handlers = new EnumMap<>(OrderStatus.class);
        OrderStatusHandler statusHandler = handlers.get(action);
        statusHandler.handle(null);
        orderService.updateStatus(orderId, action);
        //SOLID -> OPEN-CLOSE PRINCIPLE*/

        //TODO: user POST and request body

        //orderService.processOrder(OrderPayload payload);
        orderService.processOrder(new OrderRequest(orderId, OrderStatus.valueOf(action), damage, message));
        //orderService.processOrder(orderRequest);
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
    public String carShow(@PathVariable("id") Long id, Model model) throws CarNotFoundException {
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        model.addAttribute("price", priceService.getRentPriceByCar(car));
        return "admin/cars/car";
    }

    @ExceptionHandler(CarNotFoundException.class)
    public String handleException(CarNotFoundException e) {
        return "redirect:/admin/cars";
    }

    @PostMapping("/admin/prices/edit")
    public String setPrice(@ModelAttribute("price") @Valid RentPrice rentPrice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/cars/car";
        }
        priceService.save(rentPrice);
        return "redirect:/admin/cars/" + rentPrice.getCar().getId();
    }

    @GetMapping("/admin/cars/maintenances")
    public String maintenancesShow(Model model) {
        model.addAttribute("maintenances", maintenanceService.getMaintenanceList());
        return "admin/cars/maintenances";
    }

    @GetMapping("/admin/cars/maintenances/{id}")
    public String maintenanceShow(@PathVariable("id") Long id, Model model) {
        model.addAttribute("maintenance", maintenanceService.findMaintenanceById(id));
        return "admin/cars/maintenance";
    }

    @PostMapping("/admin/cars/maintenances")
    public String maintenanceEdit(@ModelAttribute("maintenance") Maintenance maintenance) {
        maintenanceService.save(maintenance);
        return "redirect:/admin/maintenances";
    }

    @GetMapping("/admin/cars/maintenance/{id}")
    public String maintenanceForm(@PathVariable("id") Long id, Model model) throws CarNotFoundException {
        Car car = carService.getCarById(id);
        Maintenance maintenance = new Maintenance();
        maintenance.setCar(car);
        model.addAttribute("maintenance", maintenance);
        return "admin/cars/maintenance_form";
    }

    @PostMapping("/admin/cars/maintenance")
    public String maintenanceNew(@ModelAttribute("maintenance") @Valid Maintenance maintenance, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/cars/maintenance_form";
        }
        if (maintenance.getLastDay().before(maintenance.getFirstDay())) {
            bindingResult.addError(new FieldError("maintenance", "lastDay", "Последний день должен быть позже первого"));
            return "admin/cars/maintenance_form";
        }
        if (!orderService.isCarFreeByOrders(maintenance.getCar().getId(), maintenance.getFirstDay(), maintenance.getLastDay())
                || !maintenanceService.isCarFreeByMaintenance(maintenance.getCar().getId(), maintenance.getFirstDay(), maintenance.getLastDay())) {
            bindingResult.addError(new FieldError("maintenance", "lastDay", "Машина занята на эти дни"));
            return "admin/cars/maintenance_form";
        }
        maintenanceService.save(maintenance);
        return "redirect:/admin/cars";
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
