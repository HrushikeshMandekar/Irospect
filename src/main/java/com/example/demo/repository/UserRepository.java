package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserName(String username);
	public User findByResetPasswordToken(String token);
	
	public boolean existsByUserName(String email); // If email already present then it will return true or else false
	public boolean existsByEmail(String email); 
	
}
