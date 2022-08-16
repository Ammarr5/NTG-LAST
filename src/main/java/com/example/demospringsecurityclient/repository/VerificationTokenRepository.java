package com.example.demospringsecurityclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demospringsecurityclient.entity.VerificationToken;

@Repository
public interface VerificationTokenRepository extends  JpaRepository<VerificationToken, Long>{

	VerificationToken findByToken(String token);
	
	@Query(value = "SELECT * FROM verification_token s where s.user_id= :id", nativeQuery = true)
	VerificationToken findByUser_Id(@Param("id") Long id);

}
