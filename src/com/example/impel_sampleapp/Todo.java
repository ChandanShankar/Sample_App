package com.example.chat;

import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class Todo extends Fragment {
		
		 private static final String ARG_SECTION_NUMBER = "section_number";

		    /**
		     * Returns a new instance of this fragment for the given section
		     * number.
		     */
		 public static Todo newInstance() {
		        Todo fragment = new Todo();
		        return fragment;
		    }
		@Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                             Bundle savedInstanceState) {
	    
		        View rootView = inflater.inflate(R.layout.activity_todo, container, false);
		       
		        return rootView;
		    }
		

		/**
		 * A placeholder fragment containing a simple view.
		 */
		public static class PlaceholderFragment extends Fragment {

			public PlaceholderFragment() {
			}

			@Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
				View rootView = inflater.inflate(R.layout.fragment_todo, container,
						false);
				return rootView;
			}
		}
}
	



