package com.arriendosreal.webapp.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import com.arriendosreal.webapp.entities.Users;
import com.arriendosreal.webapp.entities.Profiles;
import com.arriendosreal.webapp.repositories.ProfilesRepository;
import com.arriendosreal.webapp.repositories.UsersRepository;

@RequestMapping(value = "/api/v1/user", produces = "application/json; charset=utf-8")
@CrossOrigin(origins = "*")
@RestController
public class UsersController {

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @Autowired
    private ProfilesRepository profileRepo;

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

    }

    List<Users> findUserById(int user_id) {

        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_USERS")
                .returningResultSet("out_users", BeanPropertyRowMapper.newInstance(Users.class));
        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_user_id", user_id);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_users");
        }

    }
    
    List<Users> findAllUsers() {

        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ALL_USERS")
                .returningResultSet("out_users", BeanPropertyRowMapper.newInstance(Users.class));
        SqlParameterSource paramaters = new MapSqlParameterSource();

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_users");
        }

    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestParam(name = "profileId", required = true) int profileId,
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "password", required = true) String password) {

        int resultado = 0;
        try {
            resultado = userRepo.createUser(username, email, password, profileId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado > 0) {
            Users user = new Users(resultado, profileId, username, email, password);
            String json2 = gson.toJson(user);
            return new ResponseEntity<>(json2, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping(value = "/all")
    public ResponseEntity<String> getAllUsers() {
        List<Users> users = null;
        try {
            users = findAllUsers();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (users != null) {
            users.forEach(user -> {
                var profile = profileRepo.findByProfileId(userRepo.findById(user.getUserId()).orElse(null).getProfiles())
                        .orElse(null);
                user.setProfiles(profile.getProfileId());
            });
            return new ResponseEntity<>(gson.toJson(users), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    

    @GetMapping
    public ResponseEntity<String> getUserByID(@RequestParam(name = "userId", required = true) int user_id) {

        Users user = null;
        try {
            user = findUserById(user_id).get(0);
        } catch (Exception e) {
            user = null;
            System.out.println(e.toString());
        }

        if (user != null) {
            Profiles profile = profileRepo.findByProfileId(user.getProfiles()).orElse(null);
            if (profile == null) {
                // Hacky workaround, for some reason the SP returns 0 as the profileID
                profile = profileRepo.findByProfileId(userRepo.findById(user.getUserId()).orElse(null).getProfiles())
                        .orElse(null);
            }

            if (profile != null) {
                String json = "{\"userId\": %s, " + "\"username\": \"%s\", " + "\"email\": \"%s\", " + "\"profile\": {"
                        + "\"id\": %s," + "\"name\": \"%s\"" + "}}";
                json = String.format(json, user.getUserId(), user.getUsername(), user.getEmail(),
                        profile.getProfileId(), profile.getProfileName());
                return new ResponseEntity<>(json, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Profile Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("User Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserByID(@RequestParam(name = "userId", required = true) int user_id) {

        int resultado = -1;
        try {
            resultado = userRepo.deleteUser(user_id);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado != -1) {
            HashMap<String, Integer> output = new HashMap<String, Integer>();
            output.put("userId", user_id);
            output.put("resultado", resultado);
            return new ResponseEntity<>(gson.toJson(output), HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestParam(name = "userId", required = true) int userId,
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "password", required = true) String password,
            @RequestParam(name = "profileId", required = true) int profileId) {

        int resultado = -1;
        try {
            resultado = userRepo.updateUser(userId, username, email, password, profileId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado >= 0) {
            String json = "{\"userId\": \"%s\", " + "\"username\": \"%s\", " + " \"email\": \"%s\", "
                    + "\"profile\": \"%s\"}";
            json = String.format(json, userId, username, email, profileId);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
