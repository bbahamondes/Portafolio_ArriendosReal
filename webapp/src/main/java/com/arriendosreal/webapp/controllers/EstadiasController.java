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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.arriendosreal.webapp.entities.Checkout;
import com.arriendosreal.webapp.entities.Checkin;
import com.arriendosreal.webapp.entities.Estadias;
import com.arriendosreal.webapp.repositories.CheckOutRepository;
import com.arriendosreal.webapp.repositories.CheckinRepository;
import com.arriendosreal.webapp.repositories.EstadiasRepository;


@RequestMapping(value = "/api/v1/estadias", produces = "application/json; charset=utf-8")
@RestController
public class EstadiasController {
        
    @Autowired
    private CheckOutRepository checkoutRepo;
    
    @Autowired
    private CheckinRepository checkinRepo;
    
    @Autowired
    private EstadiasRepository estadiasRepo;
        
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ESTADIA")
                .returningResultSet("out_estadia", BeanPropertyRowMapper.newInstance(Estadias.class));
    }

    List<Estadias> findEstadiaById(int estadiaId) {

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_estadia_id", estadiaId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_estadia");
        }

    }

    @PostMapping
    public ResponseEntity<String> createEstadia(@RequestParam(name = "checkinId", required = true) int checkinId,
            @RequestParam(name = "checkoutId", required = true) int checkoutId) {

        int resultado = 0;
        try {
            resultado = estadiasRepo.createEstadia(checkinId, checkoutId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            try {
                Checkout cout = checkoutRepo.findByIdCheckout(BigDecimal.valueOf(checkoutId)).orElse(null);
                Checkin cin = checkinRepo.findByIdCheckin(BigDecimal.valueOf(checkoutId)).orElse(null);
                cout.setMultases(null);
                cout.setEstadiases(null);
                cin.setEstadiases(null);
                Estadias estadia = new Estadias(BigDecimal.valueOf(resultado), cin, cout, null);
                String json = gson.toJson(estadia);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getEstadiaByID(@RequestParam(name = "estadiaId", required = true) int estadiaId) {
        Estadias estadia = new Estadias();
        try {
            estadia =  findEstadiaById(estadiaId).get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (estadia != null) {
            try {
                String json = gson.toJson(estadia);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteEstadiaByID(@RequestParam(name = "estadiaId", required = true) int estadiaId) {
        int resultado = -1;
        try {
            resultado = estadiasRepo.deleteEstadia(estadiaId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado != -1) {
            HashMap<String, String> respuesta = new HashMap<String, String>();
            respuesta.put("estadiaId", String.valueOf(estadiaId));
            respuesta.put("resultado", String.valueOf(resultado));
            String json = gson.toJson(respuesta);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    @PutMapping
    public ResponseEntity<String> updateEstadia(@RequestParam(name = "estadiaId", required = true) int estadiaId,
            @RequestParam(name = "checkinId", required = true) int checkinId,
            @RequestParam(name = "checkoutId", required = true) int checkoutId) {        
        int resultado = -1;
        Estadias estadia = null;
        try {
            resultado = estadiasRepo.updateEstadia(estadiaId, checkinId, checkoutId);
            Checkout cout = checkoutRepo.findByIdCheckout(BigDecimal.valueOf(checkoutId)).orElse(null);
            cout.setEstadiases(null);
            cout.setMultases(null);
            Checkin cin = checkinRepo.findByIdCheckin(BigDecimal.valueOf(checkinId)).orElse(null);
            cin.setEstadiases(null);
            estadia = new Estadias(BigDecimal.valueOf(estadiaId), cin, cout, null);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {            
            try {                
                String json = gson.toJson(estadia);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
