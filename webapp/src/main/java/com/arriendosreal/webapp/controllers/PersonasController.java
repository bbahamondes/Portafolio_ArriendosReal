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

import com.arriendosreal.webapp.entities.Personas;
import com.arriendosreal.webapp.entities.Users;
import com.arriendosreal.webapp.repositories.PersonasRepository;
import com.arriendosreal.webapp.repositories.UsersRepository;
import com.google.gson.Gson;

@RequestMapping(value = "/api/v1/persona", produces = "application/json; charset=utf-8")
@CrossOrigin(origins = "*")
@RestController
public class PersonasController {

    @Autowired
    private PersonasRepository personaRepo;

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

    }

    List<Personas> findPersonaById(int in_persona_id) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_PERSONA")
                .returningResultSet("out_persona", BeanPropertyRowMapper.newInstance(Personas.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_persona_id", in_persona_id);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_persona");
        }

    }
    
    List<Personas> findAllPersonas() {

        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ALL_PERSONA")
                .returningResultSet("out_persona", BeanPropertyRowMapper.newInstance(Personas.class));
        
        SqlParameterSource paramaters = new MapSqlParameterSource();

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_persona");
        }

    }

    @PostMapping
    public ResponseEntity<String> createPersona(@RequestParam(name = "rut", required = true) String rut,
            @RequestParam(name = "nombre", required = true) String nombre,
            @RequestParam(name = "apellidos", required = true) String apellidos,
            @RequestParam(name = "telefono", required = true) String telefono,
            @RequestParam(name = "userId", required = true) int userId) {

        int resultado = 0;
        try {
            resultado = personaRepo.createPersona(rut, nombre, apellidos, telefono, userId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado > 0) {
            Personas persona = new Personas(resultado, userId, rut, nombre, apellidos, telefono, null);            
            String json = gson.toJson(persona);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping(value = "/all")
    public ResponseEntity<String> getPersonaByID() {
        List<Personas> persons = null;
        try {
            persons = findAllPersonas();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (persons != null) {            
            return new ResponseEntity<>(gson.toJson(persons), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getPersonaByID(@RequestParam(name = "personaId", required = true) int personaId) {

        Personas person = new Personas();
        List<Personas> persons = new ArrayList<Personas>();
        try {
            persons = findPersonaById(personaId);
            person = persons.get(0);
        } catch (Exception e) {
            person = null;
            System.out.println(e.toString());
        }

        if (person != null) {

            Users user = userRepo.findByUserId(person.getUsers()).orElse(null);
            if (user == null) {
                // Hack workaround, for some reason the SP returns 0 as the UserID
                user = userRepo.findByUserId(personaRepo.findById(person.getIdPersona()).orElse(null).getUsers())
                        .orElse(null);
            }

            String json = "{\"personaId\": %s, " + "\"rut\": \"%s\", " + " \"nombre\": \"%s\", "
                    + " \"apellidos\": \"%s\", " + " \"telefono\": \"%s\", " + " \"userId\": %s }";

            json = String.format(json, personaId, person.getRut(), person.getNombre(), person.getApellidos(),
                    person.getTelefono(), user.getUserId());
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deletePersonaByID(@RequestParam(name = "personaId", required = true) int personaId) {

        int resultado = -1;
        try {
            resultado = personaRepo.deletePersona(personaId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado != -1) {
            String json = String.format("{ \"personaId\": %s, \"result\": %s  }", personaId, resultado);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<String> updatePersona(@RequestParam(name = "personaId", required = true) int personaId,
            @RequestParam(name = "rut", required = true) String rut,
            @RequestParam(name = "nombre", required = true) String nombre,
            @RequestParam(name = "apellidos", required = true) String apellidos,
            @RequestParam(name = "telefono", required = true) String telefono) {

        int resultado = -1;
        try {
            resultado = personaRepo.updatePersona(personaId, rut, nombre, apellidos, telefono);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado >= 0) {
            String json = "{\"personaId\": %s, " + "\"rut\": \"%s\", " + " \"nombre\": \"%s\", "
                    + " \"apellidos\": \"%s\", " + " \"telefono\": \"%s\"}";

            json = String.format(json, personaId, rut, nombre, apellidos, telefono);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
