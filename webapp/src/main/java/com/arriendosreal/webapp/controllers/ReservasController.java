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
import com.arriendosreal.webapp.entities.Reservas;
import com.arriendosreal.webapp.entities.Personas;
import com.arriendosreal.webapp.entities.Estadias;
import com.arriendosreal.webapp.entities.Departmentos;
import com.arriendosreal.webapp.repositories.ReservasRepository;
import com.arriendosreal.webapp.repositories.DepartamentosRepository;
import com.arriendosreal.webapp.repositories.EstadiasRepository;
import com.arriendosreal.webapp.repositories.PersonasRepository;


@RequestMapping(value = "/api/v1/reservas", produces = "application/json; charset=utf-8")
@CrossOrigin(origins = "*")
@RestController
public class ReservasController {
    
    @Autowired
    private ReservasRepository reservasRepo;
    
    @Autowired
    private DepartamentosRepository deptoRepo;
    
    @Autowired
    private EstadiasRepository estadiasRepo;
    
    @Autowired
    private PersonasRepository personaRepo;
        
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCallRefCursor;
    
    private Gson gson = new Gson();

    @PostConstruct
    public void init() {
        // o_name and O_NAME, same
        jdbcTemplate.setResultsMapCaseInsensitive(true);
    }

    List<Reservas> findReservaById(int reservaId) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_RESERVA")
                .returningResultSet("out_reserva", BeanPropertyRowMapper.newInstance(Reservas.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_reserva_id", reservaId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_reserva");
        }

    }
    
    List<Reservas> findReservaByUserId(int userId) {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_MIS_RESERVAS")
                .returningResultSet("out_reserva", BeanPropertyRowMapper.newInstance(Reservas.class));

        SqlParameterSource paramaters = new MapSqlParameterSource().addValue("in_user_id", userId);

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_reservas");
        }

    }
    
    List<Reservas> findAllReserva() {
        
        simpleJdbcCallRefCursor = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_GET_ALL_RESERVA")
                .returningResultSet("out_reserva", BeanPropertyRowMapper.newInstance(Reservas.class));

        SqlParameterSource paramaters = new MapSqlParameterSource();

        Map out = simpleJdbcCallRefCursor.execute(paramaters);

        if (out == null) {
            return Collections.emptyList();
        } else {
            return (List) out.get("out_reserva");
        }

    }

    @PostMapping
    public ResponseEntity<String> createReserva(@RequestParam(name = "fechaEntrada", required = true) Date fechaEntrada,
            @RequestParam(name = "fechaSalida", required = true) Date fechaSalida,
            @RequestParam(name = "departamentoId", required = true) int departamentoId,
            @RequestParam(name = "personaId", required = true) int personaId,
            @RequestParam(name = "estadiaId", required = true) int estadiaId) {

        int resultado = 0;
        try {
            resultado = reservasRepo.createReservas(fechaEntrada, fechaSalida, departamentoId, personaId, estadiaId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado > 0) {
            try {
                Departmentos depto = deptoRepo.findByIdDepartmento(BigDecimal.valueOf(departamentoId)).orElse(null);
                depto.setInventarioses(null);
                depto.setMantencioneses(null);
                depto.setServicioses(null);
                depto.setReservases(null);
                Estadias estadia = estadiasRepo.findByIdEstadia(BigDecimal.valueOf(estadiaId)).orElse(null);
                estadia.setReservases(null);
                estadia.setCheckin(null);
                estadia.setCheckout(null);
                Personas persona = personaRepo.findByIdPersona(personaId).orElse(null);
                persona.setReservases(null);
                Reservas res = new Reservas(BigDecimal.valueOf(resultado), depto, estadia, persona, fechaEntrada, fechaSalida, null);
                String json = gson.toJson(res);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping(value = "/all")
    public ResponseEntity<String> getAllReserva() {
        List<Reservas> res = null;
        try {
            res = findAllReserva();
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (res != null) {
            try {
                String json = gson.toJson(res);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @GetMapping
    public ResponseEntity<String> getReservaByID(@RequestParam(name = "reservaId", required = true) int reservaId) {
        Reservas res = new Reservas();
        try {
            res = findReservaById(reservaId).get(0);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (res != null) {
            try {
                res.setAcompananteses(null);
                String json = gson.toJson(res);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/user/{userId}" )
    public ResponseEntity<String> getReservaByUserID(@PathVariable(name = "userId", required = true) int userId) {
        
        List<Reservas> res = null;
        try {
            res = findReservaByUserId(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (res != null) {
            try {
                String json = gson.toJson(res);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("Tipos Servicio Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteReservaByID(@RequestParam(name = "reservaId", required = true) int reservaId) {
        int resultado = -1;
        try {
            resultado = reservasRepo.deleteReservas(reservaId);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado != -1) {
            HashMap<String, String> respuesta = new HashMap<String, String>();
            respuesta.put("reservaId", String.valueOf(reservaId));
            respuesta.put("resultado", String.valueOf(resultado));
            String json = gson.toJson(respuesta);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    @PutMapping
    public ResponseEntity<String> updateReserva(@RequestParam(name = "reservaId", required = true) int reservaId,
            @RequestParam(name = "fechaEntrada", required = true) Date fechaEntrada,
            @RequestParam(name = "fechaSalida", required = true) Date fechaSalida,
            @RequestParam(name = "departamentoId", required = true) int departamentoId,
            @RequestParam(name = "personaId", required = true) int personaId,
            @RequestParam(name = "estadiaId", required = true) int estadiaId) {        
        int resultado = -1;
        Reservas res = null;
        try {
            resultado = reservasRepo.updateReservas(reservaId, fechaEntrada, fechaSalida, departamentoId, personaId, estadiaId);
            Departmentos depto = deptoRepo.findByIdDepartmento(BigDecimal.valueOf(departamentoId)).orElse(null);
            depto.setInventarioses(null);
            depto.setMantencioneses(null);
            depto.setServicioses(null);
            depto.setReservases(null);
            Estadias estadia = estadiasRepo.findByIdEstadia(BigDecimal.valueOf(estadiaId)).orElse(null);
            estadia.setReservases(null);
            estadia.setCheckin(null);
            estadia.setCheckout(null);
            Personas persona = personaRepo.findByIdPersona(personaId).orElse(null);
            persona.setReservases(null);
            res = new Reservas(BigDecimal.valueOf(resultado), depto, estadia, persona, fechaEntrada, fechaSalida, null);
        } catch (Exception e) {
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resultado >= 0) {            
            try {                
                String json = gson.toJson(res);
                return new ResponseEntity<>(json, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(gson.toJson(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("NPE!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
