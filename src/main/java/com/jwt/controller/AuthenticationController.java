package com.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.model.LoginRequestModel;
import com.jwt.model.LoginResponseModel;
import com.jwt.model.User;
import com.jwt.model.UserRequestModel;
import com.jwt.security.JwtTokenUtil;
import com.jwt.service.UserService;

@RestController
public class AuthenticationController {

	@Autowired
	UserService userService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	AuthenticationManager authenticationManager;

	User user = new User();

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserRequestModel user) throws Exception {

		userService.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).body("User has been created");
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseModel> createToken(@RequestBody LoginRequestModel authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new LoginResponseModel(token));
	}
	
	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
