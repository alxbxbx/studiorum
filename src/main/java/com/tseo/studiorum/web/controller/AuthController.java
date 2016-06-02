package com.tseo.studiorum.web.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tseo.studiorum.entities.User;
import com.tseo.studiorum.service.UserService;
import com.tseo.studiorum.web.dto.UserDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
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
				.setSubject(userDTO.getUserName())
				.claim("roles", user.getUserName())
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "oursecretkey9634h022")
				.compact());
	}

    @SuppressWarnings("unused")
    private static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }
	
}
