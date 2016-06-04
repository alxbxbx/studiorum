package com.tseo.studiorum.web.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tseo.studiorum.entities.User;
import com.tseo.studiorum.responses.LoginResponse;
import com.tseo.studiorum.service.UserService;
import com.tseo.studiorum.web.dto.UserDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public LoginResponse login(@RequestBody UserDTO userDTO) 
		throws ServletException {
		
		User user = userService.findOneByUsernameAndPassword(
				userDTO.getUserName(), 
				userDTO.getPassword());
		
		if (user == null) {
			throw new ServletException("Authentification failed, user not found in database.");
		}
		
		// Protect password
		user.setPassword("");
		
		return new LoginResponse(Jwts.builder()
				.setSubject(user.getUserName())
				.claim("userdata", user)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "filipbekic01")
				.compact());
	}

}
