package com.example.chat;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;
import com.phonegap.impel.DftlyFAQCallback;

import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FAQ extends ActionBarActivity implements DftlyFAQCallback 
{

		Object value;
	    int yourInt = 0;
	    ArrayAdapter<String> adapter;	
	    ArrayList<String> arraylist;
	    ArrayList<HashMap<String, String>> albumsList;
	    private ListView listview;
		private EditText Search;
		boolean Activity_resume_status = false;
	    @Override
	    protected void onCreate(Bundle savedInstanceState)
	    {
	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.activity_faq);
	    	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    	Dftly.registerCallbackForFAQ(FAQ.this);	 
	    	
	    	// TO get the FAQs Categories
	    	Dftly.getFaqTypes(FAQ.this); 	
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.faq, menu);		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_search) {
	        }
			return true;
		}
		

		


	//Code to update FAQ types for selection.
	// Extract JSON object, object contains FAQ_Type id, Categories and Descriptions
	//and displayed in Listview.
	//Code to update faq types for selection
	
@SuppressWarnings("unused")
@Override
public void resTOGetFAQTypes(JSONArray ob)
{
	// TODO Auto-generated method stub
		Log.i("respone in resTOGetFAQTypes","" + ob.toString());
		initializeVars();
		try{
				final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
						String[] despt = new String[ob.length()];        
						String[] typename = new String[ob.length()];
						String[] IDType = new String[ob.length()];
						//Log.i("length"+ob.length(),"!!!!!!");
						// ITERATE THROUGH AND RETRIEVE CLUB FIELDS
						//int n = ob.length();
						for (int i = 0; i < ob.length(); i++) 
						{
						    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
							JSONObject jo = ob.getJSONObject(i);		                 
							// RETRIEVE EACH JSON OBJECT'S FIELDS
							IDType[i]= jo.getString("FAQTypeId");
							typename[i]  = jo.getString("Tpye");
							despt[i] = jo.getString("Description");
							 HashMap<String, String> map = new HashMap<String, String>();
							String ID_Type = IDType[i];
							map.put("ID_Type", IDType[i] );
						    String typname = typename[i];
							map.put("typname",typename[i]);
							System.out.print(typename[i]);
							String desc = despt[i];
							map.put("desc", despt[i]);
							list.add(map);
							//Log.i("IDType"+IDType[i]+"typname"+typename[i]+"dec"+despt[i],"!!!!!");
						}
						this.runOnUiThread(
				                new Runnable(){
				                	
				                    public void run(){
				                        {
				                        	ListAdapter adapters = new SimpleAdapter(getApplicationContext(), list,R.layout.type_faq, new String[] { "typname","ID_Type","desc" },new int[] { R.id.typ, R.id.typid,R.id.Desp});
				                            listview.setAdapter(adapters);
				                        }
				                    }
				                });
						listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
						 {

							@Override
							public void onItemClick(AdapterView<?> parent,View view, int position, long id) {
								// TODO Auto-generated method stub
								String Typeid = ((TextView)view.findViewById(R.id.typid)).getText().toString();
								Intent intent = new Intent(getApplicationContext(),FAQs.class);
								intent.putExtra("FAQid",Typeid);
								startActivity(intent);
							}
						 });
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}		
}


@SuppressWarnings("unused")
@Override
public void resTOgetFAQListFromType(JSONArray ob) 
{
	// TODO Auto-generated method stub
}

	public void initializeVars(){
		listview = (ListView)findViewById(R.id.listView1); 
		 }
	
	public void onResume(){
		super.onResume();
		if(!Activity_resume_status)
		{
		Dftly.getFaqTypes(FAQ.this);
		Activity_resume_status = true;
		}
	}

	@Override
	public void resTOGetFAQList(JSONArray arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resTOGetSingleFAQ(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}
}






