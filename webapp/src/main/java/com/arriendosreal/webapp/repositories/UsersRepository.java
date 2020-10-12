package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.arriendosreal.webapp.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	//List<Users> findByProfile(String profile);
	Users findByEmail(String email);
	
	@Procedure(procedureName = "Users.createUser")
	int createUser(@Param("username") String username, 
			@Param("email") String email, 
			@Param("password") String password, 
			@Param("profile_id") int profile_id);
	
	@Procedure(procedureName = "Users.deleteUser")
	int deleteUser(@Param("user_id") int user_id);
	
	@Procedure(procedureName = "Users.updateUser")
	int updateUser(@Param("in_user_id") int user_id,
			@Param("in_username") String username, 
			@Param("in_email") String email, 
			@Param("in_password") String password, 
			@Param("in_profile_id") int profile_id);
		
}