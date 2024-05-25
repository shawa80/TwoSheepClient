package com.siliconandsynapse.ixcpp.ui;

public interface MessageReceiverModel {

	interface Listener {

		public void messageAdded(String from, String msg);
	}
	
	public String getMessage();
	public void appendMessage(String from, String msg);
	
	public void addListener(Listener listener);
	public void removeListener(Listener listener);
	
}
