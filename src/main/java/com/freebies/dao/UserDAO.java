package com.freebies.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.freebies.model.UserInfo;


@Repository
public interface UserDAO extends MongoRepository<UserInfo, String>{
	
	UserInfo findByEmail(String email);
	
	UserInfo findByUserId(String userId);

}
