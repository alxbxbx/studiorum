package com.tseo.studiorum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.entities.ProfessorRole;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.SubjectDependency;
import com.tseo.studiorum.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private DutyService dutyService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ProfessorRoleService professorRoleService;
    
    @Autowired
    private SubjectDependencyService subjectDependencyService;

    public Subject findOne(Integer id) {
        return subjectRepository.findOne(id);

    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }
    
    /**
     * Method that finds every subject that is possible to be
     * required for subject passed to method
     * 
     * @param subject
     * @return List<Subject>
     */
    public List<Subject> findPossibleDependency(Subject subject){
    	List<Subject> allSubjects = subjectRepository.findAll();
    	List<Subject> availableSubjects = new ArrayList<Subject>();
    	for(Subject oneSubject : allSubjects){
    		if(!subjectDependencyService.areDependent(subject, oneSubject)){
    			availableSubjects.add(oneSubject);
    		}
    	}
    	
    	return availableSubjects;
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }
    /**
     * Method removes every dependency this subject had
     * 
     * @param id
     */
    public void remove(Integer id) {
    	Subject subject = subjectRepository.findOne(id);
    	
    	//Removing every duty this subject had
    	for(Duty duty : subject.getDuties()){
    		dutyService.remove(duty);
    	}
    	
    	//Removing subject from every students
    	for(Student student : subject.getStudents()){
    		student.getSubjects().remove(subject);
    		studentService.save(student);
    	}
    	
    	//Removing professorRole object with particular subject
    	for(ProfessorRole professorRole : subject.getProfessorRole()){
    		professorRoleService.remove(professorRole.getId());
    	}
    	
    	SubjectDependency subjectDependency = subjectDependencyService.findBySubject(subject);
    	subjectDependencyService.deleteSubjectDependency(subjectDependency.getId());
    	
        subjectRepository.delete(id);
    }

}
