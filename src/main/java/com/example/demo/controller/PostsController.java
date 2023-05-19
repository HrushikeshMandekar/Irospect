package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.PostView;
import com.example.demo.entity.Location;
import com.example.demo.entity.Posts;
import com.example.demo.entity.User;
import com.example.demo.service.PostsServices;
import com.example.demo.service.UserServices;
import java.util.ArrayList;

@RestController
@RequestMapping("/posts")
public class PostsController {

	@Autowired
	private PostsServices postSerivice;

	@Autowired
	private UserServices userService;

	@GetMapping("/allPosts")
	private Iterable<Posts> getPosts(Principal principal){

		String LoggedInUserName = principal.getName();
		User loggedUser = userService.getByUserName(LoggedInUserName);

		// Filtering the Posts By Location
		List<Posts> filteredPostsByLocation = postSerivice.getAll().stream().filter(post -> {

			String createdPostUserName = post.getPostCreatedUser().getUserName();
			User createdUserObject = userService.getByUserName(createdPostUserName);

			if(!createdUserObject.getBlackListedCountriesId().isEmpty()) {
				List<String> blackListCountries = post.getPostCreatedUser().getBlackListedCountriesId().stream().
						map(blc -> blc.getCountry()).collect(Collectors.toList());

				if(blackListCountries.contains(loggedUser.getLocationid().getCountry())) {
					return false;  // If login user country is in black list of other user then his post won't be showed
				}
			}
			return true;

		}).collect(Collectors.toList());


		// Filtering the Posts By Age
		if(loggedUser.getAge() < 18) {
			List<Posts> filteredPostsByAge = filteredPostsByLocation.stream().filter(post -> !post.isAgeRestricted()).collect(Collectors.toList());
			return filteredPostsByAge;
		}
		
		return filteredPostsByLocation;
	}

	@GetMapping("/getPostsById/{id}")
	private List getPostsById(@PathVariable int id, Principal principal) {	
		Posts post = postSerivice.getPostsById(id);
		String createdPostUserName = post.getPostCreatedUser().getUserName();
		User createdUserObject = userService.getByUserName(createdPostUserName);
		if(!createdUserObject.getBlackListedCountriesId().isEmpty()) {
			List<String> blackListCountries = post.getPostCreatedUser().getBlackListedCountriesId().stream().
					map(blc -> blc.getCountry()).collect(Collectors.toList());

			String LoggedInUserName = principal.getName();

			User loggedUser = userService.getByUserName(LoggedInUserName);

			if(blackListCountries.contains(loggedUser.getLocationid().getCountry())) {
				PostView postview = new PostView("Your Location is BLOCKED for this user post!", null);	
				return postview.display();  
			}
		}

		PostView postview = new PostView("Your required Post is Here!", post);	
		return postview.display();
	}


	@PostMapping(value="/bulkPostsSave")
	private String addAllPosts(@RequestBody List<Posts> location) {
		postSerivice.addAllPosts(location);
		return "All Posts added successfully";
	}


	@PostMapping(value="/savvePosts") 
	private String addPosts(@RequestBody Posts p, Principal principal){

		String LoggedInUserName = principal.getName();

		User loggedUser = userService.getByUserName(LoggedInUserName);

		if(loggedUser.getRoles().equals("ROLE_USER")) {
			return "Your role is ROLE_USER, you can't create post!";
		}

		User postCreatedUser = p.getPostCreatedUser();

		if(loggedUser.getId() != postCreatedUser.getId()) {
			return "You can create post with your own id only!";
		}

		postSerivice.savePosts(p);
		return "Posts saved";
	}


	@PutMapping("/updatePosts/{id}")
	private Posts updatePosts(@RequestBody Posts e, @PathVariable int id) {
		e.setId(id);
		postSerivice.savePosts(e);
		return e;
	}


	@DeleteMapping("/deletePosts/{id}")
	private String deletePosts(@PathVariable int id) {
		postSerivice.delete(id);
		return "Posts Deleted";
	}
}
