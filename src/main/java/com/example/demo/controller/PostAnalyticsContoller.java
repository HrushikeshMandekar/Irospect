package com.example.demo.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.example.demo.entity.PostAnalytics;
import com.example.demo.entity.Posts;
import com.example.demo.entity.User;
import com.example.demo.service.LocationServices;
import com.example.demo.service.PostAnalyticsService;
import com.example.demo.service.PostsServices;
import com.example.demo.service.UserServices;
import com.example.demo.common.PostViewAllRecords;

@RestController
@RequestMapping("/home")
public class PostAnalyticsContoller {

	@Autowired
	private PostAnalyticsService postAnalyticsService;

	@Autowired
	private PostsServices postService;
	
	@Autowired
	private UserServices userService;


	@GetMapping("/allPostAnalytics")
	private Iterable<PostAnalytics> getPostAnalytics(){
		return postAnalyticsService.getAll();
	}

	@GetMapping("/getAllPostAnalyticsByUserId/{intid}")
	private List<PostAnalytics> getAllPostAnalyticsByUserId(@PathVariable int intid) {
		return postAnalyticsService.getAllPostAnalyticsByUserId(intid);
	}

	@GetMapping("/getAllPostAnalyticsByPostId/{intid}")
	private List<PostAnalytics> getAllPostAnalyticsByPostId(@PathVariable int intid) {
		return postAnalyticsService.getAllPostAnalyticsByPostId(intid);
	}

	@GetMapping("/calculateLikeDislikeLovedForPost/{intid}")
	private List calculateLikeDislikeLovedForPost(@PathVariable int intid) {
		String result = "";

		List<PostAnalytics> postLikedUsers = postAnalyticsService.getAllPostAnalyticsByPostId(intid).stream().
				filter(post -> post.isUserlike()).collect(Collectors.toList());
		List<PostAnalytics> postDisLikedUsers = postAnalyticsService.getAllPostAnalyticsByPostId(intid).stream().
				filter(post -> post.isUsersdislike()).collect(Collectors.toList());
		List<PostAnalytics> postLovedUsers = postAnalyticsService.getAllPostAnalyticsByPostId(intid).stream().
				filter(post -> post.isUsersloved()).collect(Collectors.toList());

		int total = postLikedUsers.size() + postDisLikedUsers.size() + postLovedUsers.size();
		result += "Post Total Response:" + total +"( Likes = " + postLikedUsers.size() + " , Dislikes = " + postDisLikedUsers.size() +
				", Loved = "  + postLovedUsers.size() +")";
		Posts p = postService.getPostsById(intid);
		PostView postview = new PostView(result, p);
		return postview.display();
	}
	

