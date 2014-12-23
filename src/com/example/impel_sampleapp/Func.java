package com.example.chat;

import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class Func extends Fragment {
	
	 private static final String ARG_SECTION_NUMBER = "section_number";

	     //Returns a new instance of this fragment for the given section number.
	 
	    public static Func newInstance(int sectionNumber) {
	       Func fragment = new Func();
	        Bundle args = new Bundle();
	        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	        fragment.setArguments(args);
	        return fragment;
	    }
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
    
	        View rootView = inflater.inflate(R.layout.activity_func, container, false);
	        
	        //Handle onclick Event for chat, FAQ by categories, FAQ search, and Ticket 

	        rootView.findViewById(R.id.fragment_main_btn_Chat).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v){
	            	Dftly.chatWindow(getActivity(),R.drawable.dftly_icon);
	            }
	        });

	        rootView.findViewById(R.id.fragment_main_btn_FAQ).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                Intent intent = new Intent(getActivity(), FAQ.class);
	                startActivity(intent);
	            }
	        });

	        rootView.findViewById(R.id.fragment_main_btn_my_tickets).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                Intent intent = new Intent(getActivity(), ConversationStatus.class);
	                startActivity(intent);
	            }
	        });
      
	        rootView.findViewById(R.id.fragment_main_btn_Search).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                Intent intent = new Intent(getActivity(), Search_FAQs.class);
	                startActivity(intent);
	            }
	        });
	        return rootView;
	    }
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	  // A placeholder fragment containing a simple view.
	
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_func, container,
					false);
			return rootView;
		}
	}
}
