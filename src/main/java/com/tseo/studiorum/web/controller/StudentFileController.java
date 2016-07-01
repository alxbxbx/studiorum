package com.tseo.studiorum.web.controller;

import com.tseo.studiorum.annotations.Permission;
import com.tseo.studiorum.entities.Document;
import com.tseo.studiorum.service.DocumentService;
import com.tseo.studiorum.service.StudentService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/students/{studentId}/files")
@PropertySource({ "classpath:application.properties" })
public class StudentFileController {

    @Autowired
    Environment env;

    @Autowired
    DocumentService documentService;

    @Autowired
    StudentService studentService;
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "{fileId}", method = RequestMethod.GET, produces="application/pdf")
    public ResponseEntity<byte[]> getAllDocuments(HttpServletResponse response, @PathVariable Integer studentId, @PathVariable Integer fileId) {

        Document document = null;
        HttpHeaders headers = new HttpHeaders();
        byte[] contents = null;
        try {
            document = documentService.findOne(fileId);
            File file = new File(env.getProperty("storage") + document.getPath());
            InputStream is = new FileInputStream(file);
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = document.getName();
            headers.setContentDispositionFormData(filename, filename);

            contents = IOUtils.toByteArray(is);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Document>> getAllDocuments(@PathVariable Integer studentId) {
        List<Document> documents = documentService.findByStudentId(studentId);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "{fileId}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable Integer fileId) {
        Document document = documentService.findOne(fileId);
        File file = new File(env.getProperty("storage") + document.getName());
        file.delete();
        documentService.remove(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Document> getStudents(HttpServletRequest req, @PathVariable Integer studentId,
                                                @RequestParam("file") MultipartFile tempFile) {

        Document document = new Document();
        BufferedOutputStream buffStream = null;

        try {
            String fileName = tempFile.getOriginalFilename();
            fileName = System.currentTimeMillis() + "_" + fileName;
            byte[] bytes = tempFile.getBytes();
            buffStream = new BufferedOutputStream(new FileOutputStream(new File(env.getProperty("storage") + fileName)));
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
