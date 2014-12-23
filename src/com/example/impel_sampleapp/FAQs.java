package com.example.chat;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;
import com.phonegap.impel.DftlyFAQCallback;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FAQs extends ActionBarActivity implements DftlyFAQCallback  {
	   private ListView listview;
	  private EditText inputSearch;
	  ListAdapter adapters;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faqs);
		Dftly.registerCallbackForFAQ(FAQs.this);	 
		String typeid = getIntent().getExtras().getString("FAQid");
		System.out.print(typeid);
		//call this method when you need to search the FAQ form the server
		Dftly.getFAQListFromType(FAQs.this,typeid);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		
		  /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {
             
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ((SimpleAdapter) FAQs.this.adapters).getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.faqs, menu);
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
	public void resTOGetFAQList(JSONArray arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resTOGetFAQTypes(JSONArray arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resTOGetSingleFAQ(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}
	
    // Exracting the Json array and and the array contains Question and Answer.
	// displaying the Selected FAQ categories Question and Answers on listview
	
	@Override
	public void resTOgetFAQListFromType(JSONArray arg0) {
		// TODO Auto-generated method stub
		 initializeVars();
			Log.i("resTogetFAQListFromType","" + arg0.toString());
	final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
			
			try{
				
				String[] faq = new String[arg0.length()+1];        
				String[] ans = new String[arg0.length()+1];
				// ITERATE THROUGH AND RETRIEVE CLUB FIELDS
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
		                            adapters = new SimpleAdapter(getApplicationContext(), list,R.layout.faq_list, new String[] { "faq_q","ans_q" },new int[] { R.id.faq_q,R.id.ans_q});
				                    listview.setAdapter(adapters);
		                        }
		                    }
		                });
				listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
				 {
				@SuppressWarnings("unused")
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

		public void initializeVars(){
			  listview = (ListView)findViewById(R.id.FAQTYPEID);  
			 }
		}
