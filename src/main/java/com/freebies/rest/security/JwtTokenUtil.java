package com.freebies.rest.security;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.freebies.common.Constants.LoginType;
import com.freebies.model.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_ID = "jti";
    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.sysUserExpiration}")
    private Long sysUserExpiration;

    public Integer getTenantIdFromToken(String token) {
        Integer tenantId;
        try {
            final Claims claims = getClaimsFromToken(token);
            tenantId = Integer.parseInt(claims.getId());
        } catch (Exception e) {
        	tenantId = null;
        }
        return tenantId;
    }

    
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(String loginType) {
    	
    	if(loginType.equalsIgnoreCase(LoginType.System.name())){
    		return new Date(System.currentTimeMillis() + sysUserExpiration * 1000);
    	} else {
    		return new Date(System.currentTimeMillis() + expiration * 1000);
    	}
        
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, String lastPasswordReset) throws ParseException {
    	
    	
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    	Date lastPwdDate =formatter.parse(lastPasswordReset); 
    	
        return (lastPasswordReset != null && created.before(lastPwdDate));
    }

    private String generateAudience() {
        String audience = AUDIENCE_UNKNOWN;
        	
        audience = AUDIENCE_WEB;
        
        return audience;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserInfo userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getEmail());
        claims.put(CLAIM_KEY_ID, userDetails.getEmail());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims, LoginType.System.name());
    }

    String generateToken(Map<String, Object> claims,String loginType) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate(loginType))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, String lastPasswordReset) throws ParseException {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token,String loginType) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims, loginType);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    

	public boolean validateToken(String token, UserInfo user) throws ParseException {
		
		 final String username = getUsernameFromToken(token);
	       final Date created = getCreatedDateFromToken(token);
	       
	       return (
	                username.equals(user.getEmail())
	                        && !isTokenExpired(token)
	                        && !isCreatedBeforeLastPasswordReset(created, user.getLoginInfo().getLastPwdDate()));
	}
	
	 public long getExpectedTimeForExpiration(String token) {
		 
		 	Date expirationDate= getExpirationDateFromToken(token);
		  
	        long actualExpTime = expirationDate.getTime();
	        
	        long expectedExpTime = actualExpTime - 300000; // 5 minute less from actual expiration time
	        
	        return expectedExpTime;
	  }
}