 package com.example.demo.controller;

import javax.security.auth.login.CredentialNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.payload.AuthRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServices;

import net.bytebuddy.utility.RandomString;

@RestController
public class ForgotPasswordController {

	@Autowired
	private UserServices userService;
	
	@Autowired
    private UserRepository repository;

	@GetMapping("/forgot_password/{username}")
	public String processForgotPassword(@PathVariable String username) {

		String token = RandomString.make(30);
		
		User u = userService.getByUserName(username);
		u.setResetPasswordToken(token);
		repository.save(u);
		return token;

	}

	@PostMapping("/reset_password/{username}/{token}")
	public String processResetPassword(@PathVariable  String username, @PathVariable  String token, @RequestBody AuthRequest userprovided) {

		User user = userService.getByUserName(username);
		
		String password = userprovided.getPassword();
		
		if (user.getResetPasswordToken().equals(token)) {
			userService.updatePassword(user, password);
			return "Password Updated Successfully!";
		} else {           
			return "Enable to update your password!.";
		}
	}
}
