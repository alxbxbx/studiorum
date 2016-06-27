package com.tseo.studiorum.web.controller;

import com.tseo.studiorum.entities.Document;
import com.tseo.studiorum.service.DocumentService;
import com.tseo.studiorum.service.StudentService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/students/{studentId}/files")
public class StudentFileController {

    private String storage = "C:\\Users\\Alxbxbx\\Desktop\\";

    @Autowired
    DocumentService documentService;

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "{fileId}", method = RequestMethod.GET)
    public ResponseEntity<Document> getAllDocuments(HttpServletResponse response, @PathVariable Integer studentId, @PathVariable Integer fileId) {

        Document document = null;
        try {
            document = documentService.findOne(fileId);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "File Transfer");
            response.setHeader("Content-Disposition", "attachment; filename=" + document.getName());
            File file = new File(this.storage + document.getPath());
            InputStream is = new FileInputStream(file);
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Document>> getAllDocuments(@PathVariable Integer studentId) {
        List<Document> documents = documentService.findByStudentId(studentId);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @RequestMapping(value = "{fileId}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable Integer fileId) {
        Document document = documentService.findOne(fileId);
        File file = new File(this.storage + document.getName());
        file.delete();
        documentService.remove(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Document> getStudents(HttpServletRequest req, @PathVariable Integer studentId,
                                                @RequestParam("file") MultipartFile tempFile) {

        Document document = new Document();
        BufferedOutputStream buffStream = null;

        try {
            String fileName = tempFile.getOriginalFilename();
            fileName = System.currentTimeMillis() + "_" + fileName;
            byte[] bytes = tempFile.getBytes();
            buffStream = new BufferedOutputStream(new FileOutputStream(new File(this.storage + fileName)));
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
