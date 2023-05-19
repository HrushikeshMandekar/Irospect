package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name="posts")
@Table(name="posts")
public class Posts {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="ageRestricted")
	private boolean ageRestricted;
	
	@Column(name="text")
	private String text;
	
	@OneToOne 
	@JoinColumn(name="postCreatedUser")
	private User postCreatedUser;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinColumn(name="fileId")
	private List<FileDB> fileId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAgeRestricted() {
		return ageRestricted;
	}

	public void setAgeRestricted(boolean ageRestricted) {
		this.ageRestricted = ageRestricted;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<FileDB> getFileId() {
		return fileId;
	}

	public void setFileId(List<FileDB> fileId) {
		this.fileId = fileId;
	}

	public User getPostCreatedUser() {
		return postCreatedUser;
	}

	public void setPostCreatedUser(User postCreatedUser) {
		this.postCreatedUser = postCreatedUser;
	}
	
	
	
	
}
