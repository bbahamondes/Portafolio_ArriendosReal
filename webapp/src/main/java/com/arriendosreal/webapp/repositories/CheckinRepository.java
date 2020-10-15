package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import java.util.Date;

import com.arriendosreal.webapp.entities.Checkin;

public interface CheckinRepository extends JpaRepository<Checkin, Integer> {

    @Procedure(name = "Checkin.createCheckIn")
    int createCheckIn(@Param("in_fecha") Date fecha, 
            @Param("in_pago") int pago);

    @Procedure(name = "Checkin.deleteCheckIn")
    int deleteCheckIn(@Param("in_id_checkin") int checkinId);

    @Procedure(name = "Checkin.updateCheckIn")
    int updateCheckIn(@Param("in_id_checkin") int checkinId, 
            @Param("in_fecha") Date fecha, 
            @Param("in_pago") int pago);

}