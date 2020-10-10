package com.arriendosreal.webapp.controllers.rest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.arriendosreal.webapp.entities.Profiles;
import com.arriendosreal.webapp.entities.Users;
import com.arriendosreal.webapp.repositories.UsersRepository;
import com.arriendosreal.webapp.repositories.ProfilesRepository;
import java.math.BigDecimal;

@RestController
public class Login {
	
	@Autowired
	private UsersRepository userRepo;
	
	@Autowired
	private ProfilesRepository profileRepo;
	
	@PostMapping(value = "/login", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> login(@RequestParam(name="email", required=true) String email, @RequestParam(name="password", required=true) String password, Model model) {
		
		Users user = userRepo.findByEmail(email);
		if(user != null ) {
			if(user.getPassword().contentEquals(password.toString())) {
				String json = String.format("{\"email\": \"%s\", \"profile\": \"%s\"}", user.getEmail(), user.getProfiles().getProfileName());
				return new ResponseEntity<>(json, HttpStatus.OK	);
			} else {
				return new ResponseEntity<>("Wrong creds", HttpStatus.UNAUTHORIZED);
			}			
		} else {
			return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping(value = "/user", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> createUser(@RequestParam(name="profileId", required=true) int profileId,
			@RequestParam(name="username", required=true) String username, 
			@RequestParam(name="email", required=true) String email, 
			@RequestParam(name="password", required=true) String password) {
		
		int resultado = 0;
		try {
			resultado = userRepo.createUser(username, email, password, profileId);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		if(resultado > 0) {
			return new ResponseEntity<>("OK", HttpStatus.OK	);			
		} else {
			return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/*Not working, need to figure out why it's producing a null*/
	@GetMapping(value = "/user", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> getUserByID(@RequestParam(name="userId", required=true) int user_id) {
	
		Users user = new Users();
		List<Users> users = new ArrayList<>();
		//HashMap<String, List<Users>> test = new HashMap<String, List<Users>>();
		int resultado = 0;
		try {			
			users= userRepo.getUserById(user_id);
			System.out.println(users);
			//test.get("out_users").forEach(System.out::println);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		if(user != null) {
			return new ResponseEntity<>("OK", HttpStatus.OK	);			
		} else {
			return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
