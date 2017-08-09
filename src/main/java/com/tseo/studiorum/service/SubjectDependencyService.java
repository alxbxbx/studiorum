package com.tseo.studiorum.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.SubjectDependency;
import com.tseo.studiorum.repository.SubjectDependencyRepository;
import com.tseo.studiorum.web.dto.SubjectDTO;
import com.tseo.studiorum.web.dto.SubjectDependencyDTO;

@Service
public class SubjectDependencyService {

	@Autowired
	private SubjectDependencyRepository subjectDependencyRepository;

	public SubjectDependency getSubjectDependencyById(Integer id) {
		return subjectDependencyRepository.findOne(id);
	}

	public SubjectDependency findBySubject(Subject subject) {
		return subjectDependencyRepository.findBySubject(subject);
	}

	public SubjectDependency saveSubjectDependency(SubjectDependency subjectDependency) {
		return subjectDependencyRepository.save(subjectDependency);
	}

	public void deleteSubjectDependency(Integer id) {
		subjectDependencyRepository.delete(id);
	}
	
	/**
	 * Method for checking dependency between two subjects
	 * 
	 * @param one
	 * @param two
	 * @return boolean
	 */
	public boolean areDependent(Subject one, Subject two) {
		SubjectDependency subjectDependencyOne = subjectDependencyRepository.findBySubject(one);
		SubjectDependency subjectDependencyTwo = subjectDependencyRepository.findBySubject(two);

		if (one.equals(two))
			return true;
		
		//Check if subject two is required subject for subject one
		for (Subject subject : subjectDependencyOne.getRequiredSubjects()) {
			if (subject.equals(two))
				return true;
		}
		
		//Check if subject one is required subject for subject two
		for (Subject subject : subjectDependencyTwo.getRequiredSubjects()) {
			if (subject.equals(one))
				return true;
		}
		return false;
	}

	/**
	 * Method for converting SubjectDependencyDTO object to SubjectDependency object
	 * 
	 * @param subjectDependencyDTO
	 * @return SubjectDependency
	 */
	public SubjectDependency DTOToDependency(SubjectDependencyDTO subjectDependencyDTO) {
		SubjectDependency subjectDependency;
		if (subjectDependencyDTO.getId() != null) {
			subjectDependency = subjectDependencyRepository.findOne(subjectDependencyDTO.getId());
		} else {
			subjectDependency = new SubjectDependency();
		}
		subjectDependency.setSubject(new Subject(subjectDependencyDTO.getSubject()));

		subjectDependency.setRequiredSubjects(new ArrayList<Subject>());

		for (SubjectDTO subjectDTO : subjectDependencyDTO.getRequiredSubjects()) {
			subjectDependency.getRequiredSubjects().add(new Subject(subjectDTO));
		}

		return subjectDependency;

	}
}
