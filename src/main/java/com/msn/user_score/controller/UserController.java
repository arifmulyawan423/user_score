package com.msn.user_score.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msn.user_score.request.UserLoginRequest;
import com.msn.user_score.request.UserRegistrationRequest;
import com.msn.user_score.response.UserLoginResponse;
import com.msn.user_score.response.UserRegistrationResponse;
import com.msn.user_score.service.UserScoreService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Login and Register", description = "User Login and Register APIs")
@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired UserScoreService userScoreService;

	@PostMapping("login")
	public ResponseEntity login(@RequestBody UserLoginRequest request) {
		return userScoreService.login(request);
	}
	
	@PostMapping("register")
	public ResponseEntity register(@RequestBody UserRegistrationRequest request) {
		return userScoreService.register(request);
	}
}
