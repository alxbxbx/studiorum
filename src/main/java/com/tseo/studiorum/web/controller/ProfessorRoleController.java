package com.tseo.studiorum.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tseo.studiorum.entities.Professor;
import com.tseo.studiorum.entities.ProfessorRole;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.service.ProfessorRoleService;
import com.tseo.studiorum.service.ProfessorService;
import com.tseo.studiorum.service.SubjectService;
import com.tseo.studiorum.web.dto.ProfessorRoleDTO;

@RestController
@RequestMapping(value = "api/professorRoles")
public class ProfessorRoleController {

    @Autowired
    ProfessorRoleService prService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    ProfessorService professorService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProfessorRoleDTO>> getProfessorRoles() {
        List<ProfessorRole> professorRoles = prService.findAll();
        List<ProfessorRoleDTO> profesorRolesDTO = new ArrayList<ProfessorRoleDTO>();
        for (ProfessorRole professorRole : professorRoles) {
            profesorRolesDTO.add(new ProfessorRoleDTO(professorRole));
        }

        return new ResponseEntity<>(profesorRolesDTO, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProfessorRoleDTO> saveProfessorRole(@RequestBody ProfessorRoleDTO professorRoleDTO) {
        if (professorRoleDTO.getProfessorDTO() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (professorRoleDTO.getSubjectDTO() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Subject subject = subjectService.findOne(professorRoleDTO.getSubjectDTO().getId());
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Professor professor = professorService.findOne(professorRoleDTO.getProfessorDTO().getId());
        if (professor == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        ProfessorRole professorRole = new ProfessorRole();
        professorRole.setProfessor(professor);
        professorRole.setSubject(subject);
        professorRole.setRole(professorRoleDTO.getRole());

        professorRole = prService.save(professorRole);
        return new ResponseEntity<>(new ProfessorRoleDTO(professorRole), HttpStatus.OK);


    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ProfessorRoleDTO> updateProfessorRole(@RequestBody ProfessorRoleDTO professorRoleDTO) {
        ProfessorRole professorRole = prService.findOne(professorRoleDTO.getId());
        if (professorRole == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        professorRole.setRole(professorRoleDTO.getRole());
        professorRole = prService.save(professorRole);

        return new ResponseEntity<>(new ProfessorRoleDTO(professorRole), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteResponsePayment(@PathVariable Integer id) {
        ProfessorRole professorRole = prService.findOne(id);
        if (professorRole == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            prService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }

    }


}
