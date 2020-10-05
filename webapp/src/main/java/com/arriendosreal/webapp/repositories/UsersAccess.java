package com.arriendosreal.webapp.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.arriendosreal.webapp.entities.Users;

public interface UsersAccess extends CrudRepository<Users, String> {

	List<Users> findByProfile(String profile);
	Users findByEmail(String email);

}