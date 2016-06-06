package com.tseo.studiorum.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tseo.studiorum.entities.Document;
import com.tseo.studiorum.entities.Exam;
import com.tseo.studiorum.entities.Payment;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.User;
import com.tseo.studiorum.service.StudentService;
import com.tseo.studiorum.web.dto.DocumentDTO;
import com.tseo.studiorum.web.dto.ExamDTO;
import com.tseo.studiorum.web.dto.PaymentDTO;
import com.tseo.studiorum.web.dto.StudentDTO;
import com.tseo.studiorum.web.dto.SubjectDTO;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping(value="api/students")
public class StudentController {
	@Autowired
	StudentService studentService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<StudentDTO>> getStudents(HttpServletRequest req){

		/*Claims claim = (Claims) req.getAttribute("claims");
		@SuppressWarnings("unused")
		User user = (User) claim.get("userdata");
		@SuppressWarnings("unused")
		String k = "tew";
		@SuppressWarnings("unused")
		String j = "zxcvz";*/

		List<Student> students = studentService.findAll();
		List<StudentDTO> studentsDTO = new ArrayList<StudentDTO>();
		for(Student student : students){
			studentsDTO.add(new StudentDTO(student));
		}
		return new ResponseEntity<>(studentsDTO, HttpStatus.OK);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<StudentDTO> getStudent(@PathVariable Integer id){
		Student student = studentService.findOne(id);
		if(student == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO studentDTO){
		Student student = new Student();
		student.setGender(studentDTO.getGender());
		student.setAddress(studentDTO.getAddress());
		student.setDateOfBirth(studentDTO.getDateOfBirth());
		student.setJMBG(studentDTO.getJMBG());
		student.setStudentId(studentDTO.getStudentId());
		student.setName(studentDTO.getName());
		student.setLastName(studentDTO.getLastName());
		student.setRole("student");
		student.setPassword(studentDTO.getPassword());
		student.setUserName(studentDTO.getUserName());

		student = studentService.save(student);
		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO){
		Student student = studentService.findOne(studentDTO.getId());

		if(student == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		student.setGender(studentDTO.getGender());
		student.setAddress(studentDTO.getAddress());
		student.setDateOfBirth(studentDTO.getDateOfBirth());
		student.setJMBG(studentDTO.getJMBG());
		student.setStudentId(studentDTO.getStudentId());
		student.setName(studentDTO.getName());
		student.setLastName(studentDTO.getLastName());
		student.setRole(studentDTO.getRole());
		student.setPassword(studentDTO.getPassword());
		student.setUserName(studentDTO.getUserName());

		student = studentService.save(student);
		return new ResponseEntity<>(new StudentDTO(student), HttpStatus.OK);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStudent(@PathVariable Integer id){
		Student student = studentService.findOne(id);
		if(student==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else{
			studentService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value="/{id}/subjects", method = RequestMethod.GET)
	public ResponseEntity<List<SubjectDTO>> getSubjects(@PathVariable Integer id){
		Student student = studentService.findOne(id);
		if(student == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		Set<Subject> subjects = student.getSubjects();
		List<SubjectDTO> subjectsDTO = new ArrayList<SubjectDTO>();
		for(Subject subject : subjects){
			subjectsDTO.add(new SubjectDTO(subject));
		}
		return new ResponseEntity<>(subjectsDTO, HttpStatus.OK);

	}

	@RequestMapping(value="/{id}/documents", method = RequestMethod.GET)
	public ResponseEntity<List<DocumentDTO>> getDocuments(@PathVariable Integer id){
		Student student = studentService.findOne(id);
		if(student == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		Set<Document> documents = student.getDocuments();
		List<DocumentDTO> documentsDTO = new ArrayList<DocumentDTO>();
		for(Document document : documents){
			documentsDTO.add(new DocumentDTO(document));
		}
		return new ResponseEntity<>(documentsDTO, HttpStatus.OK);
	}

	@RequestMapping(value="/{id}/payments", method = RequestMethod.GET)
	public ResponseEntity<List<PaymentDTO>> getPayments(@PathVariable Integer id){
		Student student = studentService.findOne(id);
		if(student == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		Set<Payment> payments = student.getPayments();
		List<PaymentDTO> paymentsDTO = new ArrayList<PaymentDTO>();
		for(Payment payment : payments){
			paymentsDTO.add(new PaymentDTO(payment));
		}
		return new ResponseEntity<>(paymentsDTO, HttpStatus.OK);

	}

	@RequestMapping(value="/{id}/exams", method = RequestMethod.GET)
	public ResponseEntity<List<ExamDTO>> getExams(@PathVariable Integer id){
		Student student = studentService.findOne(id);
		if(student == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Set<Exam> exams = student.getExams();
		List<ExamDTO> examsDTO = new ArrayList<ExamDTO>();
		for(Exam exam : exams){
			examsDTO.add(new ExamDTO(exam));
		}
		return new ResponseEntity<>(examsDTO, HttpStatus.OK);

	}

}
