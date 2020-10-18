package com.arriendosreal.webapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
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
import com.arriendosreal.webapp.entities.Profiles;
import com.arriendosreal.webapp.entities.Users;
import com.arriendosreal.webapp.repositories.ProfilesRepository;

@RequestMapping(value = "/api/v1/profile", produces = "application/json; charset=utf-8")
@CrossOrigin(origins = "*")
@RestController
public class ProfilesController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;

    @Autowired
    private ProfilesRepository profileRepo;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

    }

    List<Profiles> findProfileById(int profile_id) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_PROFILES")
                .returningResultSet("out_users", BeanPropertyRowMapper.newInstance(Profiles.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_profile_id", profile_id);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_users");
        }

    }
    
    List<Profiles> findAllProfiles() {

        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ALL_PROFILES")
                .returningResultSet("out_profiles", BeanPropertyRowMapper.newInstance(Profiles.class));
        SqlParameterSource paramaters = new MapSqlParameterSource();

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_profiles");
        }

    }
    
    @GetMapping(value = "/all")
    public ResponseEntity<String> getAllProfiles() {
        List<Profiles> profiles = null;
        try {
            profiles = findAllProfiles();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (profiles != null) {
            return new ResponseEntity<>(gson.toJson(profiles), HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Profile Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    

    @PostMapping
    public ResponseEntity<String> createProfile(
            @RequestParam(name = "profileName", required = true) String profileName) {

        int resultado = 0;
        try {
            resultado = profileRepo.createProfile(profileName);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado > 0) {
            String json = "{\"profileId\": %s, " + "\"profileName\": \"%s\"}";
            json = String.format(json, resultado, profileName);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getProfileByID(@RequestParam(name = "profileId", required = true) int profileId) {

        Profiles profile = new Profiles();
        List<Profiles> profiles = new ArrayList<Profiles>();
        try {
            profiles = findProfileById(profileId);
            profile = profiles.get(0);
        } catch (Exception e) {
            profile = null;
            System.out.println(e.toString());
        }

        if (profile != null) {
            String json = "{\"id\": %s," + "\"name\": \"%s\"" + "}";
            json = String.format(json, profile.getProfileId(), profile.getProfileName());
            return new ResponseEntity<>(json, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Profile Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteProfileByID(@RequestParam(name = "profileId", required = true) int profileId) {

        int resultado = -1;
        try {
            resultado = profileRepo.deleteProfile(profileId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado != -1) {
            String json = String.format("{ \"profileId\": %s, \"result\": %s  }", profileId, resultado);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<String> updateProfile(@RequestParam(name = "profileId", required = true) int profileId,
            @RequestParam(name = "profileName", required = true) String profileName) {

        int resultado = -1;
        try {
            resultado = profileRepo.updateProfile(profileId, profileName);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado >= 0) {
            String json = "{\"profileId\": %s," + "\"profileName\": \"%s\"}";
            json = String.format(json, profileId, profileName);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
