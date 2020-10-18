package com.arriendosreal.webapp.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.arriendosreal.webapp.entities.Checkin;
import com.arriendosreal.webapp.repositories.CheckinRepository;


@RequestMapping(value = "/api/v1/checkin", produces = "application/json; charset=utf-8")
@CrossOrigin(origins = "*")
@RestController
public class CheckinController {
    
    @Autowired
    private CheckinRepository checkInRepo;
        
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        jdbcTemplate.setResultsMapCaseInsensitive(true);        
    }
    
    List<Checkin> findAllCheckIn() {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ALL_CHECKIN")
                .returningResultSet("out_checkin", BeanPropertyRowMapper.newInstance(Checkin.class));

        SqlParameterSource paramaters = new MapSqlParameterSource();

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_checkin");
        }

    }

    List<Checkin> findCheckInById(int checkinId) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_CHECKIN")
                .returningResultSet("out_checkin", BeanPropertyRowMapper.newInstance(Checkin.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_checkin_id", checkinId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_checkin");
        }

    }

    @PostMapping
    public ResponseEntity<String> createCheckin(@RequestParam(name = "fecha", required = true) Date fecha,
            @RequestParam(name = "pago", required = true) int pago) {

        int resultado = 0;
        try {
            resultado = checkInRepo.createCheckIn(fecha, pago);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            try {
                Checkin cin = new Checkin(BigDecimal.valueOf(resultado), fecha, BigDecimal.valueOf(pago), null);
                String json = gson.toJson(cin);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    
    @GetMapping(value = "/all")
    public ResponseEntity<String> getAllCheckin() {
        List<Checkin> cins = new ArrayList<Checkin>();
        try {
            cins = findAllCheckIn();
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cins != null) {
            try {
                String json = gson.toJson(cins);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<String> getCheckinByID(@RequestParam(name = "checkinId", required = true) int checkinId) {
        Checkin cin = new Checkin();
        List<Checkin> cins = new ArrayList<Checkin>();
        try {
            cins = findCheckInById(checkinId);
            cin = cins.get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cin != null) {
            try {
                cin.setEstadiases(null);
                String json = gson.toJson(cin);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCheckInByID(@RequestParam(name = "checkinId", required = true) int checkinId) {
        int resultado = -1;
        try {
            resultado = checkInRepo.deleteCheckIn(checkinId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado != -1) {
            HashMap<String, String> respuesta = new HashMap<String, String>();
            respuesta.put("checkinId", String.valueOf(checkinId));
            respuesta.put("resultado", String.valueOf(resultado));
            String json = gson.toJson(respuesta);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    @PutMapping
    public ResponseEntity<String> updateCheckIn(@RequestParam(name = "checkinId", required = true) int checkinId,
            @RequestParam(name = "fecha", required = true) Date fecha,
            @RequestParam(name = "pago", required = true) int pago) {        
        int resultado = -1;
        Checkin cin = new Checkin();
        try {
            resultado = checkInRepo.updateCheckIn(checkinId, fecha, pago);
            cin = new Checkin(BigDecimal.valueOf(checkinId), fecha, BigDecimal.valueOf(pago), null);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {            
            try {                
                String json = gson.toJson(cin);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
