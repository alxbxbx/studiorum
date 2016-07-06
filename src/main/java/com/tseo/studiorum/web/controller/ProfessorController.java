package com.tseo.studiorum.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tseo.studiorum.annotations.Permission;
import com.tseo.studiorum.entities.Professor;
import com.tseo.studiorum.entities.ProfessorRole;
import com.tseo.studiorum.service.ProfessorRoleService;
import com.tseo.studiorum.service.ProfessorService;
import com.tseo.studiorum.web.dto.ProfessorDTO;
import com.tseo.studiorum.web.dto.ProfessorRoleDTO;

@RestController
@RequestMapping(value = "api/professors")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @Autowired
    ProfessorRoleService prService;

    @Permission(roles = {"user", "professor"})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProfessorDTO>> getProfessors() {
        List<Professor> professors = professorService.findAll();
        List<ProfessorDTO> professorsDTO = new ArrayList<ProfessorDTO>();
        for (Professor professor : professors) {
            professorsDTO.add(new ProfessorDTO(professor));
        }
        return new ResponseEntity<>(professorsDTO, HttpStatus.OK);
    }

    @Permission(roles = {"user", "professor"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ProfessorDTO> getProfessor(@PathVariable Integer id) {
        Professor professor = professorService.findOne(id);
        if (professor == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(new ProfessorDTO(professor), HttpStatus.OK);
    }

    @Permission(roles = {"user"})
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ProfessorDTO> saveProfessor(@RequestBody ProfessorDTO professorDTO) {
        Professor professor = new Professor();
        professor.setAddress(professorDTO.getAddress());
        professor.setRole("professor");
        professor.setName(professorDTO.getName());
        professor.setLastName(professorDTO.getLastName());
        professor.setUserName(professorDTO.getUserName());
        professor.setPassword(professorDTO.getPassword());
        professor.setGender(professorDTO.getGender());
        professor.setDateOfBirth(professorDTO.getDateOfBirth());
        professor.setJMBG(professorDTO.getJMBG());
        professor.setTitle(professorDTO.getTitle());

        professor = professorService.save(professor);
        return new ResponseEntity<>(new ProfessorDTO(professor), HttpStatus.OK);
    }

    @Permission(roles = {"user", "professor"})
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<ProfessorDTO> updateProfessor(@RequestBody ProfessorDTO professorDTO) {
        Professor professor = professorService.findOne(professorDTO.getId());
        if (professor == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        professor.setAddress(professorDTO.getAddress());
        professor.setName(professorDTO.getName());
        professor.setLastName(professorDTO.getLastName());
        professor.setUserName(professorDTO.getUserName());
        professor.setPassword(professorDTO.getPassword());
        professor.setGender(professorDTO.getGender());
        professor.setDateOfBirth(professorDTO.getDateOfBirth());
        professor.setJMBG(professorDTO.getJMBG());
        professor.setTitle(professorDTO.getTitle());

        professor = professorService.save(professor);
        return new ResponseEntity<>(new ProfessorDTO(professor), HttpStatus.OK);
    }

    @Permission(roles = {"user"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteProfessor(@PathVariable Integer id) {
        Professor professor = professorService.findOne(id);
        for(ProfessorRole pr: professor.getRoles()){
        	pr.setProfessor(null);
        	prService.remove(pr.getId());
        }
        if (professor == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            professorService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Permission(roles = {"user", "professor"})
    @RequestMapping(value = "/{id}/roles", method = RequestMethod.GET)
    public ResponseEntity<List<ProfessorRoleDTO>> getRoles(@PathVariable Integer id) {
        Professor professor = professorService.findOne(id);
        if (professor == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<ProfessorRole> professorRoles = professor.getRoles();
        List<ProfessorRoleDTO> professorRolesDTO = new ArrayList<ProfessorRoleDTO>();
        for (ProfessorRole professorRole : professorRoles) {
            professorRolesDTO.add(new ProfessorRoleDTO(professorRole));
        }
        return new ResponseEntity<>(professorRolesDTO, HttpStatus.OK);
    }
}
