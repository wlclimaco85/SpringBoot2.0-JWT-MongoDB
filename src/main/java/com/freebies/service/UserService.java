package com.freebies.service;

import org.springframework.stereotype.Service;

import com.freebies.model.UserInfo;

@Service
public interface UserService {

	UserInfo findByUserEmail(String username);

}
