package me.ride.controller;

import me.ride.entity.User;
import me.ride.entity.client.Client;
import me.ride.service.ClientService;
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
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("clientForm", new Client());
        return "client/register";
    }

    @PostMapping("/register")
    public String addClient(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult,
                          @ModelAttribute("clientForm") @Valid Client clientForm, BindingResult bindingResult2, Model model) {

        if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
            return "client/register";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            bindingResult.addError(new FieldError("userForm","passwordConfirm","Пароли не совпадают"));
            return "client/register";
        }
        if (!userService.saveUser(userForm)) {
            bindingResult.addError(new FieldError("userForm","username","Пользователь с таким именем уже существует"));
            return "client/register";
        }
        clientForm.setUser(userForm);
        clientService.saveClient(clientForm);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profileShow(Model model) {
        model.addAttribute("user", userService.getAuthorizedUser());
        model.addAttribute("client", clientService.findClientByUser(userService.getAuthorizedUser()));
        model.addAttribute("orders", orderService.getListOfOrders(userService.getAuthorizedUser()));
        return "client/profile/index";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        model.addAttribute("userForm", userService.getAuthorizedUser());
        model.addAttribute("clientForm", clientService.findClientByUser(userService.getAuthorizedUser()));
        return "client/profile/edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult,
                                @ModelAttribute("clientForm") @Valid Client clientForm, BindingResult bindingResult2, Model model){
        if (bindingResult.hasErrors() || bindingResult2.hasErrors()) {
            return "client/profile/edit";
        }
        clientService.updateProfile(userForm, clientForm);
        return "redirect:/client/profile";
    }
}
