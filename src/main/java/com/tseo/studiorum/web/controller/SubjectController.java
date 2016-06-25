package com.tseo.studiorum.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tseo.studiorum.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.entities.ProfessorRole;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.service.SubjectService;
import com.tseo.studiorum.web.dto.DutyDTO;
import com.tseo.studiorum.web.dto.ProfessorRoleDTO;
import com.tseo.studiorum.web.dto.StudentDTO;
import com.tseo.studiorum.web.dto.SubjectDTO;

@RestController
@RequestMapping(value = "api/subjects")
public class SubjectController {

    @Autowired
    StudentService studentService;

    @Autowired
    SubjectService subjectService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SubjectDTO>> getSubjects() {
        List<Subject> subjects = subjectService.findAll();
        List<SubjectDTO> subjectsDTO = new ArrayList<SubjectDTO>();
        for (Subject subject : subjects) {
            subjectsDTO.add(new SubjectDTO(subject));
        }
        return new ResponseEntity<>(subjectsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SubjectDTO> getSubject(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new SubjectDTO(subject), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubjectDTO> saveSubject(@RequestBody SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setName(subjectDTO.getName());
        subject.setSemester(subjectDTO.getSemester());
        subject.setDescription(subjectDTO.getDescription());

        subject = subjectService.save(subject);
        return new ResponseEntity<>(new SubjectDTO(subject), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<SubjectDTO> updateSubject(@RequestBody SubjectDTO subjectDTO) {
        Subject subject = subjectService.findOne(subjectDTO.getId());

        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        subject.setName(subjectDTO.getName());
        subject.setSemester(subjectDTO.getSemester());
        subject.setDescription(subjectDTO.getDescription());

        subject = subjectService.save(subject);
        return new ResponseEntity<>(new SubjectDTO(subject), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSubject(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            subjectService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}/duties", method = RequestMethod.GET)
    public ResponseEntity<List<DutyDTO>> getDuties(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Set<Duty> duties = subject.getDuties();
        List<DutyDTO> dutiesDTO = new ArrayList<DutyDTO>();
        for (Duty duty : duties) {
            dutiesDTO.add(new DutyDTO(duty));
        }
        return new ResponseEntity<>(dutiesDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/professorRoles", method = RequestMethod.GET)
    public ResponseEntity<List<ProfessorRoleDTO>> getProfessorRoles(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Set<ProfessorRole> professorRoles = subject.getProfessorRole();
        List<ProfessorRoleDTO> professorRolesDTO = new ArrayList<ProfessorRoleDTO>();
        for (ProfessorRole professorRole : professorRoles) {
            professorRolesDTO.add(new ProfessorRoleDTO(professorRole));
        }
        return new ResponseEntity<>(professorRolesDTO, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}/students", method = RequestMethod.GET)
    public ResponseEntity<List<StudentDTO>> getStudents(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Set<Student> students = subject.getStudents();
        List<StudentDTO> studentsDTO = new ArrayList<StudentDTO>();
        for (Student student : students) {
            studentsDTO.add(new StudentDTO(student));
        }
        return new ResponseEntity<>(studentsDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/students/{ids}", method = RequestMethod.POST)
    public void saveStudents(@PathVariable Integer subjectId, @PathVariable String studentIds) {
        String[] ids = studentIds.split(",");
        for (String id : ids) {
            Subject subject = subjectService.findOne(subjectId);
            for (Student student : subject.getStudents()) {

            }
        }
    }
}
