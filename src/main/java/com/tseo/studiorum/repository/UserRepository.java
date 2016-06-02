package com.tseo.studiorum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tseo.studiorum.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("SELECT user FROM User user WHERE user.userName=:username AND user.password=:password")
	public User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
	
}
