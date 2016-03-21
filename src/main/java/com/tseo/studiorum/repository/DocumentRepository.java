package com.tseo.studiorum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tseo.studiorum.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer>{

}
