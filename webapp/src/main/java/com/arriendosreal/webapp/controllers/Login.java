package com.arriendosreal.webapp.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Login {
	
	@PostMapping(value = "/login", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> login(@RequestParam(name="email", required=true) String email, @RequestParam(name="password", required=true) String password, Model model) {
		Map<String, String[]> usu = new HashMap<String, String[]>();
		usu.put("admin@gmail.com", new String[] {"123", "admin"});
		usu.put("fun@gmail.com", new String[] {"123", "funcionario"});
		usu.put("cliente@gmail.com", new String[] {"123", "cliente"});
		
		if(usu.containsKey(email) && usu.get(email)[0].contentEquals(password)) {
			String json = String.format("{\"email\": \"%s\", \"profile\": \"%s\"}", email, usu.get(email)[1]);
			return new ResponseEntity<>(json, HttpStatus.OK	);
		} else {
			return new ResponseEntity<>("Wrong creds", HttpStatus.UNAUTHORIZED);
		}
	}

}
