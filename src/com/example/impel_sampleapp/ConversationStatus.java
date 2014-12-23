package com.example.chat;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;
import com.phonegap.impel.DftlyConversation;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ConversationStatus extends ActionBarActivity implements DftlyConversation {

	 Button NewConversation, OldConversation;
	 Object value;
	 private ListView listview;
	 private TextView Text;
	 HashMap<String, String> map ;
	 Boolean Activity_resume_status = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation_status);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		listview = (ListView)findViewById(R.id.listView1);
		Text=(TextView)findViewById(R.id.textView2);
		
		//Extracting the Json data from the server, the Json array contains all the Conversations 
		//that the Consumer has/had with your Support team.
		//and dispalyed in the Listview.
		
		JSONArray a = Dftly.getCoversations(ConversationStatus.this);  
		final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		System.out.print("length"+a.length());
		if(a.length()==0){  
			   Text.setText("You do not have any Conversation. Please click on the New Conversation button below to start.");
			}
		if(a!= null){
			 
						String MessageID = null;        
						String  Conversation = null;
						String  Created_time = null;
						 
						// ITERATE THROUGH AND RETRIEVE CLUB FIELDS
						
						try {
							for (int i = 0; i < a.length(); i++) {
						    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
							if(a.getJSONObject(i) != null){
								JSONObject jo = a.getJSONObject(i);		
								Log.i("Json object from ","" +  jo.get("msgID"));
								// RETRIEVE EACH JSON OBJECT'S FIELDS
								long MessaID;
							//	if(jo.get("msgID") != null)
							     MessaID = jo.getLong("msgID");
							     MessageID = String.valueOf(MessaID);
								if(jo.getString("message") != null)
									Conversation = jo.getString("message");
								if( jo.getString("createdTime") != null)
								Created_time = jo.getString("createdTime");	
								Log.i("Conversation : " + Conversation + " Created_time : " + Created_time + " MessageID " + MessageID,"");
							
								if(MessageID != null && Conversation != null && Created_time != null){
									//Log.i("Conversation : " + Conversation + " Created_time : " + Created_time + " MessageID " + MessageID ,"");
									map = new HashMap<String, String>();
								    
									map.put("mesgID",MessageID);
									map.put("Conversation",Conversation);
									map.put("Time",Created_time);
									list.add(map);					
								}
								else
									Log.i("Conversation : " + Conversation + " Created_time : " + Created_time + " MessageID " + MessageID,"");
					           }  
							}
							
						this.runOnUiThread(
				                new Runnable(){
									public void run(){
				                        {
				                        	ListAdapter adapters = new SimpleAdapter(getApplicationContext(), list,R.layout.conversation_list, new String[] { "mesgID","Conversation","Time" },new int[] { R.id.messgid,R.id.message,R.id.time});
						                    listview.setAdapter(adapters);
				                        }
				                    }
				                });
						
						 listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
					            @Override
					            public void onItemClick(AdapterView<?> arg0, View view, int arg2,long arg3) {					            	
					            	 Intent i = new Intent(getApplicationContext(), Conversation.class);
					                 
					                 // send album id to tracklist activity to get list of songs under that album
					                 String messgid = ((TextView) view.findViewById(R.id.messgid)).getText().toString();
					                 i.putExtra("messgid", messgid);               
					                  
					                 startActivity(i);
				           }
						 });
						
						
				} catch (JSONException e) {
						// TODO Auto-generated catch block
					Log.i("Exception in json exctration ","" + e);
						e.printStackTrace();
					}
			}		
	 
	 NewConversation=(Button)findViewById(R.id.NEW_CONV);
	 NewConversation.setOnClickListener(new View.OnClickListener()
	 { 
	 public void onClick(View v)  
		    {
		       
		        	    Intent intent = new Intent(getApplicationContext(),Conversation.class);
		        	    String messgid ="0";
		        	    intent.putExtra("messgid",messgid);
						startActivity(intent);
		    }
	  });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversation_status, menu);
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
	public void messageFromServer(JSONObject ob) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error_Callback(JSONObject ob) {
		// TODO Auto-generated method stub
		
	}
	
	public void onResume(){
		super.onResume();
		if(!Activity_resume_status)
		{
		Dftly.getCoversations(ConversationStatus.this);
		Activity_resume_status = true;
		}
	}

}

	

	
