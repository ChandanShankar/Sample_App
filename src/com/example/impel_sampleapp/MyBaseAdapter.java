package com.example.chat;

import java.util.List;  
import android.annotation.SuppressLint;
import android.app.Activity;  
import android.content.Context;  
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.LinearLayout;
import android.widget.TextView;  
import com.example.impel_sampleapp.R;

public class MyBaseAdapter extends BaseAdapter {  
     private Activity mContext; 
     @SuppressWarnings("unused")
	private LinearLayout content;
     public TextView mTVItem;
     private List<Chatstring_message> mList;  
     @SuppressWarnings("unused")
	private LayoutInflater mLayoutInflater = null;  
     public MyBaseAdapter(Activity context, List<Chatstring_message> list) {  
          mContext = context;  
          mList = list;  
          mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
     }  
     @Override  
     public int getCount() {  
          return this.mList.size();  
     }  
     @Override  
     public Chatstring_message getItem(int pos) {  
          return this.mList.get(pos);  
     }   
     
     public MyBaseAdapter(Context context, int textViewResourceId) {
 		super();
 	}
     
     public void modify(int visiblePosition,int recipientIndex){
     	
     	try{
     	
 		   Chatstring_message object =  mList.get((recipientIndex));
 		   String tm = object.timeStr;
 		   object.timeStr = tm + " D";
 		   mList.set((recipientIndex) , object);
 		   super.notifyDataSetChanged();
     	}
     	catch(Exception e){
     		Log.i("Exception in modify of myBaseAdapter ","" + e);
     	}
     }
     
     @SuppressLint("InflateParams")
	@Override  
     public View getView(int position, View convertView, ViewGroup parent) {  
          View v = convertView;   
          if (convertView == null) 
          {                
        	  LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
               v = li.inflate(R.layout.list_item_message, parent, false);   
          }
                content = (LinearLayout)v.findViewById(R.id.content);
                Chatstring_message chat_mess = getItem(position);
                mTVItem = (TextView)v.findViewById(R.id.listchat);
                mTVItem.setText(chat_mess.comment);
                mTVItem.append("\n");
        		mTVItem.append(Html.fromHtml("<small>" + chat_mess.timeStr+ "</small>"));
        		mTVItem.setBackgroundResource(chat_mess.left  ? R.drawable.outgoing_message_bg: R.drawable.incoming_message_bg);
                content.setGravity(chat_mess.left ? Gravity.LEFT: Gravity.RIGHT);
                mTVItem.setGravity(chat_mess.left ? Gravity.LEFT:Gravity.RIGHT);
          return v;  
     }
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	public void add(Chatstring_message chatstring_message) {
		// TODO Auto-generated method stub
		mList.add(chatstring_message);
		//super();
	}  
}   
