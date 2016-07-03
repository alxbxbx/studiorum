package com.tseo.studiorum.web.controller;

import com.tseo.studiorum.annotations.Permission;
import com.tseo.studiorum.entities.Document;
import com.tseo.studiorum.service.DocumentService;
import com.tseo.studiorum.service.StudentService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
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
    @RequestMapping(value = "{fileId}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> getAllDocuments(HttpServletResponse response, @PathVariable Integer studentId, @PathVariable Integer fileId) {

        Document document = null;
        HttpHeaders headers = new HttpHeaders();
        InputStream is = null;
        try {
            document = documentService.findOne(fileId);
            File file = new File(env.getProperty("storage") + document.getPath());
            is = new FileInputStream(file);
            
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(is));
    }
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "/{fileId}/download", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable Integer studentId, @PathVariable Integer fileId) {

        Document document = null;
        InputStream is = null;
        try {
            document = documentService.findOne(fileId);
            File file = new File(env.getProperty("storage") + document.getPath());
            is = new FileInputStream(file);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    
	
    /*
    @Permission(roles = {"user", "professor", "student"})
    @RequestMapping(value = "/pictures", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getPicture(HttpServletResponse response, @PathVariable Integer studentId) {

    	Student student = studentService.findOne(studentId);
        HttpHeaders headers = new HttpHeaders();
        InputStream is = null;
        try {
            File file = new File(env.getProperty(student.getPicturePath()));
            is = new FileInputStream(file);
            
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(is));
    }*/
}
