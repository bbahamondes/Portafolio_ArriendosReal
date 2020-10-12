package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

import com.arriendosreal.webapp.entities.Profiles;

public interface ProfilesRepository extends JpaRepository <Profiles, String> {
	
	Optional<Profiles> findByProfileId(int profileId);
	
	@Procedure(procedureName = "Profiles.createProfile")
	int createProfile(@Param("in_profile_name") String profileName);
	
	@Procedure(procedureName = "Profiles.deleteProfile")
	int deleteProfile(@Param("in_profile_id") int profileId);
	
	@Procedure(procedureName = "Profiles.updateProfile")
	int updateProfile(@Param("in_profile_id") int profileId, @Param("in_profile_name") String profileName);
}
