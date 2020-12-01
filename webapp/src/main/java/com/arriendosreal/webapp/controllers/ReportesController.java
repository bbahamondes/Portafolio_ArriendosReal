package com.arriendosreal.webapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.arriendosreal.webapp.controllers.DepartamentosController;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@RequestMapping(value = "/api/v1/reportes", produces = "application/json; charset=utf-8")
@CrossOrigin(origins = "*")
@RestController
public class ReportesController {
       
    @Autowired
    private DepartamentosController deptoController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);

    }

    List<Departmentos> reporteDeptos(String fechaD, String fechaH, String zona) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_REPORTE_GANANCIAS")
                .returningResultSet("out_ganancias", BeanPropertyRowMapper.newInstance(Departmentos.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_fecha_desde", fechaD)
                .addValue("in_fecha_hasta", fechaH)
                .addValue("in_zona", zona);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_ganancias");
        }

    }
    
    List<Reservas> reporteDeptos2(String fechaD, String fechaH, String zona) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_REPORTE_GANANCIAS2")
                .returningResultSet("out_ganancias", BeanPropertyRowMapper.newInstance(Reservas.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_fecha_desde", fechaD)
                .addValue("in_fecha_hasta", fechaH)
                .addValue("in_zona", zona);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_ganancias");
        }

    }
    
    List<Reservas> findReservasByDepto(int departamentoId) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_RESERVAS_BY_DEPTO")
                .returningResultSet("out_reserva", BeanPropertyRowMapper.newInstance(Reservas.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_depto_id", departamentoId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_reserva");
        }

    }
    
    
    @PostMapping(value = "/ganancia/depto")
    public ResponseEntity<String> getAllDepartamentos(
            @RequestParam(name = "in_fecha_desde", required = true) String fechaD, 
            @RequestParam(name = "in_fecha_hasta", required = true) String fechaH, 
            @RequestParam(name = "in_zona", required = true) String zona) {
        
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        List<Departmentos> deps = null;
        Date date1;
        Date date2;
        try {
            deps = reporteDeptos(fechaD, fechaH, zona);
            date1 = df.parse(fechaD);
            date2 = df.parse(fechaH);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (deps != null) {
            deps.forEach(dep -> {
                List<Reservas> res = findReservasByDepto(dep.getIdDepartmento().intValue());
                List<Reservas> res3 = new ArrayList<Reservas>();
                res.forEach(re -> {
                    if(!re.getFechaEntrada().before(date1) && !re.getFechaSalida().after(date2)) {
                        res3.add(re);
                    }
                });
                Set<Reservas> res2 = new HashSet<>(res3);
                dep.setReservases(res2);
            });
            
            String json = gson.toJson(deps);
            
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Depto Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @PostMapping(value = "/ganancia/depto2")
    public ResponseEntity<String> getAllDepartamentos2(
            @RequestParam(name = "in_fecha_desde", required = true)String fechaD, 
            @RequestParam(name = "in_fecha_hasta", required = true) String fechaH, 
            @RequestParam(name = "in_zona", required = true) String zona) {
        List<Reservas> deps = null;
        try {
            deps = reporteDeptos2(fechaD, fechaH, zona);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (deps != null) {
            String json = gson.toJson(deps);
            
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Depto Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
