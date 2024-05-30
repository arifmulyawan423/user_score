package com.msn.user_score.service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.msn.user_score.entity.User;
import com.msn.user_score.repository.UserRepository;
import com.msn.user_score.request.UserLoginRequest;
import com.msn.user_score.request.UserRegistrationRequest;
import com.msn.user_score.response.UserLoginResponse;
import com.msn.user_score.response.UserRegistrationResponse;

@Service
public class UserScoreService {
	
	@Autowired UserRepository userRepository;
	
	Logger logger = LoggerFactory.getLogger(UserScoreService.class);

	public ResponseEntity<UserLoginResponse> login(UserLoginRequest request) {
		UserLoginResponse response = new UserLoginResponse();
		ResponseEntity<UserLoginResponse> result = ResponseEntity.ok(response);
		
		Optional<User> optUser = userRepository.findByUsername(request.getUsername());
		
		if (optUser.isEmpty()) {
			return new ResponseEntity<UserLoginResponse>(HttpStatus.BAD_REQUEST);
		}
		
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		
		if (!encryptor.checkPassword(request.getPassword(), optUser.get().getEncPassword())) {
			return new ResponseEntity<UserLoginResponse>(HttpStatus.BAD_REQUEST);
		}
		
		String authToken = RandomStringUtils.randomAlphanumeric(16);
		
		logger.info("userRepository.updateUserAuthToken with params user_id = ? and auth_token = ?", optUser.get().getId(), authToken);
		userRepository.updateUserAuthToken(optUser.get().getId(), authToken);
		
		response.setAuthToken(authToken);
		response.setUserId(optUser.get().getId().toString());
		response.setUsername(request.getUsername());
		
		return result;
	}
	
	public ResponseEntity<UserRegistrationResponse> register(UserRegistrationRequest request) {
		UserRegistrationResponse response = new UserRegistrationResponse();
		ResponseEntity<UserRegistrationResponse> result = ResponseEntity.ok(response);
		
		String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,20}$";
		String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@"
							+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		
		if (request.getAge() < 18 
				|| !Pattern.compile(passwordRegex).matcher(request.getPassword()).matches()
				|| !Pattern.compile(emailRegex).matcher(request.getUsername()).matches()) {
			return new ResponseEntity<UserRegistrationResponse>(HttpStatus.BAD_REQUEST);
		}
		
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		
		UUID userId = UUID.randomUUID();
		
		userRepository.regisUser(userId, request.getUsername(), encryptor.encryptPassword(request.getPassword()), request.getAge(), request.getName());
		
		response.setAge(request.getAge());
		response.setId(userId.toString());
		response.setName(request.getName());
		response.setUsername(request.getUsername());
		
		return result;
	}
}
