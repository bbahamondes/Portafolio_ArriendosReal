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
import com.arriendosreal.webapp.entities.Mantenciones;
import com.arriendosreal.webapp.repositories.MantencionesRepository;

//Test
import com.arriendosreal.webapp.entities.Departmentos;
import com.arriendosreal.webapp.repositories.DepartamentosRepository;

@RequestMapping(value = "/api/v1/mantenciones", produces = "application/json; charset=utf-8")
@RestController
public class MantencionesController {
    
    @Autowired
    private MantencionesRepository mantencionesRepo;
    
    @Autowired
    private DepartamentosRepository deptoRepo;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_MANTENCIONES")
                .returningResultSet("out_mantencion", BeanPropertyRowMapper.newInstance(Mantenciones.class));
    }
    
    List<Mantenciones> findAllMantenciones() {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ALL_MANTENCIONES")
                .returningResultSet("out_mantencion", BeanPropertyRowMapper.newInstance(Mantenciones.class));

        SqlParameterSource paramaters = new MapSqlParameterSource();

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_mantencion");
        }

    }

    List<Mantenciones> findMantencionesById(int mantencionId) {

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_mantencion_id", mantencionId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_mantencion");
        }

    }

    @PostMapping
    public ResponseEntity<String> createMantencion(@RequestParam(name = "fechaInicio", required = true) Date fechaInicio,
            @RequestParam(name = "costo", required = true) int costo,
            @RequestParam(name = "descripcion", required = true) String descripcion,
            @RequestParam(name = "departamentoId", required = true) int departamentoId,
            @RequestParam(name = "fechaTermino", required = true) Date fechaTermino) {

        int resultado = 0;
        try {
            resultado = mantencionesRepo.createMantencion(fechaInicio, costo, descripcion, departamentoId, fechaTermino);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            try {
                Departmentos depto = deptoRepo.findByIdDepartmento(BigDecimal.valueOf(departamentoId)).orElse(null);
                depto.setServicioses(null);
                depto.setInventarioses(null);
                depto.setMantencioneses(null);
                depto.setReservases(null);
                Mantenciones mant = new Mantenciones(BigDecimal.valueOf(resultado), depto, fechaInicio, BigDecimal.valueOf(costo), descripcion, fechaTermino);
                String json = gson.toJson(mant);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping(value = "/all")
    public ResponseEntity<String> getAllMantenciones() {
        List<Mantenciones> mantenciones = null;
        try {
            mantenciones = findAllMantenciones();
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (mantenciones != null) {
            try {
                String json = gson.toJson(mantenciones);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<String> getMantencionByID(@RequestParam(name = "mantencionId", required = true) int mantencionId) {
        Mantenciones mant = new Mantenciones();
        List<Mantenciones> mantenciones = new ArrayList<Mantenciones>();
        try {
            mantenciones = findMantencionesById(mantencionId);
            mant = mantenciones.get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (mant != null) {
            try {
                String json = gson.toJson(mant);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMantencionByID(@RequestParam(name = "mantencionId", required = true) int mantencionId) {
        int resultado = -1;
        try {
            resultado = mantencionesRepo.deleteMantencion(mantencionId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado != -1) {
            HashMap<String, String> respuesta = new HashMap<String, String>();
            respuesta.put("inventarioId", String.valueOf(mantencionId));
            respuesta.put("resultado", String.valueOf(resultado));
            String json = gson.toJson(respuesta);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    @PutMapping
    public ResponseEntity<String> updateMantencion(@RequestParam(name = "mantencionId", required = true) int mantencionId,
            @RequestParam(name = "fechaInicio", required = true) Date fechaInicio,
            @RequestParam(name = "costo", required = true) int costo,
            @RequestParam(name = "descripcion", required = true) String descripcion,
            @RequestParam(name = "departamentoId", required = true) int departamentoId,
            @RequestParam(name = "fechaTermino", required = true) Date fechaTermino) {
        
        int resultado = -1;
        Mantenciones mant = new Mantenciones();
        Departmentos depto = null;
        try {
            resultado = mantencionesRepo.updateMantencion(mantencionId, fechaInicio, costo, descripcion, departamentoId, fechaTermino);
            depto = deptoRepo.findByIdDepartmento(BigDecimal.valueOf(departamentoId)).orElse(null);
            depto.setServicioses(null);
            depto.setInventarioses(null);
            depto.setMantencioneses(null);
            depto.setReservases(null);
            mant = new Mantenciones(BigDecimal.valueOf(resultado), depto, fechaInicio, BigDecimal.valueOf(costo), descripcion, fechaTermino);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {            
            try {                
                String json = gson.toJson(mant);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
