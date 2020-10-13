package com.arriendosreal.webapp.controllers;

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

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_TIPO_SERVICIO")
                .returningResultSet("out_departamento", BeanPropertyRowMapper.newInstance(TiposServicio.class));

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
            throw e;
            //System.out.println(e.toString());
        }

        if (resultado > 0) {
            String json = "{\"tipoServicioId\": \"%s\", "
                    + "\"descripcion\": \"%s\" }";
            json = String.format(json, resultado, descripcion);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getDepartamentoByID(@RequestParam(name = "departamentoId", required = true) int departamentoId) {
        return new ResponseEntity<>("Depto Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDeptoByID(@RequestParam(name = "departamentoId", required = true) int departamentoId) {
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<String> updateDepartamento(@RequestParam(name = "departamentoId", required = true) int departamentoId,
            @RequestParam(name = "nombre", required = true) String nombre,
            @RequestParam(name = "direccion", required = true) String direccion,
            @RequestParam(name = "region", required = true) String region,
            @RequestParam(name = "ciudad", required = true) String ciudad,
            @RequestParam(name = "precio", required = true) int precio,
            @RequestParam(name = "disponibilidad", required = true) boolean disponibilidad) {

        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
