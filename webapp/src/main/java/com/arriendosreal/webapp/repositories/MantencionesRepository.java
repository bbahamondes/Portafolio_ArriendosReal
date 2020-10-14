package com.arriendosreal.webapp.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import com.arriendosreal.webapp.entities.Mantenciones;

public interface MantencionesRepository extends JpaRepository<Mantenciones, Integer> {

    @Procedure(name = "Mantenciones.createMantencion")
    int createMantencion(@Param("in_fecha_inicio") Date fechaInicio, 
            @Param("in_costo") int costo,
            @Param("in_descripcion") String descripcion,
            @Param("in_idDepartamento") int departamentoId,
            @Param("in_fecha_termino") Date fechaTermino);

    @Procedure(name = "Mantenciones.deleteMantencion")
    int deleteMantencion(@Param("in_id_mantencion") int mantencionId);

    @Procedure(name = "Mantenciones.updateMantencion")
    int updateMantencion(@Param("in_id_mantencion") int mantencionId, 
            @Param("in_fecha_inicio") Date fechaInicio, 
            @Param("in_costo") int costo,
            @Param("in_descripcion") String descripcion,
            @Param("in_idDepartamento") int departamentoId,
            @Param("in_fecha_termino") Date fechaTermino);

}