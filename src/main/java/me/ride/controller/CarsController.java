package me.ride.controller;

import me.ride.entity.car.Car;
import me.ride.entity.system.Order;
import me.ride.exception.CarNotFoundException;
import me.ride.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String show(@PathVariable("id") Long id, Model model) throws CarNotFoundException {
        model.addAttribute("car", carService.getCarById(id));
        return "cars/show";
    }

    @ExceptionHandler(CarNotFoundException.class)
    public String handleException(CarNotFoundException ex){
        return "redirect:/cars";
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

    @GetMapping("/search/date")
    public String dateSearch(@ModelAttribute("order")Order order){
        return "cars/search/date_form";
    }

    @PostMapping("/search/date")
    public String dateSearchResult(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "cars/search/date_form";
        }
        model.addAttribute("cars", carService.findAvailableCarsBetween(order.getFirstDay(), order.getLastDay()));
        return "cars/index";
    }
}