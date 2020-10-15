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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.arriendosreal.webapp.entities.Checkout;
import com.arriendosreal.webapp.repositories.CheckOutRepository;


@RequestMapping(value = "/api/v1/checkout", produces = "application/json; charset=utf-8")
@RestController
public class CheckOutController {
    
    @Autowired
    private CheckOutRepository checkOutRepo;
        
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_CHECKOUT")
                .returningResultSet("out_checkout", BeanPropertyRowMapper.newInstance(Checkout.class));
    }

    List<Checkout> findCheckOutById(int checkoutId) {

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_checkout_id", checkoutId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_checkout");
        }

    }

    @PostMapping
    public ResponseEntity<String> createCheckout(@RequestParam(name = "fecha", required = true) Date fecha) {

        int resultado = 0;
        try {
            resultado = checkOutRepo.createCheckOut(fecha);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            try {
                Checkout cout = new Checkout(BigDecimal.valueOf(resultado), fecha, null, null);
                String json = gson.toJson(cout);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getCheckoutByID(@RequestParam(name = "checkoutId", required = true) int checkoutId) {
        Checkout cout = new Checkout();
        List<Checkout> couts = new ArrayList<Checkout>();
        try {
            couts = findCheckOutById(checkoutId);
            cout = couts.get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cout != null) {
            try {
                cout.setEstadiases(null);
                cout.setMultases(null);
                String json = gson.toJson(cout);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCheckOutByID(@RequestParam(name = "checkoutId", required = true) int checkoutId) {
        int resultado = -1;
        try {
            resultado = checkOutRepo.deleteCheckOut(checkoutId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado != -1) {
            HashMap<String, String> respuesta = new HashMap<String, String>();
            respuesta.put("checkoutId", String.valueOf(checkoutId));
            respuesta.put("resultado", String.valueOf(resultado));
            String json = gson.toJson(respuesta);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    @PutMapping
    public ResponseEntity<String> updateCheckOut(@RequestParam(name = "checkoutId", required = true) int checkoutId,
            @RequestParam(name = "fecha", required = true) Date fecha) {        
        int resultado = -1;
        Checkout cout = new Checkout();
        try {
            resultado = checkOutRepo.updateCheckOut(checkoutId, fecha);
            cout = new Checkout(BigDecimal.valueOf(checkoutId), fecha, null, null);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {            
            try {                
                String json = gson.toJson(cout);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
