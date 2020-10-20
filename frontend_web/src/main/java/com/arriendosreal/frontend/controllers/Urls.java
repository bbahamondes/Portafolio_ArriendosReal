package com.arriendosreal.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Urls {
    
    @GetMapping("/")
    public String menu(@CookieValue(value = "username", required=false) String user, Model model) {
        model.addAttribute("user", user);
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

    @GetMapping("/reservas")
    public String reserva(@CookieValue(value = "username", required=false) String user, Model model) {
        model.addAttribute("user", user);
        return "reservas";
    }

    @GetMapping("/departamentos")
    public String departamentos(@CookieValue(value = "username", required=false) String user, Model model) {
        model.addAttribute("user", user);
        return "departamentos";
    }

}
