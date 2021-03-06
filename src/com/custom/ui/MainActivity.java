package com.custom.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

import com.custom.ui.activity.AttrActivity;
import com.custom.ui.activity.BannerActivity;
import com.custom.ui.activity.CustomVGroupActivity;
import com.custom.ui.activity.EmpireActivity;
import com.custom.ui.activity.JDActivity;
import com.custom.ui.activity.LinearListActivity;
import com.custom.ui.activity.RingActivity;
import com.custom.ui.activity.SpinnerActivity;
import com.custom.ui.activity.ToggleBtnActivity;
import com.custom.ui.activity.YouKuActivity;
import com.custom.ui.utils.ActivityUtil;
import com.custom.ui.view.HKText;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends Activity implements OnClickListener {
	
	@ViewInject(R.id.one)
	private Button btn_one;
	@ViewInject(R.id.two)
	private Button btn_two;
	@ViewInject(R.id.three)
	private Button btn_three;
	@ViewInject(R.id.four)
	private Button btn_four;
	@ViewInject(R.id.five)
	private Button btn_five;
	@ViewInject(R.id.six)
	private Button btn_six;
	@ViewInject(R.id.seven)
	private Button btn_seven;
	@ViewInject(R.id.eight)
	private Button btn_eight;
	@ViewInject(R.id.nine)
	private Button btn_nine;
	@ViewInject(R.id.zero)
	private Button btn_zero;
	@ViewInject(R.id.devide)
	private Button btn_devide;
	@ViewInject(R.id.point)
	private Button btn_point;
	@ViewInject(R.id.plus)
	private Button btn_plus;
	@ViewInject(R.id.equal)
	private Button btn_equal;
	@ViewInject(R.id.minus)
	private Button btn_minus;
	@ViewInject(R.id.multiply)
	private Button btn_multiply;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_layout);
		
		ViewUtils.inject(this);  // 注入View
		
		btn_one.setOnClickListener(this);
		btn_two.setOnClickListener(this);
		btn_three.setOnClickListener(this);
		btn_four.setOnClickListener(this);
		btn_five.setOnClickListener(this);
		btn_six.setOnClickListener(this);
		btn_seven.setOnClickListener(this);
		btn_eight.setOnClickListener(this);
		btn_nine.setOnClickListener(this);
		btn_zero.setOnClickListener(this);
		btn_plus.setOnClickListener(this);
		btn_minus.setOnClickListener(this);
		btn_devide.setOnClickListener(this);
		btn_equal.setOnClickListener(this);
		btn_multiply.setOnClickListener(this);
		btn_point.setOnClickListener(this);
		
		
	}


	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch(v.getId()){
		case R.id.one:
			// 通过Intent 切换 Activity
			intent.setClass(this, JDActivity.class);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			startActivity(intent);
			break;
			
		case R.id.two:
			ActivityUtil.goToActivityFromBottom2Top(MainActivity.this, YouKuActivity.class);
			break;
		case R.id.three:
			ActivityUtil.goToActivity(MainActivity.this, BannerActivity.class);
			
			break;
		case R.id.four:
			ActivityUtil.goToActivity(MainActivity.this, ToggleBtnActivity.class);		
		
			break;
		case R.id.five:
			ActivityUtil.goToActivity(MainActivity.this, CustomVGroupActivity.class);		
			
			break;
		case R.id.six:
			ActivityUtil.goToActivity(MainActivity.this, RingActivity.class);	
			break;
		case R.id.seven:
			ActivityUtil.goToActivity(MainActivity.this, EmpireActivity.class);	
			break;
		case R.id.eight:
			ActivityUtil.goToActivity(MainActivity.this, LinearListActivity.class);	
			
			break;
		case R.id.nine:
			
			break;
		case R.id.zero:
			
			break;
		case R.id.devide:
			ActivityUtil.goToActivity(MainActivity.this, SpinnerActivity.class);
			break;
		case R.id.multiply:
			
			break;
		case R.id.plus:
			
			break;
		case R.id.minus:
			
			break;
		case R.id.point:
			
			break;
		case R.id.equal:
			
			break;
		
		
		
		
		
		}
		
		
		
		
		
		
	}
}
