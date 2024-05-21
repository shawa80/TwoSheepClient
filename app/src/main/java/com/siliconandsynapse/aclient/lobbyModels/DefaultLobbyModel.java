//package com.siliconandsynapse.aclient.lobbyModels;
//
//import java.util.HashSet;
//
//import com.siliconandsynapse.ixcpp.protocol.lobby.LobbyModel;
//import com.siliconandsynapse.observerPool.ObserverPool;
//
//public class DefaultLobbyModel implements LobbyModel {
//
//	private HashSet<String> users;
//	private ObserverPool<Listener> pool;
//
//	public DefaultLobbyModel() {
//
//		users = new HashSet<String>();
//		pool = new ObserverPool<Listener>(Listener.class);
//	}
//
//
//	@Override
//	public void addUser(String user) {
//		users.add(user);
//		pool.getDispatcher().userAdded(user);
//	}
//
//	@Override
//	public void clear() {
//
//		for (String user : users)
//			pool.getDispatcher().userRemoved(user);
//
//		users.clear();
//	}
//
//	@Override
//	public void removeUser(String user) {
//		users.remove(user);
//		pool.getDispatcher().userRemoved(user);
//	}
//
//
//	@Override
//	public void addListener(Listener listener) {
//		pool.add(listener);
//
//	}
//
//
//	@Override
//	public void removeListener(Listener listener) {
//		pool.remove(listener);
//
//	}
//
//}
