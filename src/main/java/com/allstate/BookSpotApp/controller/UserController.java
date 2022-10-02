package com.allstate.BookSpotApp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.allstate.BookSpotApp.SendMail.EmailNotification;
import com.allstate.BookSpotApp.model.Credentials;
import com.allstate.BookSpotApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@EnableSwagger2
public class UserController {

    String body="You have been successfully registered to BookSpot App website";
    String subject="Congratulations on successful registration";
    String s1="Book Spot";
	@Autowired
	UserService userService;

	@PostMapping("/user/login")
	public ResponseEntity<?> logIn(@RequestBody Credentials credentials)
	{
		Credentials credentials1 = userService.getUserDetails(credentials.getUsername());
		if (credentials1== null){
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		boolean bcrypt = BCrypt.checkpw(credentials.getPassword(), credentials1.getPassword());
		if(bcrypt==true) {
			String token = Jwts.builder().setId(credentials1.getUsername()).setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "usersecretkey").compact();

			Map<String, String> tokenMap = new HashMap<String, String>();
			tokenMap.put("token", token);
			tokenMap.put("message", "User Successfully logged in");
			ResponseEntity<Map<String, String>> response = new ResponseEntity<Map<String, String>>(tokenMap, HttpStatus.OK);
			return response;
		}
		else {
			return new ResponseEntity<Credentials>( HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/user/create")
	public ResponseEntity<?> registeruser(@RequestBody Credentials cred)
	{
		Credentials credentials1 = userService.getUserDetails(cred.getUsername());
		if (credentials1 != null){
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String userHashedPassword = bCryptPasswordEncoder.encode(cred.getPassword());
		Credentials credentials = new Credentials(cred.getUsername(),userHashedPassword, cred.getName(), cred.getEmail());
				Credentials credentialsResp = userService.newUserRegistration(credentials);
        EmailNotification.sendMail(cred.getEmail(), s1, subject, body);
		return new ResponseEntity<Credentials>(credentialsResp,HttpStatus.OK);
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<Credentials> getUserName(@PathVariable("username") String username){
		ResponseEntity<Credentials> credentialsResponseEntity = null;
		try {
			Credentials credentials = userService.getUserDetails(username);
			credentialsResponseEntity = ResponseEntity.status(HttpStatus.OK).body(credentials);
		} catch(Exception e) {
			credentialsResponseEntity = ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		 return credentialsResponseEntity;
	}

	@GetMapping("/user/fetch/{id}")
	public ResponseEntity<Credentials> getUserById(@PathVariable("id") int id){
		ResponseEntity<Credentials> credentialsResponseEntity = null;
		try {
			Credentials credentials = userService.getUserDetailsById(id);
			credentialsResponseEntity = ResponseEntity.status(HttpStatus.OK).body(credentials);
		} catch(Exception e) {
			credentialsResponseEntity = ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return credentialsResponseEntity;
	}
}
