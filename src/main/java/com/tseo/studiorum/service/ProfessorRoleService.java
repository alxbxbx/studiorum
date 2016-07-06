package com.tseo.studiorum.service;

import java.util.List;

import com.tseo.studiorum.entities.Professor;
import com.tseo.studiorum.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.ProfessorRole;
import com.tseo.studiorum.repository.ProfessorRoleRepository;

@Service
public class ProfessorRoleService {

    @Autowired
    ProfessorRoleRepository prRepository;

    public ProfessorRole findOne(Integer id) {
        return prRepository.findOne(id);
    }

    public List<ProfessorRole> findAll() {
        return prRepository.findAll();
    }

    public ProfessorRole save(ProfessorRole professorRole) {
        return prRepository.save(professorRole);
    }

    public void remove(Integer id) {
        prRepository.delete(id);
    }

    public List<ProfessorRole> findBySubject(Subject subject) { return prRepository.findBySubject(subject); }

}
