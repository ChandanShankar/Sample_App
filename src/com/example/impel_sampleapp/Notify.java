         package com.example.chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;
import com.phonegap.impel.DftlyNotiCallback;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



public class Notify extends ActionBarActivity implements DftlyNotiCallback{
	final Context context = this;
	AlertDialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//Handle the notification for the Server
		JSONArray a =Dftly.getAllNotifications(Notify.this);
		if(a!= null)
		{
			Log.i("length:"," "+ a.length());
	     }	
		Dftly.showNotifications(Notify.this,R.drawable.ic_launcher);
	} 


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notify, menu);
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
	public void notificationFromDftly(JSONObject ob)
	{
		// TODO Auto-generated method stub
	
			try{	
				if(ob != null){
					//------------ITERATE THROUGH AND RETRIEVE CLUB FIELDS---------------//
				Log.i("Notification object length ","" + ob.length());
						// RETRIEVE JSON OBJECT FIELDS
						String title = ob.getString("Title");
						String body = ob.getString("Body");
						String question =" Title :"+ title  +"Body  "+ body ;
						System.out.print(question);
					
						
					             //------AlertDialog------//
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					alertDialogBuilder.setTitle(title);
					 
					// set dialog message
					alertDialogBuilder
						.setMessage(body)
						.setCancelable(false)
						.setPositiveButton("ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
			
							}
						  });
						// create alert dialog
					if(alertDialogBuilder != null){
						alertDialog	 = alertDialogBuilder.create();

						// show it
						 if(!this.isFinishing()){
								alertDialog.show();
						    }
					
					}
				}
				else{
					Log.i("Notification object is null ","");
				}
				}
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
			
	}
	
	
	@Override
	  protected void onStop() {
	    super.onStop();
	if (alertDialog != null) {

	        if(alertDialog.isShowing())
	        	alertDialog.dismiss();

	        alertDialog = null;
	 }
	}
	
	@Override
	 public void onDestroy(){
		 super.onDestroy();
		if (alertDialog != null) {

	        if(alertDialog.isShowing())
	        	alertDialog.dismiss();

	        alertDialog = null;
	 }
	}
	@Override
	 public void onResume()
	{
		super.onResume();
		Dftly.notificationOpened(Notify.this);
	}
}
