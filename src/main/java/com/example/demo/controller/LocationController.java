package com.example.demo.controller;

import java.util.List;

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
import com.example.demo.entity.Location;
import com.example.demo.service.LocationServices;

@RestController
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private LocationServices locationServices;
	

	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_IROSPECT_USER') or hasAuthority('ROLE_USER')")
	@GetMapping("/allLocation")
	private Iterable<Location> getLocation(){
		return locationServices.getAll();
	}
	

	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_IROSPECT_USER') or hasAuthority('ROLE_USER')")
	@GetMapping("/getLocationById/{id}")
	private Location getLocationById(@PathVariable int id) {	
		return locationServices.getLocationById(id);
	}
	

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping(value="/bulkLocationSave")
	private String addAllLocation(@RequestBody List<Location> location) {
		locationServices.addAllLocation(location);
		return "All Locations added successfully";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping(value="/savveLocation") 
	private String addEmployee(@RequestBody Location e){
		locationServices.saveLocation(e);
		return "Location saved";
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/updateLocation/{id}")
	private Location updateLocation(@RequestBody Location e, @PathVariable int id) {
		e.setId(id);
		locationServices.saveLocation(e);
		return e;
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/deleteLocation/{id}")
	private String deleteLocation(@PathVariable int id) {
		locationServices.delete(id);
		return "Location Deleted";
	}
	
}
