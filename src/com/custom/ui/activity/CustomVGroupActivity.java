package com.custom.ui.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.custom.ui.R;
import com.custom.ui.view.MyScrollView;

public class CustomVGroupActivity extends Activity {
	
	
	private MyScrollView msv;
	private RadioGroup radioGroup;
	
	private List<Integer> ids=new ArrayList<Integer>(Arrays.asList(R.drawable.a1,
			R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6));

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_scroll_view);
		
		msv = (MyScrollView) findViewById(R.id.myscroll_view);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		// 为 myScrollView 添加视图
		for (int i = 0; i < ids.size(); i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(ids.get(i));
			msv.addView(image);
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	

}
