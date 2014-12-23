 package com.example.chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.impel_sampleapp.R;
import com.phonegap.impel.Dftly;
import com.phonegap.impel.DftlyConversation;

public class Conversation extends ActionBarActivity implements DftlyConversation {

	private ListView messagesContainer;
	private EditText messageEditText;
	private Button sendButton;
	private TextView ServerError;
    private List<Chatstring_message> mItems;  
	private MyBaseAdapter adapter;  
	Long coversationId = (long) 0;
     
	@SuppressWarnings("null")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Intent messageIntent = getIntent();
		Dftly.regConCallback(Conversation.this,R.drawable.ic_launcher);
		if(messageIntent != null && messageIntent.getStringExtra("messgid") != null && !messageIntent.getStringExtra("messgid").equals("0"))
			{String  messid_ = messageIntent.getStringExtra("messgid");
			if(messid_ != null)
			get_conv(messid_);
			System.out.print("inside messid");
		}
		 else if(messageIntent != null && messageIntent.getStringExtra("messgid") != null && messageIntent.getStringExtra("messgid").equals("0")){
			 Log.i("MEssage id when do new conversation","!!!" + messageIntent.getStringExtra("messgid"));
			 String messid = "0";
			 initdetailsViews();
	  	}
		}

		     
	private Long getExtra(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//To start with new Conversation
	private void initdetailsViews() 
	{       
		    mItems = new ArrayList<Chatstring_message>(); 
			messagesContainer = (ListView) findViewById(R.id.messagesContainer);
	  		messageEditText = (EditText) findViewById(R.id.messageEdit);
	        sendButton = (Button) findViewById(R.id.chatSendButton);
	    	ServerError =(TextView)findViewById(R.id.ServerError);
	    	
	        sendButton.setOnClickListener(new View.OnClickListener() 
	        {
	        	@Override
	        	public void onClick(View v) 
	        	{					
	        		  addItemsToList();  
	        		  
	        	}
			});		        
	}
	  
private void addItemsToList() {  

	String currentTime = getTimeNow();
	currentTime += "," + " " + getDateNow();
    String chatmessage = messageEditText.getText().toString();
    Log.i("MEassage on new conversation","" + chatmessage);
		if (TextUtils.isEmpty(chatmessage) || chatmessage == null || chatmessage.trim().equals("") ) {
			return;
		}
		else
		{  	
		adapter = new MyBaseAdapter(this, mItems);
		messagesContainer.setAdapter(adapter);
	    adapter.add(new Chatstring_message(false, messageEditText.getText().toString(), currentTime,null));
        messageEditText.setText(" ");
        if(coversationId == 0){
        	// Method for submitting the new conversation 
        	coversationId = Dftly.submitNewConverstation(Conversation.this,chatmessage); 
        }
        else if (coversationId > 0){
        	//Method for submitting the existing conversation using the Conversation_ID
        	  Dftly.submitConverstation(Conversation.this,chatmessage,coversationId); 
        }
        adapter.notifyDataSetChanged();
		}
		
}  
	
	
	// for getting detail Conversation using message_id
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initdetailsViews(long messid) 
		{
				final long mess_id = messid;
				messagesContainer = (ListView) findViewById(R.id.messagesContainer);
		  		messageEditText = (EditText) findViewById(R.id.messageEdit);
		        sendButton = (Button) findViewById(R.id.chatSendButton);
		    	ServerError =(TextView)findViewById(R.id.ServerError);
		    	
		        sendButton.setOnClickListener(new View.OnClickListener() 
		        {
		        	@Override
		        	public void onClick(View v) 
		        	{					
		        		  addItemsToList(mess_id);                  
		        	}
				});		        
		}
	
	//submitting the existing Conversation
	private void addItemsToList(long messid) {  
		long mess=messid;
		String currentTime = getTimeNow();
		currentTime += "," + " " + getDateNow();
        String chatmessage = messageEditText.getText().toString();
 		if (TextUtils.isEmpty(chatmessage) || chatmessage == null || chatmessage.trim().equals("") ) 
 		{
 			return;
 		}
 		else
 		{	
 	    adapter.add(new Chatstring_message(false, messageEditText.getText().toString(), currentTime,null));
        messageEditText.setText(" ");
        // call this method when you need to submit the conversation_message using ConversationID
        Dftly.submitConverstation(Conversation.this,chatmessage,mess); 
        adapter.notifyDataSetChanged(); 
 		}
   }  
	
	// Code to get current time
	@SuppressLint("SimpleDateFormat")
	public String getTimeNow() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
		return dateFormat.format(calendar.getTime());
	}

	// Code to get current date
	@SuppressLint("SimpleDateFormat")
	public String getDateNow() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
		return dateFormat.format(calendar.getTime());
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversation, menu);
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
	
	// Extracting the JSON object from the server, the object contains Conversation Type, 
	//message and the Conversation id of an existing Conversation 

	@SuppressWarnings("unused")
	@Override
	public void messageFromServer(JSONObject ob) {
		
		Log.i("messageFromServer","1");
		try{
			        
				// RETRIEVE EACH JSON OBJECT'S FIELDS 
			    if(ob != null){
				Log.i("from the servers side",""+ob.toString());
				String currentTime=ob.getString("createdTime");
				Log.i("messageFromServer","2 " +currentTime);
				String Responce= ob.getString("conversationType");
				String servermessage= ob.getString("message");
				String Conversationid= ob.getString("convesationId");
				if(Conversationid != null){
					System.out.println(Conversationid);
					get_conv(Conversationid);
				}
					Log.i("inside Conversation","When message is called from server"); 	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Handle the error messages 
	@Override
	public void error_Callback(JSONObject ob) {
		// TODO Auto-generated method stub
		try{
			
				// RETRIEVE EACH JSON OBJECT'S FIELDS
				String Error= ob.getString("error");
				ServerError.setText(Error);
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
				
	}
	@Override
	 public void onResume()
	{
		super.onResume();
		Dftly.conNotificationOpened(Conversation.this);
	}
	
	
	// Extracting the JSON data of the existing Conversation 
	//Displaying the data in listView 
	public void get_conv(String messid)
	{
		String messd = messid;
		long messid1 = Long.valueOf(messd);
		Log.i("MEssage id ","!!!" + messid1);
	
		JSONArray a = Dftly.getDetailedConversation(Conversation.this,messid1);
		mItems = new ArrayList<Chatstring_message>();  
		if(a != null){
			for(int i1=0;i1<a.length();i1++){
				try {
						Log.i("onResume",""  + " Coversation msg:" + a.getJSONObject(i1).getString("message") + " Created time:" + a.getJSONObject(i1).getString("createdTime"));
	
						String ConversationType = null;        
						String  Conversation = null;
						String  Created_time = null;
						
						    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
							if(a.getJSONObject(i1) != null){
								Log.i("Conversation json object","" +  a.getJSONObject(i1));
								JSONObject jo = a.getJSONObject(i1);		                 
								// RETRIEVE EACH JSON OBJECT'S FIELDS
								if(jo.getString("conversationType") !=null)
									ConversationType = jo.getString("conversationType");
								if(jo.getString("message") != null)
									Conversation = jo.getString("message");
								if( jo.getString("createdTime") != null)
								Created_time = jo.getString("createdTime");	
								if(ConversationType != null && Conversation != null && Created_time != null){
									Log.i("Conversation : " + Conversation + " Created_time : " + Created_time + " MessageID " +ConversationType,"");
									if(ConversationType.contentEquals("Response"))
									{    Log.i("Then the message is updated from database", "");
										mItems.add(new Chatstring_message(true,Conversation,Created_time,null));
									}
									else{
										mItems.add(new Chatstring_message(false,Conversation, Created_time,null));
										}
								}
								else
								{
									Log.i("Conversation : " + Conversation + " Created_time : " + Created_time + " MessageID " + ConversationType,"");
					           }
						   	
				
				}
				}catch (JSONException e) {
						// TODO Auto-generated catch block
					
						e.printStackTrace();
					}
		 }
	}
	
		
			System.out.print("inside else");
			initdetailsViews(messid1);
			adapter = new MyBaseAdapter(this, mItems);  
			messagesContainer.setAdapter(adapter);
		}
}















