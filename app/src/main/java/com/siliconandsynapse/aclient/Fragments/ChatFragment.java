//package com.siliconandsynapse.aclient.Fragments;
//
//import com.siliconandsynapse.aclient.NetworkService;
//import com.siliconandsynapse.aclient.R;
//import com.siliconandsynapse.ixcpp.protocol.lobby.LobbyModel;
//import com.siliconandsynapse.ixcpp.protocol.lobby.MessageCmd;
//import com.siliconandsynapse.ixcpp.ui.MessageReceiverModel;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.TextView.OnEditorActionListener;
//
//public class ChatFragment extends Fragment {
//
//	private Activity act;
//
//	private EditText input;
//	private ListView messages;
//	private MessageReceiverModel messageModel;
//	private NetworkService service;
//
//	private LobbyModel lobbyModel;
//
//	private ChatListAdapter adapter;
//
//
//
//	public ChatFragment(Activity act) {
//		this.act = act;
//		messageModel = null;
//		lobbyModel = null;
//
//
//	}
//
//	public void setNetworkService(NetworkService service) {
//		this.service = service;
//
//	}
//
//
//	public void setReceiverModel(MessageReceiverModel messageModel) {
//		this.messageModel = messageModel;
//
//		messageModel.addListener(new MessageReceiverModel.Listener() {
//			@Override
//			public void messageAdded(String from, String msg) {
//				addMessage(from, msg);
//			}
//		});
//
//		addMessage("???", messageModel.getMessage());
//	}
//
//
//	public void setLobbyModel(LobbyModel lobbyModel) {
//
//		this.lobbyModel = lobbyModel;
//
//		this.lobbyModel.addListener(new LobbyModel.Listener() {
//
//			@Override
//			public void userRemoved(String user) {
//				addMessage("Notice", "Lobby Removed " + user);
//			}
//
//			@Override
//			public void userAdded(String user) {
//				addMessage("Notice", "Lobby Added " + user);
//			}
//		});
//
//	}
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//
//        ViewGroup rootView = (ViewGroup) inflater.inflate(
//                R.layout.chat, container, false);
//
//        if (messageModel != null) {
//        	addMessage("???", messageModel.getMessage());
//        }
//
//        return rootView;
//    }
//
//
//    @Override
//   	public void onStart() {
//
//   		super.onStart();
//
//   		messages = (ListView)this.getActivity().findViewById(R.id.LobbyMessages);
//   		input = (EditText)this.getActivity().findViewById(R.id.inputText);
//   		//input.setImeActionLabel("test", KeyEvent.KEYCODE_ENTER);
//
//
//		adapter = new ChatListAdapter(this.getActivity());
//
//		messages.setAdapter(adapter);
//
//   		input.setOnEditorActionListener(new OnEditorActionListener() {
//
//			@Override
//			public boolean onEditorAction(TextView v, int actionId,
//					KeyEvent event) {
//
//				String message = v.getText().toString();
//
//				if (!"".equals(message))
//					service.executeLobby(new MessageCmd(message + ""));
//
//				//sender.appendMessage(v.getText() + "");
//				v.setText("");
//
//				return false;
//			}
//
//   		});
//
//   	}
//
//    public void addMessage(final String from, final String msg) {
//
//    	act.runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				if (adapter != null)
//					adapter.add(new ChatMessage(from, msg));
//			}
//		});
//    }
//}
//
