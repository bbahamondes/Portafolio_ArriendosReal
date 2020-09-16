package com.arriendosreal.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Urls {

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
		return "menu";
	}

}
	