package com.example.demo.common;

import java.util.ArrayList;

public class UserTypes {
	
	public static ArrayList<String> usertypes = new ArrayList<String>();
	
	public static void addusertypes() {
		usertypes.add("ROLE_USER");
		usertypes.add("ROLE_IROSPECT_USER");
		usertypes.add("ROLE_ADMIN");
	}
	
	public static ArrayList<String> getUserTypes() {
		addusertypes();
		return usertypes;
	}
	

}
