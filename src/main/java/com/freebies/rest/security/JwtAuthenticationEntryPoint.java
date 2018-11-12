package com.freebies.rest.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freebies.rest.controller.ErrorInfo;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        
        PrintWriter writer = response.getWriter();
    
        ErrorInfo errorInfo = new ErrorInfo(); 
       
        if(authException.getMessage().equalsIgnoreCase("AccountLocked")) {
        	errorInfo.setErrorCode("408");
        	errorInfo.setErrorDesc(authException.getMessage());
        	errorInfo.setStatus(408);
        	response.setStatus(408);
        
        }  
        else if(authException.getMessage().equalsIgnoreCase("Access is Blocked. Please contact your administrator")) {
        	errorInfo.setErrorCode("409");
        	errorInfo.setErrorDesc(authException.getMessage());
        	errorInfo.setStatus(409);
        	response.setStatus(409);
        
        }else {
        	errorInfo.setErrorCode(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
        	errorInfo.setErrorDesc("Unauthorized");
        	errorInfo.setStatus(401);
        	response.setStatus(401);
        }
        
       
        ObjectMapper mapper = new ObjectMapper(); 
        writer.println(mapper.writeValueAsString(errorInfo));
        
        
    }
}