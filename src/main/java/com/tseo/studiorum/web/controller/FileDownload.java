package com.tseo.studiorum.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tseo.studiorum.entities.Document;
import com.tseo.studiorum.service.DocumentService;

@Controller
public class FileDownload {
	@Autowired
    Environment env;

	@Autowired
    DocumentService documentService;

	private static final int BUFFER_SIZE = 4096;

	@RequestMapping(value = "/download/{studentId}/{fileId}", method = RequestMethod.GET)
	public void doGet(HttpServletResponse response, @PathVariable Integer studentId, @PathVariable Integer fileId){
		Document document = null;
        InputStream is = null;
        try {
            document = documentService.findOne(fileId);
            File file = new File(env.getProperty("storage") + document.getPath());
            is = new FileInputStream(file);

            String mimeType = "application/octet-stream";
            response.setContentType(mimeType);
            response.setContentLength((int) file.length());
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    file.getName());
            response.setHeader(headerKey, headerValue);

            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            while ((bytesRead = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            is.close();
            outStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
