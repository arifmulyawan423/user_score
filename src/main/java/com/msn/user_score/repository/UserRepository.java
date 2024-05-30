package com.msn.user_score.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msn.user_score.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, UUID>{

	@Transactional
	@Modifying
	@Query(value = "CALL update_user_auth_token (:id, :token)", nativeQuery = true)
	public void updateUserAuthToken(@Param("id") UUID id, @Param("token") String token);
	
	@Transactional
	@Modifying
	@Query(value = "CALL regis_user (:id, :username, :encPassword, :age, :name)", nativeQuery = true)
	public void regisUser(@Param("id") UUID id, @Param("username") String username, @Param("encPassword") String encPassword, @Param("age") Integer age, @Param("name") String name);

	public Optional<User> findByUsername(String username);
}
