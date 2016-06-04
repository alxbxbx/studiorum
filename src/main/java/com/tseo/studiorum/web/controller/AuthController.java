package com.tseo.studiorum.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tseo.studiorum.entities.User;
import com.tseo.studiorum.responses.LoginResponse;
import com.tseo.studiorum.service.UserService;
import com.tseo.studiorum.web.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "roles/{role}", method = RequestMethod.GET)
	public Boolean roles(@PathVariable final String role, final HttpServletRequest request) throws ServletException {
		final Claims claims = (Claims) request.getAttribute("claims");
		return ((List<String>) claims.get("roles")).contains(role);
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public LoginResponse login(@RequestBody final UserDTO userDTO) 
		throws ServletException {
		
		User user = userService.findOneByUsernameAndPassword(
				userDTO.getUserName(), 
				userDTO.getPassword());
		
		if (user == null) {
			throw new ServletException("Authentification failed, user not found in database.");
		}
		
		return new LoginResponse(Jwts.builder()
				.setSubject(user.getUserName())
				.claim("roles", user.getRole())
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "acafilipfakultettehnickihnauka")
				.compact());
	}

}
