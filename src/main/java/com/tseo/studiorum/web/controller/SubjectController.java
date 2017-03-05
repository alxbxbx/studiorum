package com.tseo.studiorum.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.tseo.studiorum.entities.ProfessorRole;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.entities.StudentSubject;
import com.tseo.studiorum.entities.Subject;
import com.tseo.studiorum.entities.SubjectDependency;
import com.tseo.studiorum.service.ProfessorRoleService;
import com.tseo.studiorum.service.StudentService;
import com.tseo.studiorum.service.StudentSubjectService;
import com.tseo.studiorum.service.SubjectDependencyService;
import com.tseo.studiorum.service.SubjectService;
import com.tseo.studiorum.web.dto.DutyDTO;
import com.tseo.studiorum.web.dto.ProfessorRoleDTO;
import com.tseo.studiorum.web.dto.StudentDTO;
import com.tseo.studiorum.web.dto.SubjectDTO;

@RestController
@RequestMapping(value = "api/subjects")
public class SubjectController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private ProfessorRoleService prService;
    
    @Autowired
    private StudentSubjectService studentSubjectService;
    
    @Autowired
    private SubjectDependencyService subjectDependencyService;
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SubjectDTO>> getSubjects() {
        List<Subject> subjects = subjectService.findAll();
        List<SubjectDTO> subjectsDTO = new ArrayList<SubjectDTO>();
        for (Subject subject : subjects) {
            subjectsDTO.add(new SubjectDTO(subject));
        }
        return new ResponseEntity<>(subjectsDTO, HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<SubjectDTO> getSubject(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new SubjectDTO(subject), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(value = "/available/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<SubjectDTO>> getAvailableForDependency(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if(subject == null)
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<Subject> subjects = subjectService.findPossibleDependency(subject);
        List<SubjectDTO> subjectsDTO = new ArrayList<SubjectDTO>();
        for (Subject oneSubject : subjects) {
            subjectsDTO.add(new SubjectDTO(oneSubject));
        }
        return new ResponseEntity<>(subjectsDTO, HttpStatus.OK);
    }
    
    /**
     * 
     * Method for saving new subject
     * Method also creates new subjectDependency so we can add required subjects later
     * 
     * @param subjectDTO
     * @return subjectDTO
     */
    @Permission(roles = {"user", "professor"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubjectDTO> saveSubject(@RequestBody SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setName(subjectDTO.getName());
        subject.setSemester(subjectDTO.getSemester());
        subject.setDescription(subjectDTO.getDescription());

        subject = subjectService.save(subject);
        
        SubjectDependency subjectDependency = new SubjectDependency();
        subjectDependency.setSubject(subject);
        subjectDependencyService.saveSubjectDependency(subjectDependency);
        
        return new ResponseEntity<>(new SubjectDTO(subject), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<SubjectDTO> updateSubject(@RequestBody SubjectDTO subjectDTO) {
        Subject subject = subjectService.findOne(subjectDTO.getId());

        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        subject.setName(subjectDTO.getName());
        subject.setSemester(subjectDTO.getSemester());
        subject.setDescription(subjectDTO.getDescription());

        subject = subjectService.save(subject);
        return new ResponseEntity<>(new SubjectDTO(subject), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSubject(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if(subject == null)
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        //Because of foreign keys we need to delete this subject from every student attending on it
        for (Student student : subject.getStudents()){
        	student.getSubjects().remove(subject);
        }
        //Because of foreign keys we need to delete ProfessorRole before we relete subject
        for (ProfessorRole pr : subject.getProfessorRole()){
        	//Because of foreign keys we need to remove ProfessorRole from Professors' list of ProfessorRoles
        	pr.getProfessor().getRoles().remove(pr);
        	//Delete ProfessorRole
        	prService.remove(pr.getId());
        }
        
        subjectService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "/{id}/duties", method = RequestMethod.GET)
    public ResponseEntity<List<DutyDTO>> getDuties(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Set<Duty> duties = subject.getDuties();
        List<DutyDTO> dutiesDTO = new ArrayList<DutyDTO>();
        for (Duty duty : duties) {
            dutiesDTO.add(new DutyDTO(duty));
        }
        return new ResponseEntity<>(dutiesDTO, HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(value = "/{id}/professorRoles", method = RequestMethod.GET)
    public ResponseEntity<List<ProfessorRoleDTO>> getProfessorRoles(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Set<ProfessorRole> professorRoles = subject.getProfessorRole();
        List<ProfessorRoleDTO> professorRolesDTO = new ArrayList<ProfessorRoleDTO>();
        for (ProfessorRole professorRole : professorRoles) {
            professorRolesDTO.add(new ProfessorRoleDTO(professorRole));
        }
        return new ResponseEntity<>(professorRolesDTO, HttpStatus.OK);

    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "/{id}/students", method = RequestMethod.GET)
    public ResponseEntity<List<StudentDTO>> getStudents(@PathVariable Integer id) {
        Subject subject = subjectService.findOne(id);
        if (subject == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Set<Student> students = subject.getStudents();
        List<StudentDTO> studentsDTO = new ArrayList<StudentDTO>();
        for (Student student : students) {
            studentsDTO.add(new StudentDTO(student));
        }
        return new ResponseEntity<>(studentsDTO, HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor"})
    @RequestMapping(value = "/{subjectId}/students", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> saveStudents(@PathVariable Integer subjectId, @RequestBody String studentIds) {
        if (studentIds == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String[] ids = studentIds.split(",");
        Subject subject = subjectService.findOne(subjectId);

        //Taking students currently on subject
        Set<Student> studentsOnCourse = subject.getStudents();

        //Removing this course from students' courses
        for (Student student : studentsOnCourse) {
            student.getSubjects().remove(subject);
            studentService.save(student);
        }

        //Initializing new set of students
        Set<Student> newStudents = new HashSet<Student>();

        for (String id : ids) {
            Student student = studentService.findOne(Integer.parseInt(id));

            //Adding subject to every student that should have this one
            student.getSubjects().add(subject);
            //We added new subject, so it would be nice to save changes
            studentService.save(student);
            //Adding student to set of new students
            newStudents.add(student);
            
            //Trying to find if student and subject are in relationship, if not - make new relationship
            //so we can track if student has passed subject
            StudentSubject studentSubject = studentSubjectService.findByStudentAndSubject(student, subject);
            if(studentSubject == null){
            	studentSubject = studentSubjectService.saveStudentSubject(student, subject);
            }
        }

        //New set of students for this subject
        subject.setStudents(newStudents);

        //Saving subject
        subjectService.save(subject);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
