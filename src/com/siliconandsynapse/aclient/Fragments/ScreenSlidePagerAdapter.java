package com.siliconandsynapse.aclient.Fragments;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    
	private ArrayList<Fragment> screens;
	
	
	public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        
        screens = new ArrayList<Fragment>();
        
    }

	public void addFragment(Fragment f) {
		screens.add(f);
	}
	
	
    @Override
    public Fragment getItem(int position) {
        return screens.get(position);
    }

    @Override
    public int getCount() {
        return screens.size();
    }

}
