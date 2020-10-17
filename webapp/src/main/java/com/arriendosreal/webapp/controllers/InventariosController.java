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
import com.arriendosreal.webapp.entities.Inventarios;
import com.arriendosreal.webapp.repositories.InventariosRepository;

//Test
import com.arriendosreal.webapp.entities.Departmentos;
import com.arriendosreal.webapp.repositories.DepartamentosRepository;

@RequestMapping(value = "/api/v1/inventarios", produces = "application/json; charset=utf-8")
@RestController
public class InventariosController {
    
    @Autowired
    private InventariosRepository inventariosRepo;
    
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
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_INVENTARIO")
                .returningResultSet("out_inventario", BeanPropertyRowMapper.newInstance(Inventarios.class));

    }

    List<Inventarios> findInventariosById(int inventarioId) {

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_inventario_id", inventarioId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_inventario");
        }

    }

    @PostMapping
    public ResponseEntity<String> createInventario(@RequestParam(name = "descripcion", required = true) String descripcion,
            @RequestParam(name = "departamentoId", required = true) int departamentoId) {

        int resultado = 0;
        try {
            resultado =  inventariosRepo.createInventario(descripcion, departamentoId) ;
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            try {
                Departmentos depto = deptoRepo.findByIdDepartmento(BigDecimal.valueOf(departamentoId)).orElse(null);
                depto.setServicioses(null);
                depto.setInventarioses(null);
                Inventarios inv = new Inventarios(BigDecimal.valueOf(resultado), depto);
                String json = gson.toJson(inv);                
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getInventarioByID(@RequestParam(name = "inventarioId", required = true) int inventarioId) {
        Inventarios inventario = new Inventarios();
        List<Inventarios> inventarios = new ArrayList<Inventarios>();
        try {
            inventarios = findInventariosById(inventarioId);
            inventario = inventarios.get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (inventario != null) {
            try {
                String json = gson.toJson(inventario);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteInventarioByID(@RequestParam(name = "inventarioId", required = true) int inventarioId) {
        int resultado = -1;
        try {
            resultado = inventariosRepo.deleteInventario(inventarioId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado != -1) {
            HashMap<String, String> respuesta = new HashMap<String, String>();
            respuesta.put("inventarioId", String.valueOf(inventarioId));
            respuesta.put("resultado", String.valueOf(resultado));
            String json = gson.toJson(respuesta);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    @PutMapping
    public ResponseEntity<String> updateInventario(@RequestParam(name = "inventarioId", required = true) int inventarioId,
            @RequestParam(name = "descripcion", required = true) String descripcion,
            @RequestParam(name = "departamentoId", required = true) int departamentoId) {
        
        int resultado = -1;
        Inventarios inventario = new Inventarios();
        try {
            resultado = inventariosRepo.updateInventario(inventarioId, descripcion, departamentoId);
            Departmentos depto = deptoRepo.findByIdDepartmento(BigDecimal.valueOf(departamentoId)).orElse(null);
            depto.setServicioses(null);
            depto.setInventarioses(null);
            depto.setMantencioneses(null);
            depto.setReservases(null);
            inventario = new Inventarios(BigDecimal.valueOf(inventarioId), depto, descripcion);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {
            String json = gson.toJson(inventario);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
