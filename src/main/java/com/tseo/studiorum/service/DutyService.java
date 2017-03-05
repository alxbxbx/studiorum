package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.entities.Exam;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.repository.DutyRepository;

@Service
public class DutyService {

	@Autowired
	private DutyRepository dutyRepository;
	
	@Autowired
	private SubjectService subjectService;
	
	@Autowired
	private ExamService examService;
	
	public Duty findOne(Integer id){
		return dutyRepository.findOne(id);
	}
	
	public List<Duty> findAll(){
		return dutyRepository.findAll();
	}
	
	public Duty save(Duty duty){
		return dutyRepository.save(duty);
	}
	
	public void remove(Duty duty){
		//Remove duty from subject
		Subject subject = duty.getSubject();
        subject.getDuties().remove(duty);
        subjectService.save(subject);
        
        //Remove duty dependency from exams
        List<Exam> exams = examService.findByDuty(duty);
        exams.forEach(exam -> { examService.remove(exam.getId()); });
        
		dutyRepository.delete(duty.getId());
	}
	
}
