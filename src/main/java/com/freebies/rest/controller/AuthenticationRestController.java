package com.freebies.rest.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.freebies.common.EncryptionUtility;
import com.freebies.common.Constants.LoginType;
import com.freebies.model.UserInfo;
import com.freebies.rest.security.JwtAuthenticationRequest;
import com.freebies.rest.security.JwtAuthenticationResponse;
import com.freebies.rest.security.JwtTokenUtil;
import com.freebies.service.UserService;

@RestController
public class AuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private UserService userService;
  
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtAuthenticationRequest authenticationRequest)  throws Exception {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        UserInfo user= userService.findByUserEmail(EncryptionUtility.encrypt(authenticationRequest.getUsername()));
   
        final String token = jwtTokenUtil.generateToken(user);        
                 
        long expectedExpTime = jwtTokenUtil.getExpectedTimeForExpiration(token);
      
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token,expectedExpTime));
    }

	@RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) throws ParseException {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserInfo user= userService.findByUserEmail(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLoginInfo().getLastPwdDate())) {
        	
            String refreshedToken = jwtTokenUtil.refreshToken(token, LoginType.System.name());
            long expectedExpTime = jwtTokenUtil.getExpectedTimeForExpiration(token);
            
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken,expectedExpTime));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
	
	 
	 
	
}
