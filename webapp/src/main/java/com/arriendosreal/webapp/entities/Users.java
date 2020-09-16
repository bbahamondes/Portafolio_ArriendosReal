package com.arriendosreal.webapp.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {
	
	@Id
	private String email;
	private String password;
	private String profile;
	
	protected Users() {}
	
	public Users(String email, String profile, String password) {
		this.email = email;
		this.password = password;
		this.profile = profile;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getProfile() {
		return profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
