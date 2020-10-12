package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import com.arriendosreal.webapp.entities.Departmentos;

public interface DepartamentosRepository extends JpaRepository<Departmentos, Integer> {

    // List<Users> findByProfile(String profile);

    Optional<Departmentos> findByIdDepartmento(int departamentoId);

    @Procedure(procedureName = "Departmentos.createDepartmento")
    int createDepartamento(@Param("in_nombre") String nombre, 
            @Param("in_direccion") String direccion,
            @Param("in_region") String region,
            @Param("in_ciudad") String ciudad,
            @Param("in_precio") int precio,
            @Param("in_disponibilidad") char disponibilidad);

    @Procedure(procedureName = "Departmentos.deleteDepartmento")
    int deleteDepartamento(@Param("in_id_departamento") int departamentoId);

    @Procedure(procedureName = "Departmentos.updateDepartmento")
    int updateDepartamento(@Param("in_id_departamento") int departamentoId, 
            @Param("in_nombre") String nombre, 
            @Param("in_direccion") String direccion,
            @Param("in_region") String region,
            @Param("in_ciudad") String ciudad,
            @Param("in_precio") int precio,
            @Param("in_disponibilidad") Boolean disponibilidad);

}