package com.arriendosreal.webapp.controllers.rest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.arriendosreal.webapp.entities.Users;
import com.arriendosreal.webapp.repositories.UsersAccess;

@RestController
public class Login {
	
	@Autowired
	private UsersAccess userRepo;
	
	@PostMapping(value = "/login", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> login(@RequestParam(name="email", required=true) String email, @RequestParam(name="password", required=true) String password, Model model) {
		
		Users user = userRepo.findByEmail(email);
		if(user.getPassword().contentEquals(password.toString())) {
			String json = String.format("{\"email\": \"%s\", \"profile\": \"%s\"}", user.getEmail(), user.getProfiles());
			return new ResponseEntity<>(json, HttpStatus.OK	);
		} else {
			return new ResponseEntity<>("Wrong creds", HttpStatus.UNAUTHORIZED);
		}

	}

}
