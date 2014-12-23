package com.example.chat;

import com.example.impel_sampleapp.R;
import com.phonegap.impel.ChatService;
import com.phonegap.impel.Dftly;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends ActionBarActivity {
   
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// call this method when you need to Installing the Dftly SDK 
		Dftly.install(MainActivity.this,"my_cars_76bd275e-5882-4786-bebc-0696c962bd19","14f467f0-d96c-4eda-9869-abe74db0ec9c",null);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	@Override
	public void onResume(){
		super.onResume();
		int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
                public void run() {
                        startActivity(new Intent(MainActivity.this, MY_APP.class));
                        //finish();
                }
        }, secondsDelayed * 1000);
	}
	
	// Back button functionality override
	@Override
	public void onBackPressed() {
		Log.i("onBackPressed Called","");
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startActivity(startMain);
	}
}
