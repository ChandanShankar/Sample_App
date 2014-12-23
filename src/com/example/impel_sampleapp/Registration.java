package com.example.chat;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends ActionBarActivity {

	EditText First_name,Last_name, Email_Id, Ph_no, Comp_name;
	Button chat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				Register();
			}
	public void Register()
	{
		
		 Last_name=(EditText)findViewById(R.id.Lastname);
		 Comp_name=(EditText)findViewById(R.id.Comp);
		 First_name = (EditText)findViewById(R.id.Firstname);
	        // TextWatcher would let us check validation error on the fly
	        First_name.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.hasText(First_name);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
	        	Email_Id=(EditText)findViewById(R.id.Email);
	        	Email_Id.addTextChangedListener(new TextWatcher() {
	            // after every change has been made to this editText, we would like to check validity
	            public void afterTextChanged(Editable s) {
	                Validation.isEmailAddress(Email_Id, true);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
	 
	        	 Ph_no = (EditText)findViewById(R.id.Phone);
	        	 Ph_no.addTextChangedListener(new TextWatcher() {
	            public void afterTextChanged(Editable s) {
	                Validation.isPhoneNumber(Ph_no, false);
	            }
	            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	            public void onTextChanged(CharSequence s, int start, int before, int count){}
	        });
		 
		 
		 chat=(Button)this.findViewById(R.id.button1);
		 chat.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v) 
				{    /*
	                Validation class will check the error and display the error on respective fields
	                but it won't resist the form submission, so we need to check again before submit
	                 */
	                if ( checkValidation () )
	                    submitForm();
	                else
	                    Toast.makeText(Registration.this, "Form contains error", Toast.LENGTH_LONG).show();
	            }
	        });
	}
	
	private void submitForm() {
        // Submit your form here. your form is valid
		String firstname =  First_name.getText().toString();
		String lastname = Last_name.getText().toString();
		String pri_email = Email_Id.getText().toString();
		String company = Comp_name.getText().toString();
	    String mobile_phone = Ph_no.getText().toString();
	    
	    //Creating an object for submitting User detail
	    SharedPreferences sharedPref1 = (this.getSharedPreferences("Registration_File", Context.MODE_PRIVATE));
	    SharedPreferences.Editor  RegisterObject =sharedPref1.edit();
	    RegisterObject.putBoolean("RegistrationStatus", true);
	    RegisterObject.commit();
	  try {
			JSONArray jsonArr = new JSONArray();
			JSONObject	obj = new JSONObject();
			 	obj.put("firstname",firstname);
			    obj.put("lastname",lastname);
			    obj.put("pri_email",pri_email);
			    obj.put("company",company);
			    obj.put("mobile_phone", mobile_phone);
			    jsonArr.put(obj);
			    Dftly.registerContact (Registration.this, obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		Registration.this.finish();
		
    }
 
    private boolean checkValidation() {
        boolean ret = true;
 
        if (!Validation.hasText(First_name)) ret = false;
        if (!Validation.isEmailAddress(Email_Id, true)) ret = false;
        if (!Validation.isPhoneNumber(Ph_no, false)) ret = false;
 
        return ret;
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
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
