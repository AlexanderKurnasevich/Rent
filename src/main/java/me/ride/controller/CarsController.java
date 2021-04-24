package me.ride.controller;

import me.ride.entity.car.Car;
import me.ride.exception.CarNotFoundException;
import me.ride.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cars")
public class CarsController {

    @Autowired
    private CarService carService;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("cars", carService.index());
        return"cars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        Car car = null;
        try {
            car = carService.show(id);
        } catch (CarNotFoundException throwables) {
            throwables.printStackTrace();
            return "redirect:/cars";
        }
        model.addAttribute("car", car);
        return "cars/show";
    }

    @GetMapping("/new")
    public String newCar(@ModelAttribute("car") Car car){
        return "cars/new";
    }

    @PostMapping
    public String create(@ModelAttribute("car") Car car){
        carService.save(car);
        return "redirect:/cars";
    }
}