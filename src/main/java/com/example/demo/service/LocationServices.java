package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Location;
import com.example.demo.repository.LocationRepository;

@Service
public class LocationServices {

	@Autowired
	private LocationRepository locationrepo;
	
	public Iterable<Location> getAll(){
		return this.locationrepo.findAll();
	}
	
	public List<Location> addAllLocation(List<Location> location){
		return (List<Location>)locationrepo.saveAll(location);
	}
	
	public void saveLocation(Location e) {
		this.locationrepo.save(e);
	}
	
	public Location getLocationById(int id) {
		return this.locationrepo.findById(id).get();
	}
	
	public void delete(int id) {
		this.locationrepo.deleteById(id);
	}
}
