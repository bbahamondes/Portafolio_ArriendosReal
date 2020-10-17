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
import com.arriendosreal.webapp.entities.Servicios;
import com.arriendosreal.webapp.repositories.ServiciosRepository;

//Test
import com.arriendosreal.webapp.entities.Departmentos;
import com.arriendosreal.webapp.repositories.DepartamentosRepository;
import com.arriendosreal.webapp.entities.TiposServicio;
import com.arriendosreal.webapp.repositories.TiposServicioRepository;

@RequestMapping(value = "/api/v1/servicios", produces = "application/json; charset=utf-8")
@RestController
public class ServiciosController {
    
    @Autowired
    private ServiciosRepository serviciosRepo;
    
    @Autowired
    private DepartamentosRepository deptoRepo;
    
    @Autowired
    private TiposServicioRepository tipoRepo;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

        // Convert o_c_book SYS_REFCURSOR to List<Book>
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_SERVICIO")
                .returningResultSet("out_servicio", BeanPropertyRowMapper.newInstance(Servicios.class));

    }

    List<Servicios> findServiciosById(int servicioId) {

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_servicio_id", servicioId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_servicio");
        }

    }

    @PostMapping
    public ResponseEntity<String> createServicio(@RequestParam(name = "descripcion", required = true) String descripcion,
            @RequestParam(name = "tipoServicioId", required = true) int tipoServicioId,
            @RequestParam(name = "costoOperacion", required = true) int costoOperacion,
            @RequestParam(name = "valorCliente", required = true) int valorCliente,
            @RequestParam(name = "departamentoId", required = true) int departamentoId) {

        int resultado = 0;
        try {
            resultado = serviciosRepo.createServicio(descripcion, tipoServicioId, costoOperacion, valorCliente, departamentoId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            try {
                Departmentos depto = deptoRepo.findByIdDepartmento(BigDecimal.valueOf(departamentoId)).orElse(null);
                depto.setServicioses(null);
                TiposServicio tipo = tipoRepo.findByIdTipo(BigDecimal.valueOf(tipoServicioId)).orElse(null);
                Servicios servicio = new Servicios(BigDecimal.valueOf(resultado), depto, tipo, descripcion, BigDecimal.valueOf(costoOperacion), BigDecimal.valueOf(valorCliente));
                String json = gson.toJson(servicio);                
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<String> getServicioByID(@RequestParam(name = "servicioId", required = true) int servicioId) {
        Servicios servicio = new Servicios();
        List<Servicios> servicios = new ArrayList<Servicios >();
        try {
            servicios = findServiciosById(servicioId);
            servicio = servicios.get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (servicio != null) {
            try {
                String json = gson.toJson(servicio);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteServicioByID(@RequestParam(name = "servicioId", required = true) int servicioId) {
        int resultado = -1;
        try {
            resultado = serviciosRepo.deleteServicio(servicioId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado != -1) {
            HashMap<String, String> respuesta = new HashMap<String, String>();
            respuesta.put("tipoServicioId", String.valueOf(servicioId));
            respuesta.put("resultado", String.valueOf(resultado));
            String json = gson.toJson(respuesta);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    @PutMapping
    public ResponseEntity<String> updateServicio(@RequestParam(name = "servicioId", required = true) int servicioId,
            @RequestParam(name = "descripcion", required = true) String descripcion,
            @RequestParam(name = "tipoServicioId", required = true) int tipoServicioId,
            @RequestParam(name = "costoOperacion", required = true) int costoOperacion,
            @RequestParam(name = "valorCliente", required = true) int valorCliente,
            @RequestParam(name = "departamentoId", required = true) int departamentoId) {
        
        int resultado = -1;
        Servicios servicio = new Servicios();
        try {
            resultado = serviciosRepo.updateServicio(servicioId, descripcion, tipoServicioId, costoOperacion, valorCliente, departamentoId);
            Departmentos depto = deptoRepo.findByIdDepartmento(BigDecimal.valueOf(departamentoId)).orElse(null);
            depto.setServicioses(null);
            TiposServicio tipo = tipoRepo.findByIdTipo(BigDecimal.valueOf(tipoServicioId)).orElse(null);
            servicio = new Servicios(BigDecimal.valueOf(servicioId), depto, tipo, descripcion, BigDecimal.valueOf(costoOperacion), BigDecimal.valueOf(valorCliente));
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {
            String json = gson.toJson(servicio);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
