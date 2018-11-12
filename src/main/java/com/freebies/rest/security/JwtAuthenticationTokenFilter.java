package com.freebies.rest.security;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.freebies.model.UserInfo;
import com.freebies.service.UserService;




public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
       
	    	
	    	String authToken = request.getHeader(this.tokenHeader);
	       
	        String username = jwtTokenUtil.getUsernameFromToken(authToken);
	
	        logger.debug("checking authentication for user " + username);
	
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	
	            // It is not compelling necessary to load the use details from the database. You could also store the information
	            // in the token and read it from it. It's up to you ;)
	            UserInfo user= userService.findByUserEmail(username);
	
	            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
	            // the database compellingly. Again it's up to you ;)
	            try {
					if (jwtTokenUtil.validateToken(authToken, user)) {
						List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					   authorities.add(new SimpleGrantedAuthority(user.getRoleInfo().getRoleId()));
					    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), null, authorities);
					    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					    logger.debug("authenticated user " + username + ", setting security context");
					    SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }

        chain.doFilter(request, response);
    }
}