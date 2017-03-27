package com.tseo.studiorum.configurations;

import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tseo.studiorum.annotations.Permission;

import io.jsonwebtoken.Claims;

public class CustomMethodInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(Permission.class)) {
            Claims claims = (Claims) request.getAttribute("claims");
            HashMap<String, String> userData = (HashMap<String, String>) claims.get("userdata");
            String userRole = userData.get("role");
            String[] roles = method.getAnnotation(Permission.class).roles();
            boolean denyAccess = true;
            for (String role : roles) {
                if (role.toLowerCase().equals(userRole.toLowerCase())) {
                    denyAccess = false;
                }
            }
            if (denyAccess) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
