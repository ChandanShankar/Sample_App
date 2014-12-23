 package com.example.chat;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;


import com.example.impel_sampleapp.R;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.facebook.android.Facebook.DialogListener;
import com.phonegap.impel.Dftly;
import com.phonegap.impel.SocialProfileInfo;
//import com.phonegap.impel.SocialProfileInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBar;

public class MY_APP extends ActionBarActivity implements ActionBar.TabListener, SocialProfileInfo{//,SocialProfileInfo{

    //-------------- Facebook Integration -----------------//
	 public static final String mAPP_ID = "868784706479748";
	 public Facebook mFacebook = new Facebook(mAPP_ID);
	//---------- end------------//
	SectionsPagerAdapter mSectionsPagerAdapter;

	    /**
	     * The {@link ViewPager} that will host the section contents.
	     */
	    ViewPager mViewPager;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my__app);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		SharedPreferences sharedPref1 = (this.getSharedPreferences("Registration_File", Context.MODE_PRIVATE));
		boolean Res = sharedPref1.getBoolean("RegistrationStatus",false);
		Log.i("2!!!!!!!!!!!!!!!!!!!!!!!!!!","");
		if(!Res)
		{
			
			Log.i("3!!!!!!!!!!!!!!!!!!!!!!!!!!","");
			Intent intent = new Intent(getApplicationContext(),Registration.class);
			startActivity(intent);
		}
		
		
		// Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });



        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_ap, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//------Facebook Integration-----------//
		if (id == R.id.facebookid) {
			if( !mFacebook.isSessionValid() ) {
    			Toast.makeText(MY_APP.this, "Authorizing", Toast.LENGTH_SHORT).show();
    			mFacebook.authorize(MY_APP.this, new String[] { "" }, new LoginDialogListener());
    			}
    		else {
    			Toast.makeText( MY_APP.this, "Has valid session", Toast.LENGTH_SHORT).show();
    			try {
    				final JSONObject profile = Util.parseJson(mFacebook.request("me"));
    				Log.i("respone in FACEBOOK Profile details","" + profile.toString());
    	    		final String id1 = profile.getString("id");
    	               final String firstname = profile.getString("first_name");
    	                // getting email of the user
    	                final String email = profile.getString("email");
    	                final String gender = profile.getString("gender");
    	               final String last_name = profile.getString("last_name");
    	               final String name= profile.getString("name");
                   //  final String Profile = id1+"/n"+ firstname + "\n" + email + "\n"+ gender +"\n"+last_name+"\n"+ name;
	    			runOnUiThread(new Runnable() {
	    				 
		                   @Override
		                  public void run() {
		                        Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email+ "\nid:" +id1 + "\nfirstname" + firstname + "\n gender " + gender, Toast.LENGTH_LONG).show();
		                        JSONObject social_profile = null;
		                		
		            			try {
		            				Log.i("inside secondary json","");
		            				JSONObject	obj = new JSONObject();
		            			 		obj.put("soc_net_contact__first_name",firstname);
		            			 		obj.put("soc_net_contact__full_name",name);
		            			 		obj.put("soc_net_contact__person_id_in_soc_net",id1);
		            			 		obj.put("soc_net_contact__soc_net_name","Facebook");
		            			 	    obj.put("soc_net_contact__recent_profile", profile);
		            			 		Dftly.socialProfileInfo(obj ,MY_APP.this);
		            				}catch(Exception e){
		            					e.printStackTrace();
		            			}
		            			
		            			Log.i("inside data function","" + social_profile.toString());
		            	  
		                   }
		 
		                });
    			}
    			catch( Exception error ) {
    				Toast.makeText( MY_APP.this, error.toString(), Toast.LENGTH_SHORT).show();
    				}
    			}
    		}
	Session_store.restore(mFacebook, this);
			return true;		
	}
					//-------End of facebook Integration------------//
	  @SuppressWarnings("deprecation")
	@Override
	    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	        // When the given tab is selected, switch to the corresponding page in
	        // the ViewPager.
	        mViewPager.setCurrentItem(tab.getPosition());
	    }

	    @Override
	    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	    }

	    @Override
	    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	    }

	    
	    /**
	     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	     * one of the sections/tabs/pages.
	     */
	    public class SectionsPagerAdapter extends FragmentPagerAdapter {

	        public SectionsPagerAdapter(FragmentManager fm) {
	            super(fm);
	        }

	        @Override
	        public Fragment getItem(int position) {
	            // getItem is called to instantiate the fragment for the given page.
	            // Return a PlaceholderFragment (defined as a static inner class below).
	            if (position == 0) {
	              return Todo.newInstance();
	            }
	           else
	           	{
	                return Func.newInstance(position+1);
	            }
	        }

	        @Override
	        public int getCount() {
	            // Show 2 total pages.
	            return 2;
	        }

	        public Drawable getPageIcon(int position) {
	            switch (position) {
	                case 0:
	                case 1:
	                    return null;
	            }

	            return null;
	        }
            // method will display the tab name
	        @Override
	        public CharSequence getPageTitle(int position) {
	            Locale l = Locale.getDefault();
	            switch (position) {
	                case 0:
	                   return getString(R.string.title_section1).toUpperCase(l);
	                case 1:
	                    return getString(R.string.title_section2).toUpperCase(l);
	            }
	            return null;
	        }
	    }
	   //-------------Facebook Integration------------//
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	            mFacebook.authorizeCallback(requestCode, resultCode, data);
			
		}
		
		final class LoginDialogListener implements DialogListener {
			public void onComplete(Bundle values) {
				try {
					//The user has logged in, so now you can query and use their Facebook info
		    		final JSONObject profile = Util.parseJson(mFacebook.request("me"));
		    		final String id = profile.getString("id");
		               final String firstname = profile.getString("first_name");
		                // getting email of the user
		                final String email = profile.getString("email");
		                final String gender = profile.getString("gender");
		               final String last_name = profile.getString("last_name");
		               final String name= profile.getString("name");
	                 final String Profile = id+"/n"+ firstname + "\n" + email + "\n"+ gender +"\n"+last_name+"\n"+ name;
					Toast.makeText( MY_APP.this, "Thank you for Logging In, " + firstname + " " + last_name + "!", Toast.LENGTH_SHORT).show();
					 runOnUiThread(new Runnable() {
						 
		                    @Override
		                    public void run() {
		                        Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email+ "\nid:" +id + "\nfirstname" + firstname + "\n gender " + gender, Toast.LENGTH_LONG).show(); 
		                        JSONObject social_profile = null;
		                		
		            			try {
		            			    social_profile = new JSONObject();
		            				social_profile.put("objectname","contact" );
		            				social_profile.put("if_duplicate","Update");
		            				social_profile.put("unique_condition","pri_email = '#Email#'");
		            				Log.i("inside secondary json","");
		            				JSONObject	obj = new JSONObject();
		            			 		obj.put("soc_net_contact__first_name",firstname);
		            			 		obj.put("soc_net_contact__full_name",name);
		            			 		obj.put("soc_net_contact__person_id_in_soc_net",id);
		            			 		obj.put("soc_net_contact__soc_net_name","Facebook");
		            			 		obj.put("soc_net_contact__recent_profile", profile);
		            			 		Dftly.socialProfileInfo(obj,MY_APP.this);
		            				}catch(Exception e){
		            					e.printStackTrace();
		            			}
		            			Log.i("inside data function","" + social_profile.toString());
		            	  
		                    }	                    
		 
		                });
		 
					Session_store.save(mFacebook, MY_APP.this);
					}
				catch( Exception error ) {
					Toast.makeText( MY_APP.this, error.toString(), Toast.LENGTH_SHORT).show();
					}
				
					}

			@Override
			public void onFacebookError(FacebookError e) {
				// TODO Auto-generated method stub
				Toast.makeText(MY_APP.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		    }
			

			@Override
			public void onError(DialogError e) {
				// TODO Auto-generated method stub
				Toast.makeText(MY_APP.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		    }
			

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				Toast.makeText(MY_APP.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
		    }
			
		}
	@Override
		public void resToSocialProfileInfo(JSONObject arg0) {
			// TODO Auto-generated method stub
			// ITERATE THROUGH AND RETRIEVE CLUB FIELDS
			Log.i("response","........" + arg0.toString());
			try{
			String Type = arg0.getString("Type");
			String status = arg0.getString("status");
			Log.i("Type : " + Type + " status : " + status,"");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
		}
		
		
}

