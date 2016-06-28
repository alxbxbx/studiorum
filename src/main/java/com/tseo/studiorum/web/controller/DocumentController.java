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
import com.tseo.studiorum.entities.Document;
import com.tseo.studiorum.entities.Student;
import com.tseo.studiorum.service.DocumentService;
import com.tseo.studiorum.service.StudentService;
import com.tseo.studiorum.web.dto.DocumentDTO;

@RestController
@RequestMapping(value = "api/documents")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @Autowired
    StudentService studentService;
    
    @Permission(roles = {"user", "student"})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DocumentDTO>> getDocuments() {
        List<Document> documents = documentService.findAll();
        List<DocumentDTO> documentsDTO = new ArrayList<DocumentDTO>();
        for (Document document : documents) {
            documentsDTO.add(new DocumentDTO(document));
        }
        return new ResponseEntity<>(documentsDTO, HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "student"})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable Integer id) {
        Document document = documentService.findOne(id);
        if (document == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new DocumentDTO(document), HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "student"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DocumentDTO> saveDocument(@RequestBody DocumentDTO documentDTO) {
        if (documentDTO.getStudentDTO() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Student student = studentService.findOne(documentDTO.getStudentDTO().getId());
        if (student == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Document document = new Document();
        document.setName(documentDTO.getName());

        //automatski treba da stavis path
        document.setPath(documentDTO.getPath());
        document.setStudent(student);

        document = documentService.save(document);
        return new ResponseEntity<>(new DocumentDTO(document), HttpStatus.OK);

    }
    
    @Permission(roles = {"user", "student"})
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<DocumentDTO> updateDocument(@RequestBody DocumentDTO documentDTO) {
        Document document = documentService.findOne(documentDTO.getId());
        if (document == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        document.setName(documentDTO.getName());
        document = documentService.save(document);
        return new ResponseEntity<>(new DocumentDTO(document), HttpStatus.OK);

    }
    
    @Permission(roles = {"user", "student"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteDocument(@PathVariable Integer id) {
        Document document = documentService.findOne(id);
        if (document == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            documentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
