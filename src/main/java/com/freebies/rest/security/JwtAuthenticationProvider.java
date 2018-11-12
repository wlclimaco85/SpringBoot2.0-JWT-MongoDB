package com.freebies.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.freebies.common.EncryptionUtility;
import com.freebies.common.Constants.UserStatus;
import com.freebies.model.UserInfo;


@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	
	private static final int MAX_LOGIN_ATTEMPTS = 3;

	@Autowired
	MongoTemplate mongoTemplate;
	


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = String.valueOf(authentication.getPrincipal());
		String password = String.valueOf(authentication.getCredentials());
	    
		UserInfo user = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("email").is(EncryptionUtility.encrypt(username))), UserInfo.class);
						
		if (user != null) {
			
				BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
				
				String rawPassword = password;
				String userPassword = user.getLoginInfo().getPassword();
				
				String encodePassword = EncryptionUtility.convertHexToString(userPassword);
			
				user = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("email").is(EncryptionUtility.encrypt(username))), UserInfo.class);
	
					if (!user.getUserStatus().equals(UserStatus.Active.name())) {
						throw new BadCredentialsException(
								"Access is Blocked. Please contact your administrator");
					}
				
				
				if( UserStatus.Locked.name().equals(user.getLoginInfo().getLoginAccountStatus()))
					 throw new BadCredentialsException("AccountLocked");
				 
				if (passwordEncoder.matches(rawPassword, encodePassword)) {
				
					mongoTemplate.updateFirst(new Query(Criteria.where("email").is( EncryptionUtility.encrypt(username))),
							new Update().set("loginInfo.loginAttempts", 0), UserInfo.class);
					
					return authentication;
	
				} else {
					
					mongoTemplate.updateFirst(new Query(Criteria.where("email").is( EncryptionUtility.encrypt( username ))), new Update().inc("loginInfo.loginAttempts", 1), UserInfo.class);

					 user = mongoTemplate.findOne(new Query().addCriteria(Criteria.where("email").is(EncryptionUtility.encrypt(username))), UserInfo.class);
						
					if (user.getLoginInfo().getLoginAttempts() >= MAX_LOGIN_ATTEMPTS) {

						mongoTemplate.updateFirst(new Query(Criteria.where("email").is( EncryptionUtility.encrypt(username))),
								new Update().set("loginInfo.loginAccountStatus",UserStatus.Locked.name()), UserInfo.class);
						 throw new BadCredentialsException("AccountLocked");
					}
					
					
					throw new BadCredentialsException("CredentialNotMatched");
				}
			
			
		}else{
			 throw new BadCredentialsException("CredentialNotMatched");
		}
		
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
