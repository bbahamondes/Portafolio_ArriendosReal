package com.arriendosreal.webapp.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import com.arriendosreal.webapp.entities.TiposServicio;

public interface TiposServicioRepository extends JpaRepository<TiposServicio, String> {

    Optional<TiposServicio> findByIdTipo(BigDecimal idTipo);
    
    @Procedure(name = "TiposServicio.createTipoServicio")
    int createTipoServicio(@Param("in_descripcion") String descripcion);

    @Procedure(name = "TiposServicio.deleteTipoServicio")
    int deleteTipoServicio(@Param("in_id_tipo") int idTipo);

    @Procedure(name = "TiposServicio.updateTipoServicio")
    int updateTipoServicio(@Param("in_id_tipo") int idTipo,
            @Param("in_descripcion") String descripcion);
}
