package com.example.chat;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;
import com.phonegap.impel.DftlyFAQCallback;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Search_FAQs extends ActionBarActivity implements DftlyFAQCallback  {

	private EditText Search;
	private ImageButton Search_FAQ1;
	private ListView listview;
	private TextView Text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search__faqs);
		Dftly.registerCallbackForFAQ(Search_FAQs.this);
        Search = (EditText)findViewById(R.id.FAQ_Search_ET);
        Text = (TextView)findViewById(R.id.Result);
        
        // Code for Implementing Search FAQs from the server
        Search_FAQ1  = (ImageButton) findViewById(R.id.imageButtonSearch);
      	 Search_FAQ1.setOnClickListener(new View.OnClickListener() 
      		{
      			   @Override
      			   public void onClick(View v) 
      			   {
      			   	// if button is All_FAQ
      			   	String optional_string = Search.getText().toString();
      			   	if(optional_string == null || optional_string.trim().equals(""))
      			   	{
      			    		System.out.println("When empty String");	
      			    		Text.setText("Please Enter the text");
      			   	}
      			    	else
      			    	{
      			    		System.out.println("when pass string");
      			    		Dftly.getFAQList(Search_FAQs.this,optional_string);
      			    		Text.setText("Search Result");
      			   	}
      		}
      			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search__faqs, menu);
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

	
	//Extract the Json array of FAQs from the Server 
	// and displaying Json data in to a listview using ListAdapter
	@Override
	public void resTOGetFAQList(JSONArray arg0) {
		// TODO Auto-generated method stub
		Log.i("respone in resTOGetFAQList","" + arg0.length());	
		initializeVars();
		final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		
		try{
			
			String[] faq = new String[arg0.length()+1];        
			String[] ans = new String[arg0.length()+1];
			// ITERATE THROUGH AND RETRIEVE CLUB FIELDS
			//int n = ob.length();
			for (int i = 0; i < arg0.length(); i++) 
			{
			    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
				JSONObject jo = arg0.getJSONObject(i);		                 
				// RETRIEVE EACH JSON OBJECT'S FIELDS
				faq[i]= jo.getString("FAQ");
				ans[i]  = jo.getString("answer");
				 HashMap<String, String> map = new HashMap<String, String>();
			    String faq_q=faq[i];
				map.put("faq_q",faq[i]);
				String ans_q = ans[i];
				map.put("ans_q",ans[i]);
				list.add(map);					
			}
			this.runOnUiThread(
	                new Runnable(){
	                	
	                    public void run(){
	                        {
	                        	ListAdapter adapters = new SimpleAdapter(getApplicationContext(), list,R.layout.faq_list, new String[] { "faq_q","ans_q" },new int[] { R.id.faq_q,R.id.ans_q});
			                    listview.setAdapter(adapters);
	                        }
	                    }
	                });
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
			 {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {   
				   return true;
				}

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
             });
             }catch(Exception e)
                   {
            	 		e.printStackTrace();
                   }
	}

	@Override
	public void resTOGetFAQTypes(JSONArray arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resTOGetSingleFAQ(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resTOgetFAQListFromType(JSONArray arg0) {
		// TODO Auto-generated method stub
		
	}
	public void initializeVars(){
		  listview = (ListView)findViewById(R.id.SearchList);  
		 }
}
