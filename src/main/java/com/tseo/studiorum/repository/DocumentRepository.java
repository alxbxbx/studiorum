package com.tseo.studiorum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findAllByStudentId(Integer studentId);
}
