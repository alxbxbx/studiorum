package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.repository.DutyRepository;

@Service
public class DutyService {

	@Autowired
	DutyRepository dutyRepository;
	
	public Duty findOne(Integer id){
		return dutyRepository.findOne(id);
	}
	
	public List<Duty> findAll(){
		return dutyRepository.findAll();
	}
	
	public Duty save(Duty duty){
		return dutyRepository.save(duty);
	}
	
	public void remove(Integer id){
		dutyRepository.delete(id);
	}
	
}
