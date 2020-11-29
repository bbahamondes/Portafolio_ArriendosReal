package com.arriendosreal.webapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;

import com.arriendosreal.webapp.entities.Departmentos;
import com.arriendosreal.webapp.entities.Reservas;
import com.arriendosreal.webapp.repositories.DepartamentosRepository;
import java.math.BigDecimal;

@RequestMapping(value = "/api/v1/departamento", produces = "application/json; charset=utf-8")
@CrossOrigin(origins = "*")
@RestController
public class DepartamentosController {
    
    @Autowired
    private DepartamentosRepository departmentoRepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

    }

    List<Departmentos> findDeptoById(int departamentoId) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_DEPARTAMENTO")
                .returningResultSet("out_departamento", BeanPropertyRowMapper.newInstance(Departmentos.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_departamento_id", departamentoId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_departamento");
        }

    }
    
    List<Reservas> findReservasByDepto(int departamentoId) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_RESERVAS_BY_DEPTO")
                .returningResultSet("null", BeanPropertyRowMapper.newInstance(Reservas.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_depto_id", departamentoId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_reserva");
        }

    }
    
    List<Departmentos> findAllDeptos() {
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ALL_DEPARTAMENTO")
                .returningResultSet("out_departamento", BeanPropertyRowMapper.newInstance(Departmentos.class));
        
        SqlParameterSource paramaters = new MapSqlParameterSource();

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_departamento");
        }

    }

    @PostMapping
    public ResponseEntity<String> createDepartmento(@RequestParam(name = "nombre", required = true) String nombre,
            @RequestParam(name = "direccion", required = true) String direccion,
            @RequestParam(name = "region", required = true) String region,
            @RequestParam(name = "ciudad", required = true) String ciudad,
            @RequestParam(name = "precio", required = true) int precio,
            @RequestParam(name = "disponibilidad", required = true) boolean disponibilidad) {

        int resultado = 0;
        try {            
            resultado = departmentoRepo.createDepartamento(nombre, direccion, region, ciudad, precio, disponibilidad ? 1 : 0);
        } catch (Exception e) {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            Departmentos depto = new Departmentos(BigDecimal.valueOf(resultado), nombre, direccion, region, ciudad,
                    BigDecimal.valueOf(precio), 
                    BigDecimal.valueOf(disponibilidad ? 1 : 0));
            String json = gson.toJson(depto);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping(value = "/all")
    public ResponseEntity<String> getAllDepartamentos() {
        List<Departmentos> deps = null;
        try {
            deps = findAllDeptos();
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (deps != null) {
            deps.forEach( dep -> {
                List<Reservas> res = findReservasByDepto(dep.getIdDepartmento().intValue());
                Set<Reservas> res2 = new HashSet<>(res);
                dep.setReservases(res2);
            });
            String json = gson.toJson(deps);
            
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Depto Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getDepartamentoByID(@RequestParam(name = "departamentoId", required = true) int departamentoId) {

        Departmentos dep = null;
        List<Departmentos> deps = new ArrayList<Departmentos>();
        try {
            deps = findDeptoById(departamentoId);
            dep = deps.get(0);
            var res = findReservasByDepto(dep.getIdDepartmento().intValue());
            Set<Reservas> res2 = new HashSet<>(res);
            dep.setReservases(res2);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dep != null) {
            String json = gson.toJson(dep);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Depto Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping(value = "/{departamentoId}")
    public ResponseEntity<String> getDepartamentoByID2(@PathVariable(name = "departamentoId", required = true) int departamentoId) {

        Departmentos dep = new Departmentos();
        List<Departmentos> deps = new ArrayList<Departmentos>();
        try {
            deps = findDeptoById(departamentoId);
            dep = deps.get(0);
            var res = findReservasByDepto(dep.getIdDepartmento().intValue());
            Set<Reservas> res2 = new HashSet<>(res);
            dep.setReservases(res2);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dep != null) {
            String json = gson.toJson(dep);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Depto Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }    

    @DeleteMapping
    public ResponseEntity<String> deleteDeptoByID(@RequestParam(name = "departamentoId", required = true) int departamentoId) {
        int resultado = -1;
        try {
            resultado = departmentoRepo.deleteDepartamento(departamentoId);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado != -1) {
            String json = String.format("{ \"deptoId\": %s, \"result\": %s  }", departamentoId, resultado);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
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

        int resultado = -1;
        try {
            resultado = departmentoRepo.updateDepartamento(departamentoId, nombre, direccion, region, ciudad, precio, disponibilidad);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (resultado >= 0) {
            Departmentos depto = new Departmentos(BigDecimal.valueOf(departamentoId), nombre, direccion, region, ciudad, 
                    BigDecimal.valueOf(precio), 
                    BigDecimal.valueOf(disponibilidad ? 1 : 0));
            String json = gson.toJson(depto);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
