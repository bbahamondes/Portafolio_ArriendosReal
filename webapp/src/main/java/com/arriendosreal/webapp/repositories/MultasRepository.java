package com.arriendosreal.webapp.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.arriendosreal.webapp.entities.Multas;

public interface MultasRepository extends JpaRepository<Multas, Integer> {

    Optional<Multas> findByIdMultas(BigDecimal multaId);
    
    @Procedure(name = "Multas.createMultas")
    int createMulta(@Param("in_descripcion") String descripcion, 
            @Param("in_monto_multa") int montoMulta,
            @Param("in_idCheckOut") int checkoutId);

    @Procedure(name = "Multas.deleteMultas")
    int deleteMulta(@Param("in_id_multas") int multaId);

    @Procedure(name = "Multas.updateMultas")
    int updateMulta(@Param("in_id_multas") int multaId, 
            @Param("in_descripcion") String descripcion, 
            @Param("in_monto_multa") int montoMulta,
            @Param("in_idCheckOut") int checkoutId);

}