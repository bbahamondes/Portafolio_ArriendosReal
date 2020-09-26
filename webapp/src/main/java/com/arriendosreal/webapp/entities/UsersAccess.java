package com.arriendosreal.webapp.entities;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UsersAccess extends CrudRepository<Users, String> {

	List<Users> findByProfile(String profile);
	Users findByEmail(String email);

}