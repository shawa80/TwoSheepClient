//package com.siliconandsynapse.aclient.lobbyModels;
//
//import com.siliconandsynapse.observerPool.ObserverPool;
//import com.siliconandsynapse.ixcpp.ui.MessageReceiverModel;
//
//public class DefaultMessageModel implements MessageReceiverModel {
//
//	private ObserverPool<Listener> pool;
//	private String mgs;
//
//	public DefaultMessageModel() {
//
//		pool = new ObserverPool<Listener>(Listener.class);
//		mgs = "";
//	}
//
//
//	@Override
//	public String getMessage() {
//
//		return mgs;
//	}
//
//	public void appendMessage(final String fromId, final String message) {
//
//		mgs += message;
//
//		pool.getDispatcher().messageAdded(fromId, message);
//	}
//
//
//	@Override
//	public void addListener(Listener listener) {
//		pool.add(listener);
//	}
//
//	@Override
//	public void removeListener(Listener listener) {
//		pool.add(listener);
//	}
//
//
//
//}
