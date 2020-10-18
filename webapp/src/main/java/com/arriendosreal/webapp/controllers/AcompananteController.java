package com.arriendosreal.webapp.controllers;

import java.math.BigDecimal;
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
import com.arriendosreal.webapp.entities.Acompanantes;
import com.arriendosreal.webapp.entities.Reservas;
import com.arriendosreal.webapp.repositories.AcompanantesRepository;
import com.arriendosreal.webapp.repositories.ReservasRepository;
import com.google.gson.Gson;

@RequestMapping(value = "/api/v1/acompanantes", produces = "application/json; charset=utf-8")
@CrossOrigin(origins = "*")
@RestController
public class AcompananteController {

    @Autowired
    private AcompanantesRepository acompanantesRepo;
    
    @Autowired
    private ReservasRepository reservasRepo;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

    }

    List<Acompanantes> findAcompananteById(int acompananteId) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ACOMPANANTE")
                .returningResultSet("out_acompanante", BeanPropertyRowMapper.newInstance(Acompanantes.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_acompanante_id", acompananteId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_acompanante");
        }

    }
    
    List<Acompanantes> findAllAcompanante() {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ALL_ACOMPANANTE")
                .returningResultSet("out_acompanante", BeanPropertyRowMapper.newInstance(Acompanantes.class));
        
        SqlParameterSource paramaters = new MapSqlParameterSource();

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_acompanante");
        }

    }

    @PostMapping
    public ResponseEntity<String> createAcompanante(@RequestParam(name = "rut", required = true) String rut,
            @RequestParam(name = "nombre", required = true) String nombre,
            @RequestParam(name = "apellidoPat", required = true) String apellidoPat,
            @RequestParam(name = "apellidoMat", required = true) String apellidoMat,
            @RequestParam(name = "reservaId", required = true) int reservaId) {

        int resultado = 0;
        try {
            resultado = acompanantesRepo.createAcompanante(rut, nombre, apellidoPat, apellidoMat, reservaId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado > 0) {
            Reservas res = reservasRepo.findByIdReserva(BigDecimal.valueOf(reservaId)).orElse(null);
            res.setPersonas(null);
            res.setAcompananteses(null);
            res.setDepartmentos(null);
            res.setEstadias(null);
            Acompanantes aco = new Acompanantes(BigDecimal.valueOf(resultado), res, rut, nombre, apellidoPat, apellidoMat);
            String json = gson.toJson(aco);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping(value = "/all")
    public ResponseEntity<String> getAllAcompanante() {

        List<Acompanantes> aco = null;
        try {
            aco = findAllAcompanante();
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (aco != null) {            
            String json = gson.toJson(aco); 
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getAcompananteByID(@RequestParam(name = "acompananteId", required = true) int acompananteId) {

        Acompanantes aco = null;
        try {
            aco = findAcompananteById(acompananteId).get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (aco != null) {            
            String json = gson.toJson(aco); 
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteAcompananteByID(@RequestParam(name = "acompananteId", required = true) int acompananteId) {

        int resultado = -1;
        try {
            resultado = acompanantesRepo.deleteAcompanante(acompananteId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado != -1) {
            HashMap<String, Integer> dict = new HashMap<String, Integer>();
            dict.put("acompananteId", acompananteId);
            dict.put("resultado", resultado);
            return new ResponseEntity<>(gson.toJson(dict), HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<String> updateAcompanante(@RequestParam(name = "acompananteId", required = true) int acompananteId,
            @RequestParam(name = "rut", required = true) String rut,
            @RequestParam(name = "nombre", required = true) String nombre,
            @RequestParam(name = "apellidoPat", required = true) String apellidoPat,
            @RequestParam(name = "apellidoMat", required = true) String apellidoMat,
            @RequestParam(name = "reservaId", required = true) int reservaId) {

        int resultado = -1;
        try {
            resultado = acompanantesRepo.updateAcompanante(acompananteId, rut, nombre, apellidoPat, apellidoMat, reservaId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {
            Reservas res = reservasRepo.findByIdReserva(BigDecimal.valueOf(reservaId)).orElse(null);
            res.setPersonas(null);
            res.setAcompananteses(null);
            res.setDepartmentos(null);
            res.setEstadias(null);
            Acompanantes aco = new Acompanantes(BigDecimal.valueOf(acompananteId), res, rut, nombre, apellidoPat, apellidoMat);
            String json = gson.toJson(aco);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
