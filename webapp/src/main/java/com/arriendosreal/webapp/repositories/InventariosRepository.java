package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import com.arriendosreal.webapp.entities.Inventarios;

public interface InventariosRepository extends JpaRepository<Inventarios, Integer> {

    @Procedure(name = "Inventarios.createInventario")
    int createInventario(@Param("in_descripcion") String descripcion, @Param("in_Departamentos_id_departamento") int departamentoId);

    @Procedure(procedureName = "Inventarios.deleteInventario")
    int deleteInventario(@Param("in_id_inventario") int inventarioId);

    @Procedure(procedureName = "Inventarios.updateInventario")
    int updateInventario(@Param("in_id_inventario") int inventarioId, 
            @Param("in_descripcion") String descripcion,
            @Param("in_Departamentos_id_departamento") int departamentoId);

}