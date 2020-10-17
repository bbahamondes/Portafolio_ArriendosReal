package com.arriendosreal.webapp.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.arriendosreal.webapp.entities.Multas;
import com.arriendosreal.webapp.repositories.CheckOutRepository;
import com.arriendosreal.webapp.repositories.MultasRepository;


@RequestMapping(value = "/api/v1/multas", produces = "application/json; charset=utf-8")
@RestController
public class MultasController {
    
    @Autowired
    private MultasRepository multasRepo;
    
    @Autowired
    private CheckOutRepository checkoutRepo;
        
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_MULTAS")
                .returningResultSet("out_multa", BeanPropertyRowMapper.newInstance(Multas.class));
    }

    List<Multas> findMultaById(int multaId) {

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_multa_id", multaId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_multa");
        }

    }

    @PostMapping
    public ResponseEntity<String> createMultas(@RequestParam(name = "descripcion", required = true) String descripcion,
            @RequestParam(name = "montoMulta", required = true) int montoMulta,
            @RequestParam(name = "checkoutId", required = true) int checkoutId) {

        int resultado = 0;
        try {
            resultado = multasRepo.createMulta(descripcion, montoMulta, checkoutId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            try {
                Checkout cout = checkoutRepo.findByIdCheckout(BigDecimal.valueOf(checkoutId)).orElse(null);
                cout.setMultases(null);
                cout.setEstadiases(null);
                Multas multa = new Multas(BigDecimal.valueOf(resultado), cout, BigDecimal.valueOf(montoMulta), descripcion);
                String json = gson.toJson(multa);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getMultasByID(@RequestParam(name = "multaId", required = true) int multaId) {
        Multas multa = new Multas();
        List<Multas> multas = new ArrayList<Multas>();
        try {
            multas =  findMultaById(multaId);
            multa = multas.get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (multa != null) {
            try {
                String json = gson.toJson(multa);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMultasByID(@RequestParam(name = "multaId", required = true) int multaId) {
        int resultado = -1;
        try {
            resultado = multasRepo.deleteMulta(multaId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado != -1) {
            HashMap<String, String> respuesta = new HashMap<String, String>();
            respuesta.put("multaId", String.valueOf(multaId));
            respuesta.put("resultado", String.valueOf(resultado));
            String json = gson.toJson(respuesta);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    @PutMapping
    public ResponseEntity<String> updateMultas(@RequestParam(name = "multaId", required = true) int multaId,
            @RequestParam(name = "descripcion", required = true) String descripcion,
            @RequestParam(name = "montoMulta", required = true) int montoMulta,
            @RequestParam(name = "checkoutId", required = true) int checkoutId) {        
        int resultado = -1;
        Multas multa = new Multas();
        try {
            resultado = multasRepo.updateMulta(multaId, descripcion, montoMulta, checkoutId);
            Checkout cout = checkoutRepo.findByIdCheckout(BigDecimal.valueOf(checkoutId)).orElse(null);
            cout.setEstadiases(null);
            cout.setMultases(null);
            multa = new Multas(BigDecimal.valueOf(multaId), cout, BigDecimal.valueOf(montoMulta), descripcion);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {            
            try {                
                String json = gson.toJson(multa);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
