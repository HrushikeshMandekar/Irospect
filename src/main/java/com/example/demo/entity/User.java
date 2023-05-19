package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="userstable")
@Table(name="userstable")
public class User {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	@Column(name="age")
	private int age;

	@Column(name="roles")
	private String roles; // ROLE_USER, ROLE_IROSPECT_USER, ROLE_ADMIN
	
	@Column(name = "reset_password_token")
    private String resetPasswordToken;

	@OneToOne
	@JoinColumn(name="locationid")
	private Location locationid;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	@JoinColumn(name="blackListedCountriesId")
	private List<Location> blackListedCountriesId;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Location getLocationid() {
		return locationid;
	}

	public void setLocationid(Location locationid) {
		this.locationid = locationid;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public List<Location> getBlackListedCountriesId() {
		return blackListedCountriesId;
	}

	public void setBlackListedCountriesId(List<Location> blackListedCountriesId) {
		this.blackListedCountriesId = blackListedCountriesId;
	}
	
	
	
}