	@GetMapping("/calculateLocationWiseAgeWiseReachCount/{intid}")
	private String calculateLocationWiseAgeWiseReachCount(@PathVariable int intid) {
		String result = "";
		List<PostAnalytics> postLikedUsers = postAnalyticsService.getAllPostAnalyticsByPostId(intid).stream().
				filter(post -> post.isUserlike()).collect(Collectors.toList());
		List<PostAnalytics> postDisLikedUsers = postAnalyticsService.getAllPostAnalyticsByPostId(intid).stream().
				filter(post -> post.isUsersdislike()).collect(Collectors.toList());
		List<PostAnalytics> postLovedUsers = postAnalyticsService.getAllPostAnalyticsByPostId(intid).stream().
				filter(post -> post.isUsersloved()).collect(Collectors.toList());

		
		List<String> LikedUserslocations = postLikedUsers.stream().
				map(pas -> pas.getUserid().getLocationid().getState()).collect(Collectors.toList());
		List<String> DislikedUserslocations = postDisLikedUsers.stream().
				map(pas -> pas.getUserid().getLocationid().getState()).collect(Collectors.toList());
		List<String> LovedUserslocations = postLovedUsers.stream().
				map(pas -> pas.getUserid().getLocationid().getState()).collect(Collectors.toList());

		
		String stateWithMaxLikes = "";
		int temp = 0;
		for(String state: LikedUserslocations) {			
			int count  = Collections.frequency(LikedUserslocations, state);
			if(count > temp) {
				temp = count;
				stateWithMaxLikes = state;
			}
		}

		String stateWithMaxDisLikes = "";
		temp = 0;
		for(String state: DislikedUserslocations) {			
			int count  = Collections.frequency(DislikedUserslocations, state);
			if(count > temp) {
				temp = count;
				stateWithMaxDisLikes = state;
			}
		}

		String stateWithMaxLoved = "";
		temp = 0;
		for(String state: LovedUserslocations) {			
			int count  = Collections.frequency(LovedUserslocations, state);
			if(count > temp) {
				temp = count;
				stateWithMaxLoved = state;
			}
		}
		
		stateWithMaxLikes = !stateWithMaxLikes.equals("")?stateWithMaxLikes:"None";
		stateWithMaxDisLikes = !stateWithMaxDisLikes.equals("")?stateWithMaxDisLikes:"None";
		stateWithMaxLoved = !stateWithMaxLoved.equals("")?stateWithMaxLoved:"None";
		
		
		result += postService.getPostsById(intid).getPostCreatedUser().getLocationid().getCountry() + 
				" User post is most liked by " + stateWithMaxLikes + ", Disliked by " + stateWithMaxDisLikes + ", Loved by " + stateWithMaxLoved;

		List<Integer> likedUsersAge = postLikedUsers.stream().
				map(pas -> pas.getUserid().getAge()).collect(Collectors.toList());
		List<Integer> DislikedUsersAge = postDisLikedUsers.stream().
				map(pas -> pas.getUserid().getAge()).collect(Collectors.toList());
		List<Integer> LovedUsersAge = postLovedUsers.stream().
				map(pas -> pas.getUserid().getAge()).collect(Collectors.toList());

		
		temp = 0;
		int maxLikedUserAge= 0;
		for(Integer age: likedUsersAge) {			
			int count  = Collections.frequency(likedUsersAge, age);
			System.out.println("xount " + count);
			if(count > temp) {
				temp = count;
				maxLikedUserAge = age;
			}
		}

		int min = maxLikedUserAge - 5;
			
		if(min < 8) {
			min = 8;
		}

		if(maxLikedUserAge == 0) {
			result += "\nThis Post have no Likes by any of the age group. ";
		} else {
			result += "\nMostly  Age group "+ min +" to "+ (maxLikedUserAge + 5) +
					" Liked this post. The age of max number of people who Like the post is " + maxLikedUserAge;
		}
		
		temp = 0;
		int maxDiLikedUserAge = 0;
		for(Integer age: DislikedUsersAge) {			
			int count  = Collections.frequency(DislikedUsersAge, age);
			System.out.println("ddddllllxount " + count);
			if(count > temp) {
				temp = count;
				maxDiLikedUserAge = age;
			}
		}

		min = maxDiLikedUserAge - 5;

		if(min < 8) {
			min = 8;
		}
		
		if(maxDiLikedUserAge == 0) {
			result += "\nThis Post have no Dislikes by any of the age group. ";
		} else {
			result += "\nMostly  Age group "+ min +" to "+ (maxDiLikedUserAge + 5) +
					" Disliked this post. The age of max number of people who Disliked the post is " + maxDiLikedUserAge;
		}
		
		temp = 0;
		int maxLovedUserAge = 0;
		for(Integer age: LovedUsersAge) {			
			int count  = Collections.frequency(LovedUsersAge, age);
			if(count > temp) {
				temp = count;
				maxLovedUserAge = age;
			}
		}

		min = maxLovedUserAge - 5;

		if(min < 8) {
			min = 8;
		}
		
		if(maxLovedUserAge == 0) {
			result += "\nThis Post have not Loved by any of the age group. ";
		} else {
			result += "\nMostly  Age group "+ min +" to "+ (maxLovedUserAge + 5) +
					" Loved this post. The age of max number of people who Loved the post is " + maxLovedUserAge;
		}
		
		return result;
	}
	

