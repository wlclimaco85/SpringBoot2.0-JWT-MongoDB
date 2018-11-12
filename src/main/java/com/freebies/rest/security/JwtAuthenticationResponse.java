package com.freebies.rest.security;

import java.io.Serializable;


public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    
    private long expiryInMilliSecs;
    
    

    public JwtAuthenticationResponse(String token, long expiryInMilliSecs) {
        this.token = token;
        this.setExpiryInMillliSec(expiryInMilliSecs);
    }

    public String getToken() {
        return this.token;
    }

	public long getExpiryInMillliSec() {
		return expiryInMilliSecs;
	}

	public void setExpiryInMillliSec(long expiryInMillliSec) {
		this.expiryInMilliSecs = expiryInMillliSec;
	}
}
