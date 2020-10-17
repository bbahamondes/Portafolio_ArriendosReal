package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import com.arriendosreal.webapp.entities.Reservas;

public interface ReservasRepository extends JpaRepository<Reservas, Integer> {

    Optional<Reservas> findByIdReserva(BigDecimal reservaId);
    
    @Procedure(name = "Reservas.createReservas")
    int createReservas(@Param("in_fecha_entrada") Date fechaEntrada, 
            @Param("in_fecha_salida") Date fechaSalida,
            @Param("in_id_departamento") int departamentoId, 
            @Param("in_Personas_id_persona") int personaId, 
            @Param("in_Estadias_id_estadia") int estadiaId);

    @Procedure(name = "Reservas.deleteReservas")
    int deleteReservas(@Param("in_id_reserva") int reservaId);

    @Procedure(name = "Reservas.updateReservas")
    int updateReservas(@Param("in_id_reserva") int reservaId, 
            @Param("in_fecha_entrada") Date fechaEntrada, 
            @Param("in_fecha_salida") Date fechaSalida,
            @Param("in_id_departamento") int departamentoId, 
            @Param("in_Personas_id_persona") int personaId, 
            @Param("in_Estadias_id_estadia") int estadiaId);

}