	@GetMapping("/calculateNoOfReactedToPostsForSingleUser/{id}")
	private List calculateNoOfReactedToPostsForSingleUser(@PathVariable int id) {	
		
		List<PostAnalytics> AllReactedPostsList = postAnalyticsService.getAllPostAnalyticsByUserId(id);
		
		List<PostAnalytics> userLikedPosts = AllReactedPostsList.stream().
				filter(user -> user.isUserlike()).collect(Collectors.toList());
		
		List<PostAnalytics> userDislikedPosts = AllReactedPostsList.stream().
				filter(user -> user.isUsersdislike()).collect(Collectors.toList());
		
		List<PostAnalytics> userLovedPosts = AllReactedPostsList.stream().
				filter(user -> user.isUsersloved()).collect(Collectors.toList());
		
		
		int total = userLikedPosts.size() + userDislikedPosts.size() + userLovedPosts.size();
		
		String result = "Post Total Response:" + total +"( Likes = " + userLikedPosts.size() + 
						" , Dislikes = " + userDislikedPosts.size() +
						", Loved = "  + userLovedPosts.size() +")";
		
		PostViewAllRecords postview = new PostViewAllRecords(result, AllReactedPostsList);
		return postview.display();
	}
	
	@GetMapping("/checkUserHasGot1000LikesToAnyOfHisPost/{id}")
	private List<Posts> checkUserHasGot1000LikesToAnyOfHisPost(@PathVariable int id) {	
		
		System.out.println("postService.getAll() " + postService.getAll());
		
		List<Posts> postsThatUserHaveCreatedList = postService.getAll().stream().
				filter(post-> post.getPostCreatedUser().getId() == id).collect(Collectors.toList());
		
		System.out.println("postsThatUserHaveCreatedList " + postsThatUserHaveCreatedList);
		List<Posts> postsList = postsThatUserHaveCreatedList.stream().filter(post -> {
			
			List<PostAnalytics> postLikedUsers = postAnalyticsService.getAllPostAnalyticsByPostId(post.getId()).stream().
					filter(p -> p.isUserlike()).collect(Collectors.toList());
			
			if(postLikedUsers.size() > 1) {
				return true;
			}
			
			return false;
			
		}).collect(Collectors.toList());
		
		return postsList;
	}

	@GetMapping("/getPostAnalyticsById/{id}")
	private PostAnalytics getPostAnalyticsById(@PathVariable int id) {	
		return postAnalyticsService.getPostAnalyticsById(id);
	}


	@PostMapping(value="/bulkPostAnalyticsSave")
	private String addAllLPostAnalytics(@RequestBody List<PostAnalytics> p) {
		postAnalyticsService.addAllPostAnalytics(p);
		return "Your response is saved successfully";
	}

	@PostMapping(value="/savvePostAnalytics") 
	private String addPostAnalytics(@RequestBody PostAnalytics p, Principal principal){

		if((p.isUserlike() == true && p.isUsersdislike() == true )|| (p.isUserlike() == true && p.isUsersloved() == true ) ||
				(p.isUsersloved() == true && p.isUsersdislike() == true )) {
			return "You can either Like or Dislike or Loved the Post!";
		}
		
		// A user cannot like/dislike/love his own post
		String LoggedInUserName = principal.getName();
		User loggedUser = userService.getByUserName(LoggedInUserName);
		
		Posts posts = postService.getPostsById(p.getPostsid().getId());
		
		if(loggedUser.getId() == posts.getPostCreatedUser().getId()) {
			return "You can't  like/dislike/love your own post";
		}


		postAnalyticsService.savePostAnalytics(p);
		return "Your response is saved successfully";
	}


	@PutMapping("/updatePostAnalytics/{id}")
	private PostAnalytics updatePostAnalytics(@RequestBody PostAnalytics e, @PathVariable int id) {
		e.setAnalyticsid(id);
		postAnalyticsService.savePostAnalytics(e);
		return e;
	}

	@DeleteMapping("/deletePostAnalytics/{id}")
	private String deletePostAnalytics(@PathVariable int id) {
		postAnalyticsService.delete(id);
		return "Your response is removed";
	}
}
