package com.example.demo.common;

import com.example.demo.entity.Posts;
import java.util.ArrayList;
import java.util.List;

public class PostView {

	private String responsed;
	private Posts post;
	
	public List data = new ArrayList();
	
	public PostView(String responsed, Posts post) {
		this.responsed = responsed;
		this.post = post;
		data.add(post);
		data.add(responsed);
	}
	
	public List display() {
		return data;
	}
	
	
}
