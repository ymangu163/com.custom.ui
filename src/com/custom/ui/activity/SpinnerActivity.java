package com.custom.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.custom.ui.R;

public class SpinnerActivity extends Activity {
	private EditText inputEdit;
	private ImageView downImage;
	private List<String>  msgList;
	
	private PopupWindow popWindow;  //定义 PopupWindow
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinner_layout);
		
		inputEdit = (EditText)findViewById(R.id.inputEdit);
		downImage=(ImageView)findViewById(R.id.downImage);
		
		msgList = new ArrayList<String>();
		
		for (int i = 0; i < 20; i++) {
			msgList.add("1000000000"+i);
		}
		
		initListView();   // 初始化 ListView
		downImage.setOnClickListener(downListener);
		
		
		
		
	}
	
	
	// 初始化 ListView
	private void initListView() {

		listView = new ListView(this);   
		listView.setBackgroundResource(R.drawable.listview_background);   //设置ListView背景
		listView.setDivider(null);   //设置没有分隔线
		listView.setVerticalScrollBarEnabled(false);   //关闭垂直滑动线
		listView.setAdapter(new  SpinnerAdapter());
		
		
		
	}
	
	private OnClickListener downListener= new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//定义 PopupWindow
			popWindow = new PopupWindow(SpinnerActivity.this);
			popWindow.setWidth(inputEdit.getWidth());   //设置popupWindow宽度
			popWindow.setHeight(200); 		//设置其高度
			popWindow.setContentView(listView);  // 设置里面的内容
			popWindow.setOutsideTouchable(true);  //点击 PopupWindow以外的区域，PopupWindow自动消失
			
			// 显示在某个View 的下方，及x,y的偏移
			popWindow.showAsDropDown(inputEdit, 0, 0);
			
			
			
		}
	};
	
	private class SpinnerAdapter extends  BaseAdapter{
		private ViewHolder holder;
		@Override
		public int getCount() {
			return msgList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=View.inflate(getApplicationContext(), R.layout.spinner_list_item, null);
				holder=new ViewHolder();
				holder.tv_msg=(TextView)convertView.findViewById(R.id.tv_list_item);
				holder.delete=(ImageView)convertView.findViewById(R.id.delete);
				convertView.setTag(holder);	
				
			}else{
				holder=(ViewHolder) convertView.getTag();				
			}
			holder.tv_msg.setText(msgList.get(position));
			// delete ImageView 的点击监听事件
			holder.delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 删除对应的条目
					msgList.remove(position);
					notifyDataSetChanged();      //刷新ListView
					
				}
			});
			
			// 整个 Item 布局的点击监听事件
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// 点击之后，显示在输入框中
					inputEdit.setText(msgList.get(position));
					
					popWindow.dismiss();   // 让popuWindow消失
					
					
				}
			});
			
			return convertView;
		}
		
		
		
		
	}

	// 定义 ViewHolder
 private class ViewHolder{
	 
	 TextView tv_msg;
	 ImageView delete;
	 
 }
	
}
