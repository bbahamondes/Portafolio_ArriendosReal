package com.arriendosreal.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Urls {
    
    @GetMapping("/")
    public String menu(Model model) {
        return "menu";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        return "registro";
    }

    @GetMapping("/menu")
    public String menu(@CookieValue(value = "username") String user, Model model) {
        model.addAttribute("user", user);
        return "menu";
    }

    @GetMapping("/reserva")
    public String reserva(@CookieValue(value = "username") String user, Model model) {
        model.addAttribute("user", user);
        return "menu";
    }

    @GetMapping("/promociones")
    public String promociones(@CookieValue(value = "username") String user, Model model) {
        model.addAttribute("user", user);
        return "menu";
    }

    @GetMapping("/departamentos")
    public String departamentos(@CookieValue(value = "username") String user, Model model) {
        model.addAttribute("user", user);
        return "departamentos";
    }

}
