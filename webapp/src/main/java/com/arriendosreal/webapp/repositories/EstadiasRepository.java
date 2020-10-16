package com.arriendosreal.webapp.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import com.arriendosreal.webapp.entities.Estadias;

public interface EstadiasRepository extends JpaRepository<Estadias, Integer> {

    Optional<Estadias> findByIdEstadia(BigDecimal estadiaId);
    
    @Procedure(name = "Estadias.createEstadias")
    int createEstadia(@Param("in_id_checkin") int checkinId,
            @Param("in_id_checkout") int checkoutId);

    @Procedure(name = "Estadias.deleteEstadias")
    int deleteEstadia(@Param("in_id_estadia") int estadiaId);

    @Procedure(name = "Estadias.updateEstadias")
    int updateEstadia(@Param("in_id_estadia") int estadiaId, 
            @Param("in_id_checkin") int checkinId,
            @Param("in_id_checkout") int checkoutId);

}