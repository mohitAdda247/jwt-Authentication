package com.jwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jwt.model.User;
import com.jwt.model.UserRequestModel;

public interface UserService extends UserDetailsService{
	User save(UserRequestModel user);
	User getUserDetailsByEmail(String email);
}
