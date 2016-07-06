package com.tseo.studiorum.repository;

import com.tseo.studiorum.entities.Professor;
import com.tseo.studiorum.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.ProfessorRole;

import java.util.List;

public interface ProfessorRoleRepository extends JpaRepository<ProfessorRole, Integer> {

    List<ProfessorRole> findBySubject(Subject subject);

}
