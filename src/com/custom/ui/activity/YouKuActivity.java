package com.custom.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.custom.ui.R;
import com.custom.ui.utils.YoukuAnimUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * .使用系统控件组合成新的控件
 * 1、相对布局的练习，要先声明做为基准的view对象。
2、RotateAnim 动画的定义，默认的旋转圆心是从标原点，水平向右为0度，角度按顺时针方向增加。
 * *  **/

public class YouKuActivity extends Activity implements OnClickListener {
	
	@ViewInject(R.id.icon_menu)
	private ImageView icon_menu;
	@ViewInject(R.id.icon_home)
	private ImageView icon_home;
	
	@ViewInject(R.id.level1)
	private RelativeLayout level1;
	@ViewInject(R.id.level2)
	private RelativeLayout level2;
	@ViewInject(R.id.level3)
	private RelativeLayout level3;
	@ViewInject(R.id.channel1)
	private ImageView mp3Image;
	@ViewInject(R.id.channel2)
	private ImageView channel2;
	
	
	
	/**
	 * 判断 第3级菜单是否显示
	 * true 为显示
	 */
	private boolean isLevel3Show = true;
	/**
	 * 判断 第2级菜单是否显示
	 * true 为显示
	 */
	private boolean isLevel2Show = true;
	/**
	 * 判断 第1级菜单是否显示
	 * true 为显示
	 */
	private boolean isLevel1show = true;
	private Intent intent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.youku_menu);
				
		ViewUtils.inject(this);
		
		initListener();
		
		
	}



	//初始化监听
	private void initListener() {
		icon_home.setOnClickListener(this);   //为 ImageView 设置监听事件
		icon_menu.setOnClickListener(this);	
		mp3Image.setOnClickListener(this);
		channel2.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.icon_menu:   //处理 menu 图标的点击事件
		// 如果第3级菜单是显示状态，那么将其隐藏
			if(isLevel3Show){
				//隐藏 第3级菜单
				YoukuAnimUtils.startAnimOut(level3);
								
			}else{
				// 如果第3级菜单是隐藏状态，那么将其显示
				YoukuAnimUtils.startAnimIn(level3);
			}
			//更新状态值
			isLevel3Show = !isLevel3Show;
			break;
			
			
		case R.id.icon_home:  //处理 home 图标 的点击事件
			// 如果第2级菜单是显示状态，那么就隐藏，2，3级菜单
			if(isLevel2Show){  
				YoukuAnimUtils.startAnimOut(level2);
				   isLevel2Show=false;
				if(isLevel3Show){ // 如果此时，第3级菜单也显示，那也将其隐藏
					YoukuAnimUtils.startAnimOut(level3,200);
					isLevel3Show = false;
				}
				
			}else{
				// 如果第2级菜单是隐藏状态，那么就显示2级菜单
				YoukuAnimUtils.startAnimIn(level2);
				isLevel2Show=true;
				
			}
			
			break;
		
		case R.id.channel1:
			 intent = new Intent(YouKuActivity.this,TouchActivity.class);
			 startActivity(intent);
			 
			break;
		case R.id.channel2:
			 intent=new Intent(YouKuActivity.this,SlinceInstallActivity.class);
			 startActivity(intent);
			break;
			
		default:
			break;
		
		}
		
		
		
	}
	
	
	/**
	 * 响应按键的动作
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_MENU){ // 监听 menu 按键
			changeLevel1State();
		}
		
		
		
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * 改变第1级菜单的状态
	 */
	private void changeLevel1State() {
		//如果第1级菜单是显示状态，那么就隐藏 1，2，3级菜单 
		if(isLevel1show){
			YoukuAnimUtils.startAnimOut(level1);
			isLevel1show = false;
			
			if(isLevel2Show){ // 判断2级菜单是否显示
				YoukuAnimUtils.startAnimOut(level2,100);
				isLevel2Show = false;
				if(isLevel3Show){ // 判断3级菜单是否显示
					YoukuAnimUtils.startAnimOut(level3,200);
					isLevel3Show = false;
				}
			}
			
		}else{
			//如果第1级菜单是隐藏状态，那么就显示 1，2级菜单 
			YoukuAnimUtils.startAnimIn(level1);
			isLevel1show = true;
			
			YoukuAnimUtils.startAnimIn(level2,200);
			isLevel2Show = true;
			
			
		}
		
		
	}
	
	
	
	

}
