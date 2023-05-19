package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.PostAnalytics;
import com.example.demo.repository.PostAnalyticsRepository;

@Service
public class PostAnalyticsService {

	@Autowired
	private PostAnalyticsRepository analyticsrepo;
	
	public List<PostAnalytics> getAllPostAnalyticsByUserId(int id){
		return analyticsrepo.findPostAnalyticsByUserid(id);
	}
	
	public List<PostAnalytics> getAllPostAnalyticsByPostId(int id){
		return analyticsrepo.findPostAnalyticsByPostsid(id);
	}
	
	public Iterable<PostAnalytics> getAll(){
		return this.analyticsrepo.findAll();
	}
	
	public List<PostAnalytics> addAllPostAnalytics(List<PostAnalytics> postAnalytics){
		return (List<PostAnalytics>)analyticsrepo.saveAll(postAnalytics);
	}
	
	public void savePostAnalytics(PostAnalytics e) {
		this.analyticsrepo.save(e);
	}
	
	public PostAnalytics getPostAnalyticsById(int id) {
		return this.analyticsrepo.findById(id).get();
	}
	
	public void delete(int id) {
		this.analyticsrepo.deleteById(id);
	}
	
}
