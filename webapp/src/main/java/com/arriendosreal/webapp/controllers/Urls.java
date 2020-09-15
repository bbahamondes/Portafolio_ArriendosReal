package com.arriendosreal.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Urls {

	@GetMapping("/menu")
	public String greeting(@CookieValue(value = "username") String user, Model model) {
		model.addAttribute("user", user);
		return "menu";
	}

}
