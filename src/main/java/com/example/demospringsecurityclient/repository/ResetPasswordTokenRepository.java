package com.example.demospringsecurityclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demospringsecurityclient.entity.PasswordResetToken;


@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	PasswordResetToken findByToken(String token);
	
	@Query(value = "SELECT * FROM password_reset_token s where s.user_id= :id", nativeQuery = true)
	PasswordResetToken findByUser_Id(@Param("id") Long id);
}
