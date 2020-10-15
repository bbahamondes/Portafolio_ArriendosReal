package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import java.util.Date;

import com.arriendosreal.webapp.entities.Checkout;

public interface CheckOutRepository extends JpaRepository<Checkout, Integer> {

    @Procedure(name = "Checkout.createCheckOut")
    int createCheckOut(@Param("in_fecha") Date fecha);

    @Procedure(name = "Checkout.deleteCheckOut")
    int deleteCheckOut(@Param("in_id_checkout") int checkoutId);

    @Procedure(name = "Checkout.updateCheckOut")
    int updateCheckOut(@Param("in_id_checkout") int checkoutId, 
            @Param("in_fecha") Date fecha);

}