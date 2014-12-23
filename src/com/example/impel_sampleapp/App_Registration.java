package com.example.chat;

import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class App_Registration extends ActionBarActivity {
    Button Reset;
    EditText AppKey, AppID;
    static Context initialContext;
    Boolean RegistrationStatus = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app__registration);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		AppKey = (EditText)findViewById(R.id.APPKEY);
	   
		AppID = (EditText)findViewById(R.id.APPID);
		
		Reset= (Button)findViewById(R.id.reset);
		Reset.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				        String appkey = AppKey.getText().toString();
				        String appid = AppID.getText().toString();
				        if(appkey!= null && appkey!= null){
				        		Dftly.install(App_Registration.this,appid,appkey,"true");
				        		  //Creating an object for submitting User detail
				        	    SharedPreferences sharedPref1 = getSharedPreferences("Registration_File", Context.MODE_PRIVATE);
				        	    SharedPreferences.Editor  RegisterObject =sharedPref1.edit();
				        	    RegisterObject.putBoolean("RegistrationStatus", false);
				        	
				        	    RegisterObject.commit();
				        		Intent intent = new Intent(getApplicationContext(),Registration.class);
				        		startActivity(intent); 
				        		finish();
					}
			     }
				});
		}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.app__registration, menu);
		return true;
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
}
