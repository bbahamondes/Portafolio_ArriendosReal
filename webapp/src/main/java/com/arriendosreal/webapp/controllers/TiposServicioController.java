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
import com.arriendosreal.webapp.entities.TiposServicio;
import com.arriendosreal.webapp.repositories.TiposServicioRepository;

@RequestMapping(value = "/api/v1/tiposServicio", produces = "application/json; charset=utf-8")
@RestController
public class TiposServicioController {
    
    @Autowired
    private TiposServicioRepository tipoServicioRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_TIPO_SERVICIO")
                .returningResultSet("out_tipo_servicio", BeanPropertyRowMapper.newInstance(TiposServicio.class));

    }

    List<TiposServicio> findTipoServicioById(int tipoServicioId) {

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_tipo_servicio_id", tipoServicioId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_tipo_servicio");
        }

    }

    @PostMapping
    public ResponseEntity<String> createTipoServicio(@RequestParam(name = "descripcion", required = true) String descripcion) {

        int resultado = 0;
        try {
            resultado = tipoServicioRepo.createTipoServicio(descripcion);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            try {
                TiposServicio tipo = new TiposServicio(BigDecimal.valueOf(resultado), descripcion);
                String json = gson.toJson(tipo);                
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getTipoServicioByID(@RequestParam(name = "tipoServicioId", required = true) int tipoServicioId) {
        TiposServicio tipo = new TiposServicio();
        List<TiposServicio> tipos = new ArrayList<TiposServicio>();
        try {
            tipos = findTipoServicioById(tipoServicioId);
            tipo = tipos.get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (tipo != null) {
            try {
                String json = gson.toJson(tipo);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTipoServicioByID(@RequestParam(name = "tipoServicioId", required = true) int tipoServicioId) {
        int resultado = -1;
        try {
            resultado = tipoServicioRepo.deleteTipoServicio(tipoServicioId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado != -1) {
            HashMap<String, String> respuesta = new HashMap<String, String>();
            respuesta.put("tipoServicioId", String.valueOf(tipoServicioId));
            respuesta.put("resultado", String.valueOf(resultado));
            String json = gson.toJson(respuesta);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    @PutMapping
    public ResponseEntity<String> updateTipoServicio(@RequestParam(name = "tipoServicioId", required = true) int tipoServicioId,
            @RequestParam(name = "descripcion", required = true) String descripcion) {
        
        int resultado = -1;
        TiposServicio tipo = new TiposServicio();
        try {
            resultado = tipoServicioRepo.updateTipoServicio(tipoServicioId, descripcion);
            tipo = new TiposServicio(BigDecimal.valueOf(tipoServicioId), descripcion);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {
            String json = gson.toJson(tipo);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
