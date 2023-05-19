package com.example.demo.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.AuthRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.common.UserTypes;
import com.example.demo.entity.User;
import com.example.demo.service.UserServices;
import com.example.demo.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserAuthenticationController {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserServices userServices;

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/signin")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtUtil.generateToken(authRequest.getUserName());
		} else {
			return "User not valid";
		}
	}

	// saving users to db
	@PostMapping("/signup")
	public String joinGroup(@RequestBody AuthRequest user) {

		boolean userNameAlreadyExists = userServices.checkUserName(user.getUserName());

		if(userNameAlreadyExists) {
			return "User name already exist!";
		} 

		boolean emailAlreadyExists = userServices.checkEmail(user.getEmail());

		if(emailAlreadyExists) {
			return "Email name already exist!";
		} 

		if(user.getRoles() == null) {
			user.setRoles("ROLE_USER");
		}
		
		if(!UserTypes.getUserTypes().contains(user.getRoles())) {
			return "The role " + user.getRoles() + " is not available!";
		}

		ResponseEntity.ok(userServices.save(user));
		return "Hi " + user.getUserName() + " welcome! You have Sign up successfully!";
	}


	@GetMapping("/getUser")
	public Iterable<User> getUser()
	{
		return userServices.getAll();
	}

	@GetMapping("/userbyId/{userid}")
	private User getById(@PathVariable int userid)
	{
		return userServices.getUserById(userid);
	}


	@PutMapping("/updateUser/{userid}")
	private User updateUser(@RequestBody User u,@PathVariable int userid)
	{
		String encryptedPwd = passwordEncoder.encode(u.getPassword());
		u.setPassword(encryptedPwd);
		userServices.updateUser(u, userid);
		return u;
	}


	@DeleteMapping("/deleteUser/{userid}")
	private String deleteUser(@PathVariable int userid)
	{
		userServices.delete(userid);
		return "Deleted User successfully";
	}


}
