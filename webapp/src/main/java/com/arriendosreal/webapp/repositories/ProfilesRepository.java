package com.arriendosreal.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.Optional;

import com.arriendosreal.webapp.entities.Profiles;

public interface ProfilesRepository extends JpaRepository <Profiles, String> {
	
	Optional<Profiles> findByProfileId(BigDecimal profileId);
}
