package me.ride.controller;

import me.ride.entity.car.Car;
import me.ride.repository.CarsDAO;
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
    private CarsDAO carsDAO;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("cars", carsDAO.index());
        return"cars/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("car", carsDAO.show(id));
        return "cars/show";
    }

    @GetMapping("/new")
    public String newCar(@ModelAttribute("car") Car car){
        return "cars/new";
    }

    @PostMapping
    public String create(@ModelAttribute("car") Car car){
        carsDAO.save(car);
        return "redirect:/cars";
    }
}