package com.example.demo.common;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.PostAnalytics;
import com.example.demo.entity.Posts;

public class PostViewAllRecords {

	private String responsed;
	private List listOfPosts;
	
	public List data = new ArrayList();
	
	public PostViewAllRecords(String responsed, List<PostAnalytics> post) {
		this.responsed = responsed;
		this.listOfPosts = post;
		data.add(responsed);
		data.add(listOfPosts);
		
	}
	
	public List display() {
		return data;
	}
	
	
}
