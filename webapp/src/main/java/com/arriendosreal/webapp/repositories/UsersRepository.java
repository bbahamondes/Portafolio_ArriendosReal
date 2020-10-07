package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import java.math.BigDecimal;

import com.arriendosreal.webapp.entities.Users;

public interface UsersRepository extends JpaRepository<Users, String> {

	//List<Users> findByProfile(String profile);
	Users findByEmail(String email);
	
	@Procedure(procedureName = "sp_i_users")
	Users createUser(BigDecimal userId, BigDecimal profileId, String username, String email, String password);

}