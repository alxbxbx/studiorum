package com.tseo.studiorum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
