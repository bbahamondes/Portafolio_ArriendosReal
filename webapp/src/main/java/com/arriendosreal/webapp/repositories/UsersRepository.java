package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.arriendosreal.webapp.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	//List<Users> findByProfile(String profile);
	Users findByEmail(String email);
	
	@Procedure(procedureName = "Users.createUser")
	int createUser(@Param("username") String username, @Param("email") String email, @Param("password") String password, @Param("profile_id") int profile_id);
	
	/*@Procedure(procedureName= "Users.getUserById")
	List<Users> getUserById(int user_id);*/
	
	@Query(value = "CALL SP_GET_USERS(:in_user_id);", nativeQuery = true)
    List<Users> findUsersById(@Param("in_user_id") Integer user_id);
	
	@Procedure(procedureName= "Users.getUserById")
	List<String> getUserById2(int user_id);
	
}