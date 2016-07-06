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

import com.tseo.studiorum.web.dto.ExamDTO;
import com.tseo.studiorum.annotations.Permission;
import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.entities.Exam;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.service.DutyService;
import com.tseo.studiorum.service.ExamService;
import com.tseo.studiorum.service.StudentService;

@RestController
@RequestMapping(value = "api/exams")
public class ExamController {

    @Autowired
    ExamService examService;

    @Autowired
    DutyService dutyService;

    @Autowired
    StudentService studentService;
    	
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ExamDTO>> getExams() {
        List<Exam> exams = examService.findAll();
        List<ExamDTO> examsDTO = new ArrayList<ExamDTO>();
        for (Exam exam : exams) {
            examsDTO.add(new ExamDTO(exam));
        }
        return new ResponseEntity<>(examsDTO, HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ExamDTO> getExam(@PathVariable Integer id) {
        Exam exam = examService.findOne(id);
        if (exam == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ExamDTO(exam), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value="/duty/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ExamDTO>> getExamsByDuty(@PathVariable Integer id) {
    	Duty duty = dutyService.findOne(id);
        List<Exam> exams = examService.findByDuty(duty);
        List<ExamDTO> examsDTO = new ArrayList<ExamDTO>();
        for (Exam exam : exams) {
            examsDTO.add(new ExamDTO(exam));
        }
        return new ResponseEntity<>(examsDTO, HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value="/student/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ExamDTO>> getExamsByStudent(@PathVariable Integer id) {
    	Student student = studentService.findOne(id);
        List<Exam> exams = examService.findByStudent(student);
        List<ExamDTO> examsDTO = new ArrayList<ExamDTO>();
        for (Exam exam : exams) {
            examsDTO.add(new ExamDTO(exam));
        }
        return new ResponseEntity<>(examsDTO, HttpStatus.OK);
    }
    
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ExamDTO> saveExam(@RequestBody ExamDTO examDTO) {
        if (examDTO.getDutyDTO() == null || examDTO.getStudentDTO() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Student student = studentService.findOne(examDTO.getStudentDTO().getId());
        Duty duty = dutyService.findOne(examDTO.getDutyDTO().getId());
        if (student == null || duty == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Exam exam = new Exam();
        exam.setPass(examDTO.getPass());
        exam.setDuty(duty);
        exam.setStudent(student);
        exam.setPoints(examDTO.getPoints());
        exam = examService.save(exam);
        student.getExams().add(exam);
        studentService.save(student);
        return new ResponseEntity<>(new ExamDTO(exam), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ExamDTO> updateExam(@RequestBody ExamDTO examDTO) {
        Exam exam = examService.findOne(examDTO.getId());
        if (exam == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        exam.setPass(examDTO.getPass());
        exam.setPoints(examDTO.getPoints());
        exam = examService.save(exam);
        return new ResponseEntity<>(new ExamDTO(exam), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteExam(@PathVariable Integer id) {
        Exam exam = examService.findOne(id);
        if (exam == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
        	Student student = studentService.findOne(exam.getStudent().getId());
        	student.getExams().remove(exam);
        	studentService.save(student);
            examService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
