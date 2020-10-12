package com.arriendosreal.webapp.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

/**
 * Profiles generated by hbm2java
 */
@Entity
@Table(name = "PROFILES")

@NamedStoredProcedureQuery(name = "Profiles.createProfile", 
procedureName = "SP_CREAR_PROFILES", parameters = {
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_profile_name", type = String.class),
  @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_resultado", type = Integer.class)})

@NamedStoredProcedureQuery(name = "Profiles.deleteProfile", 
procedureName = "SP_DEL_PROFILES", parameters = {
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_profile_id", type = Integer.class),
  @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_estado", type = Integer.class)})

@NamedStoredProcedureQuery(name = "Profiles.updateProfile", 
procedureName = "SP_UPD_PROFILES", parameters = {
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_profile_id", type = Integer.class),
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_profile_name", type = String.class),
  @StoredProcedureParameter(mode = ParameterMode.OUT, name = "out_resultado", type = Integer.class)})

public class Profiles implements java.io.Serializable {

	private int profileId;
	private String profileName;
	private Set<Users> userses = new HashSet<Users>(0);

	public Profiles() {
	}

	public Profiles(int profileId) {
		this.profileId = profileId;
	}

	public Profiles(int profileId, String profileName, Set<Users> userses) {
		this.profileId = profileId;
		this.profileName = profileName;
		this.userses = userses;
	}

	@Id

	@Column(name = "PROFILE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public int getProfileId() {
		return this.profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	@Column(name = "PROFILE_NAME", length = 50)
	public String getProfileName() {
		return this.profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profiles")
	public Set<Users> getUserses() {
		return this.userses;
	}

	public void setUserses(Set<Users> userses) {
		this.userses = userses;
	}

}
