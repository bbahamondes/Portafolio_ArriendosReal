package com.arriendosreal.webapp.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.arriendosreal.webapp.entities.Servicios;

public interface ServiciosRepository extends JpaRepository<Servicios, BigDecimal> {
    
    Optional<Servicios> findByIdServicio(BigDecimal servicioId);

    @Procedure(name = "Servicios.createServicio")
    int createServicio(@Param("in_descripcion") String descripcion,
            @Param("in_tipos_servicio_id_tipo") int tipoServicioId,
            @Param("in_costo_operacion") int costoOperacion,
            @Param("in_valor_cliente") int valorCliente,
            @Param("in_id_departamento") int departamentoId);

    @Procedure(name = "Servicios.deleteServicio")
    int deleteServicio(@Param("in_id_servicios") int servicioId);

    @Procedure(name = "Servicios.updateServicio")
    int updateServicio(@Param("in_id_servicios") int servicioId,
            @Param("in_descripcion") String descripcion,
            @Param("in_tipos_servicio_id_tipo") int tipoServicioId,
            @Param("in_costo_operacion") int costoOperacion,
            @Param("in_valor_cliente") int valorCliente,
            @Param("in_id_departamento") int departamentoId);
}
