package com.siliconandsynapse.aclient.lobbyModels;

public class Credentials {

	private String user;
	private String pass;
	
	public Credentials(String user, String pass) {
		
		this.user = user;
		this.pass = pass;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPass() {
		
		return pass;
	}
	
}
