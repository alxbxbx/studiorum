package com.tseo.studiorum.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		String token = request.getHeader("Authorization");
		
		if (token == null) {
			throw new ServletException("Missing or invalid Authorization header.");
		}
		
		try {
			Claims claims = Jwts.parser().setSigningKey("filipbekic01")
					.parseClaimsJws(token).getBody();
			request.setAttribute("claims", claims);
		} catch (SignatureException ex) {
			throw new ServletException("Invalid token.");
		}
		
		chain.doFilter(req, res);
	}

}
