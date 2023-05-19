package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.PostAnalytics;
import com.example.demo.entity.User;

@Repository
public interface PostAnalyticsRepository extends JpaRepository<PostAnalytics, Integer>{

	@Query("select w from analyticstable w where w.userid.id=:usersid")
	public List<PostAnalytics> findPostAnalyticsByUserid(int usersid);
	
	@Query("select w from analyticstable w where w.postsid.id=:postidd")
	public List<PostAnalytics> findPostAnalyticsByPostsid(int postidd);
	
	
}
