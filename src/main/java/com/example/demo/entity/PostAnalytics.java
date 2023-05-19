package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="analyticstable")
@Table(name="analyticstable")
public class PostAnalytics {
	
	@Id
	@Column(name="analyticsid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int analyticsid;
	
	
	@Column(name="userlike")
	private boolean userlike;
	
	@Column(name="usersdislike")
	private boolean usersdislike;
	
	@Column(name="usersloved")
	private boolean usersloved;
	
	@OneToOne
	@JoinColumn(name="usersid")
	private User userid;
	
	@OneToOne
	@JoinColumn(name="postsid")
	private Posts postsid;

	public int getAnalyticsid() {
		return analyticsid;
	}

	public void setAnalyticsid(int analyticsid) {
		this.analyticsid = analyticsid;
	}

	public boolean isUserlike() {
		return userlike;
	}

	public void setUserlike(boolean userlike) {
		this.userlike = userlike;
	}

	public boolean isUsersdislike() {
		return usersdislike;
	}

	public void setUsersdislike(boolean usersdislike) {
		this.usersdislike = usersdislike;
	}

	public boolean isUsersloved() {
		return usersloved;
	}

	public void setUsersloved(boolean usersloved) {
		this.usersloved = usersloved;
	}

	public User getUserid() {
		return userid;
	}

	public void setUserid(User userid) {
		this.userid = userid;
	}

	public Posts getPostsid() {
		return postsid;
	}

	public void setPostsid(Posts postsid) {
		this.postsid = postsid;
	}

	

}
