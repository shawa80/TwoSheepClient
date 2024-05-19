package com.siliconandsynapse.aclient.Fragments;

import java.util.concurrent.atomic.AtomicInteger;

public class ChatMessage {

	private static AtomicInteger ids = new AtomicInteger();
	
	private int uId;
	private String message;
	private String user;
	
	
	public ChatMessage(String user, String message) {
		uId = ids.getAndAdd(1);
	
		this.message = message;
		this.user = user;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getId() {
		return uId;
	}
	
}
