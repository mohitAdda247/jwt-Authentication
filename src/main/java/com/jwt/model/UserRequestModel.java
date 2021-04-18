package com.jwt.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRequestModel {
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	@Size(min=8, max=50,message = "Password should be in range 8-50 characters")
	private String password;
}
