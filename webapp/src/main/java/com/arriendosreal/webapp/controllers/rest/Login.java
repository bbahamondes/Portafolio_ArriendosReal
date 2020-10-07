package com.arriendosreal.webapp.controllers.rest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
	
	
	/*Not working in the current iteration, should take a look at the stored procedures*/
	@PostMapping(value = "/user", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> createUser(@RequestParam(name="profileId", required=true) int profileId,
			@RequestParam(name="username", required=true) String username, 
			@RequestParam(name="email", required=true) String email, 
			@RequestParam(name="password", required=true) String password) {
		
		Profiles profile = profileRepo.findByProfileId(BigDecimal.valueOf(profileId)).orElse(null);
		Users user = new Users(BigDecimal.valueOf(101), profile, username, email, password );		
		
		if(user != null ) {
			userRepo.createUser(user.getUserId(), user.getProfiles().getProfileId(), user.getUsername(), user.getEmail(), user.getPassword());			
			return new ResponseEntity<>("OK", HttpStatus.OK	);			
		} else {
			return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
