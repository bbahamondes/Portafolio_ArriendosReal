package com.arriendosreal.webapp.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.arriendosreal.webapp.entities.Users;
import com.arriendosreal.webapp.repositories.ProfilesRepository;
import com.arriendosreal.webapp.repositories.UsersRepository;

@RequestMapping("/api/v1")
@RestController
public class Login {

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private ProfilesRepository profileRepo;

    @PostMapping(value = "/login", produces = "application/json; charset=utf-8")
    public ResponseEntity<String> login(@RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "password", required = true) String password, Model model) {

        Users user = userRepo.findByEmail(email);
        if (user != null) {
            if (user.getPassword().contentEquals(password.toString())) {
                String profileName = profileRepo.findByProfileId(user.getProfiles()).orElse(null).getProfileName();
                String json = String.format("{\"email\": \"%s\", \"profile\": \"%s\"}", user.getEmail(), profileName);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Wrong creds", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
