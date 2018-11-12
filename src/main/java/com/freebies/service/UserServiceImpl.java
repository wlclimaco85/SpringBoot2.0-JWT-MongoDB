package com.freebies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freebies.dao.UserDAO;
import com.freebies.model.UserInfo;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDAO userDAO;
	

	@Override
	public UserInfo findByUserEmail(String email) {
		
		return userDAO.findByEmail(email);
		
	}

}
