package com.tseo.studiorum.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tseo.studiorum.annotations.Permission;
import com.tseo.studiorum.entities.Professor;
import com.tseo.studiorum.service.ProfessorService;

@RestController
@RequestMapping(value = "api/professors/{professorId}/pictures")
@PropertySource({ "classpath:application.properties" })
public class ProfessorPictureController {
	
	@Autowired
    Environment env;
	
	@Autowired
    ProfessorService professorService;
	
	@Permission(roles = {"user", "professor", "student"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> addPicture(HttpServletRequest req, @PathVariable Integer professorId,
                                                @RequestParam("file") MultipartFile tempFile) {

    	Professor professor = professorService.findOne(professorId);
        BufferedOutputStream buffStream = null;
        
        //Delete old profile picture
    	try{
    		String[] parts = professor.getPicturePath().split("/");
    		String path = env.getProperty("pictures_storage") + parts[3];
    		File file = new File(path);
            file.delete();	
    	}catch(NullPointerException e){
    		System.out.println("User didn't have previous picture.");
    	}

        try {
            String fileName = tempFile.getOriginalFilename();
            fileName = System.currentTimeMillis() + "_" + fileName;
            byte[] bytes = tempFile.getBytes();
            buffStream = new BufferedOutputStream(new FileOutputStream(new File(env.getProperty("pictures_storage") + fileName)));
            buffStream.write(bytes);
            buffStream.close();
            professor.setPicturePath("/resources/profileImages/" + fileName);
            
            professorService.save(professor);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
