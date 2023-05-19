package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Posts;
import com.example.demo.repository.PostsRepository;

@Service
public class PostsServices {

	@Autowired
	private PostsRepository postsrepo;
	
	public List<Posts> getAll(){
		return this.postsrepo.findAll();
	}
	
	public List<Posts> addAllPosts(List<Posts> posts){
		return (List<Posts>)postsrepo.saveAll(posts);
	}
	
	public void savePosts(Posts e) {
		this.postsrepo.save(e);
	}
	
	public Posts getPostsById(int id) {
		return this.postsrepo.findById(id).get();
	}
	
	public void delete(int id) {
		this.postsrepo.deleteById(id);
	}
}
