package com.tseo.studiorum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.SubjectDependency;
import com.tseo.studiorum.repository.SubjectDependencyRepository;

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

	public boolean areDependent(Subject one, Subject two) {
		SubjectDependency subjectDependencyOne = subjectDependencyRepository.findBySubject(one);
		SubjectDependency subjectDependencyTwo = subjectDependencyRepository.findBySubject(two);
		
		if (one.equals(two))
			return true;

		for (Subject subject : subjectDependencyOne.getRequiredSubjects()) {
			if (subject.equals(two))
				return true;
		}
		for (Subject subject : subjectDependencyTwo.getRequiredSubjects()) {
			if (subject.equals(one))
				return true;
		}
		return false;
	}
}
