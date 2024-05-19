package com.siliconandsynapse.aclient.Fragments;

import java.util.ArrayList;
import java.util.List;

import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class GameListAdapter implements ListAdapter {

	
	private LayoutInflater mInflater;
	
	
	private List<DataSetObserver> observers;
	
	private ArrayList<GameInfo> games;
	
	public GameListAdapter(Context context) {
		
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		observers = new ArrayList<DataSetObserver>();
		games = new ArrayList<GameInfo>();
	}
	
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		observers.add(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		observers.remove(observer);
	}

	
	private void fire() {
		
		for (DataSetObserver o : observers)
			o.onChanged();
	}
	
	public void add(GameInfo game) {
		games.add(game);
		
		fire();
		
	}
	
	public void remove(GameInfo game) {
		games.remove(game);

		fire();
	}
	
	
	@Override
	public int getCount() {
		return games.size();
	}

	@Override
	public GameInfo getItem(int position) {

		return games.get(position);
	}

	@Override
	public long getItemId(int position) {

		return games.get(position).getId();
	}

	@Override
	public boolean hasStableIds() {

		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			
		
		View v;
		if (convertView == null) {
			v = mInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
		} else {
		    v = convertView;
		}
		
		TextView mainLine = (TextView)v.findViewById(android.R.id.text1);
		TextView subLine = (TextView)v.findViewById(android.R.id.text2);
		
		GameInfo gi = games.get(position);
		
		mainLine.setText("" + gi.getId());
		subLine.setText(gi.getName());
			
		return v;
	}

	
	@Override
	public int getItemViewType(int position) {

		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean isEmpty() {

		if (games.size() == 0)
			return true;
		
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		
		return true;
	}

}
