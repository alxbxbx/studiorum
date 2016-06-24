package com.tseo.studiorum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tseo.studiorum.entities.Document;
import com.tseo.studiorum.repository.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    public Document findOne(Integer id) {
        return documentRepository.findOne(id);
    }

    public List<Document> findByStudentId(Integer studentId) {
        List<Document> documents = documentRepository.findByStudentId(studentId);
        return documents;
    }

    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    public Document save(Document document) {
        return documentRepository.save(document);
    }

    public void remove(Integer id) {
        documentRepository.delete(id);
    }
    
}
