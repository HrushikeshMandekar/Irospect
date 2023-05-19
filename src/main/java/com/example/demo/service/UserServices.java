package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.User;
import com.example.demo.payload.AuthRequest;
import com.example.demo.repository.UserRepository;

@Service
public class UserServices {

	@Autowired
    private UserRepository repository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
  
     
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        repository.save(user);
    }
   

    public User save(AuthRequest authRequest) {
    	User u = new User();
    	u.setUserName(authRequest.getUserName());
    	u.setPassword(passwordEncoder.encode(authRequest.getPassword()));
    	u.setEmail(authRequest.getEmail());
    	u.setAge(authRequest.getAge());
    	u.setRoles(authRequest.getRoles());
    	repository.save(u);
    	return u;
    }
    
    public boolean checkUserName(String username) {
    	return repository.existsByUserName(username);
    }
    
    public boolean checkEmail(String username) {
    	return repository.existsByEmail(username);
    }
    
	 public Iterable<User> getAll()
	 {
		 return this.repository.findAll();
	 }
	 
	 public User getByUserName(String id)
	 {
		 return repository.findByUserName(id);
	 }
	 
	 public User getUserById(int id)
	 {
		 return repository.findById(id).get();
	 }
	 
	 public void updateUser(User v1,int id) 
	 {
		 v1.setId(id);
		 repository.save(v1);
	 }
	 
	public void delete(int id) 
	{
		repository.deleteById(id);  
		
	}
   

}
