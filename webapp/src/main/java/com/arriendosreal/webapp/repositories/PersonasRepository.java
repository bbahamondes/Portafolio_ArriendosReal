package com.arriendosreal.webapp.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import com.arriendosreal.webapp.entities.Personas;

public interface PersonasRepository extends JpaRepository<Personas, Integer> {

    Optional<Personas> findByIdPersona(int personaId);
    
    @Procedure(name = "Personas.createPersona")
    int createPersona(@Param("in_rut") String rut, @Param("in_nombre") String nombre,
            @Param("in_apellidos") String apellidos, @Param("in_telefono") String telefono,
            @Param("in_Users_user_id") int userId);

    @Procedure(name = "Personas.deletePersona")
    int deletePersona(@Param("in_id_persona") int personaId);

    @Procedure(name = "Personas.updatePersona")
    int updatePersona(@Param("in_id_persona") int personaId, @Param("in_rut") String rut,
            @Param("in_nombre") String nombre, @Param("in_apellidos") String apellidos,
            @Param("in_telefono") String telefono);

}