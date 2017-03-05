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

import com.tseo.studiorum.annotations.Permission;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.entities.StudentSubject;
import com.tseo.studiorum.service.StudentService;
import com.tseo.studiorum.service.StudentSubjectService;
import com.tseo.studiorum.web.dto.StudentDTO;
import com.tseo.studiorum.web.dto.StudentSubjectDTO;

@RestController
@RequestMapping(value = "api/studentsubject")
public class StudentSubjectController {
	
	@Autowired
	private StudentSubjectService studentSubjectService;
	
	@Autowired
	private StudentService studentService;
	
	@Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<StudentSubjectDTO> getOne(@PathVariable Integer id){
		StudentSubject studentSubject = studentSubjectService.getStudentSubjectById(id);
		if(studentSubject == null)
			return new ResponseEntity<StudentSubjectDTO>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<StudentSubjectDTO>(new StudentSubjectDTO(studentSubject), HttpStatus.OK);
	}
	
	@Permission(roles = {"user", "professor", "student"})
    @RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<StudentSubjectDTO>> getByStudent(@RequestBody StudentDTO studentDTO){
		Student student = studentService.findOne(studentDTO.getId());
		List<StudentSubject> studentSubjects = studentSubjectService.findByStudent(student);
		List<StudentSubjectDTO> studentSubjectDTOs = new ArrayList<StudentSubjectDTO>();
		studentSubjects.forEach(studentSubject -> {
			studentSubjectDTOs.add(new StudentSubjectDTO(studentSubject));
		});
		
		return new ResponseEntity<List<StudentSubjectDTO>>(studentSubjectDTOs, HttpStatus.OK);
	}

}
