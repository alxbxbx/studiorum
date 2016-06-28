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
import com.tseo.studiorum.entities.Duty;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.service.DutyService;
import com.tseo.studiorum.service.SubjectService;
import com.tseo.studiorum.web.dto.DutyDTO;

@RestController
@RequestMapping(value = "api/duties")
public class DutyController {

    @Autowired
    DutyService dutyService;

    @Autowired
    SubjectService subjectService;
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DutyDTO>> getDuties() {
        List<Duty> duties = dutyService.findAll();
        List<DutyDTO> dutiesDTO = new ArrayList<DutyDTO>();
        for (Duty duty : duties) {
            dutiesDTO.add(new DutyDTO(duty));
        }
        return new ResponseEntity<>(dutiesDTO, HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DutyDTO> getDuty(@PathVariable Integer id) {
        Duty duty = dutyService.findOne(id);
        if (duty == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new DutyDTO(duty), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DutyDTO> saveDuty(@RequestBody DutyDTO dutyDTO) {
        if (dutyDTO.getSubjectDTO() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Subject subject = subjectService.findOne(dutyDTO.getSubjectDTO().getId());
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Duty duty = new Duty();
        duty.setDate(dutyDTO.getDate());
        duty.setMaxPoints(dutyDTO.getMaxPoints());
        duty.setSubject(subject);
        duty.setTypeOfDuty(dutyDTO.getTypeOfDuty());
        duty = dutyService.save(duty);
        return new ResponseEntity<>(new DutyDTO(duty), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<DutyDTO> updateDuty(@RequestBody DutyDTO dutyDTO) {
        Duty duty = dutyService.findOne(dutyDTO.getId());
        if (duty == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        duty.setDate(dutyDTO.getDate());
        duty.setMaxPoints(dutyDTO.getMaxPoints());
        duty.setTypeOfDuty(dutyDTO.getTypeOfDuty());
        duty = dutyService.save(duty);
        return new ResponseEntity<>(new DutyDTO(duty), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteDuty(@PathVariable Integer id) {
        Duty duty = dutyService.findOne(id);
        if (duty == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            dutyService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
