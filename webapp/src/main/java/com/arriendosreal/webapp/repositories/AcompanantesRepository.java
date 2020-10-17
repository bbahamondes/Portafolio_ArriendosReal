package com.arriendosreal.webapp.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.arriendosreal.webapp.entities.Acompanantes;

public interface AcompanantesRepository extends JpaRepository<Acompanantes, Integer> {

    Optional<Acompanantes> findByIdAcompanante(BigDecimal acompananteId);
    
    @Procedure(name = "Acompanantes.createAcompanante")
    int createAcompanante(@Param("in_rut") String rut, 
            @Param("in_nombre") String nombre,
            @Param("in_apellido_pat") String apellidoPat,
            @Param("in_apellido_mat") String apellidoMat, 
            @Param("in_Reservas_id_reserva") int reservaId);

    @Procedure(name = "Acompanantes.deleteAcompanante")
    int deleteAcompanante(@Param("in_id_acompanante") int acompananteId);

    @Procedure(name = "Acompanantes.updateAcompanante")
    int updateAcompanante(@Param("in_id_acompanante") int acompananteId, 
            @Param("in_rut") String rut, 
            @Param("in_nombre") String nombre,
            @Param("in_apellido_pat") String apellidoPat,
            @Param("in_apellido_mat") String apellidoMat, 
            @Param("in_Reservas_id_reserva") int reservaId);

}