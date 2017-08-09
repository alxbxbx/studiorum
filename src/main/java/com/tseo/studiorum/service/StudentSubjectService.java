package com.tseo.studiorum.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.entities.Exam;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.entities.StudentSubject;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.SubjectDependency;
import com.tseo.studiorum.repository.StudentSubjectRepository;

@Service
public class StudentSubjectService {
	
	@Autowired
	private StudentSubjectRepository studentSubjectRepository;
	
	@Autowired
	private SubjectDependencyService subjectDependencyService;
	
	@Autowired
	private ExamService examService;
	
	public StudentSubject getStudentSubjectById(Integer id){
		return studentSubjectRepository.getOne(id);
	}
	
	public List<StudentSubject> findByStudent(Student student){
		return studentSubjectRepository.findByStudent(student);
	}
	
	public StudentSubject findByStudentAndSubject(Student student, Subject subject){
		return studentSubjectRepository.findByStudentAndSubject(student, subject);
	}
	
	public StudentSubject saveStudentSubject(StudentSubject studentSubject){
		return studentSubjectRepository.save(studentSubject);
	}
	
	/**
	 * 
	 * Method for saving student subject relationship dependency
	 * Method also check if particular subject can be passed because of 
	 * required subjects in subjectDependency object
	 * 
	 * @param student
	 * @param subject
	 * 
	 * @return studentSubject object
	 */
	public StudentSubject saveStudentSubject(Student student, Subject subject){
		StudentSubject studentSubject = new StudentSubject();
		studentSubject.setStudent(student);
		studentSubject.setSubject(subject);
		studentSubject.setPass(false);
		studentSubject.setAvailable(true);
		
		//Check if subjects this subject depending on are passed
		SubjectDependency subjectDependency = subjectDependencyService.findBySubject(subject);
		//If there is no required subjects set available true
		if(subjectDependency.getRequiredSubjects() == null){
			studentSubject.setAvailable(true);
		}else{
			//If there is any required subject, check and see if it is passed
			//In case required subject is not passed, set availability of current subject to false
			subjectDependency.getRequiredSubjects().forEach(oneSubject -> {
				StudentSubject current = studentSubjectRepository.findByStudentAndSubject(student, oneSubject);
				if(current != null && current.isPass() == false){
					studentSubject.setAvailable(false);
				}
			});
		}
		
		return studentSubjectRepository.save(studentSubject);
	}
	
	/**
	 * Method that iterates through set of duties and find exam with 
	 * particular duty and student and check if exam is passed 
	 * and calculates sum of points
	 * If sum is greater than 54 points sets pass flag true
	 * 
	 * 
	 * @param studentSubject
	 */
	public void checkIfPass(StudentSubject studentSubject){
		int points = 0;
		Set<Duty> duties = studentSubject.getSubject().getDuties();
		
		for(Duty duty : duties){
			Exam exam = examService.findByStudentAndDuty(studentSubject.getStudent(), duty);
			if(exam.isPass()){
				points += exam.getPoints();
			}
		}
		
		if(points > 54){
			studentSubject.setPass(true);
			studentSubjectRepository.save(studentSubject);
		}
	}
	
	public void deleteStudentSubject(Integer id){
		studentSubjectRepository.delete(id);
	}
	
	public void deleteStudentSubject(StudentSubject studentSubject){
		studentSubjectRepository.delete(studentSubject);
	}

}
