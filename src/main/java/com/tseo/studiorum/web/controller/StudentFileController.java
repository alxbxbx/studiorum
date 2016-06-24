package com.tseo.studiorum.web.controller;

import com.tseo.studiorum.entities.Document;
import com.tseo.studiorum.service.DocumentService;
import com.tseo.studiorum.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/students/{studentId}/files")
public class StudentFileController {

    @Autowired
    DocumentService documentService;

    @Autowired
    StudentService studentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Document>> getAllDocuments(@PathVariable Integer studentId) {
        List<Document> documents = documentService.findAllByStudentId(studentId);
        String x = "123";
        String y = "456";
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Document> getStudents(HttpServletRequest req, @PathVariable Integer studentId,
                                                @RequestParam("file") MultipartFile tempFile) {

        String storagePath = "C:\\Users\\Filip\\Desktop\\";
        Document document = new Document();
        BufferedOutputStream buffStream = null;

        try {
            String fileName = tempFile.getOriginalFilename();
            byte[] bytes = tempFile.getBytes();
            buffStream = new BufferedOutputStream(new FileOutputStream(new File(storagePath + fileName)));
            buffStream.write(bytes);
            buffStream.close();
            document.setName(fileName);
            document.setPath(fileName);
            document.setStudent(studentService.findOne(studentId));
            documentService.save(document);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(document, HttpStatus.OK);
    }

}
