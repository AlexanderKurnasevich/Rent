package me.ride.controller;

import me.ride.entity.User;
import me.ride.entity.client.Client;
import me.ride.service.ClientService;
import me.ride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("clientForm", new Client());

        return "client/register";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute("clientForm") @Valid User userForm, @Valid Client clientForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "client/register";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "client/register";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "client/register";
        }
        clientForm.setUser(userForm);
        clientService.saveClient(clientForm);
        return "redirect:/";
    }

    @GetMapping()
    public String profileShow(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        UserDetails user = userService.loadUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("client", clientService.findClientByUser((User) user));

        return "client/profile";
    }

    @PostMapping("/test")
    public String addUser(@ModelAttribute("clientForm") @Valid Client clientForm, Model model) {

        clientService.saveClient(clientForm);
        return "redirect:/";
    }
}
