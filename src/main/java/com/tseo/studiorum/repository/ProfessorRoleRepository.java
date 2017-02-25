package com.tseo.studiorum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.ProfessorRole;
import com.tseo.studiorum.entities.Subject;

public interface ProfessorRoleRepository extends JpaRepository<ProfessorRole, Integer> {

    List<ProfessorRole> findBySubject(Subject subject);

}
