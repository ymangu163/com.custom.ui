package com.custom.ui.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.custom.ui.R;
import com.custom.ui.adapter.BannerPagerAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * .  ①  在使用第3方jar包时 Property 中 Java Build path 的 Order and Export中的jar包要勾上
 *    
 **/

public class BannerActivity extends Activity {
	@ViewInject(R.id.viewpager)
	private ViewPager viewPager;
	@ViewInject(R.id.point_group)
	private LinearLayout pointGroup;  //动态添加点的布局
	 @ViewInject(R.id.image_desc)
	 private TextView imageDesc;
	 
		// 图片资源ID
		private final int[] imageIds = { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e };
		
		private ArrayList<ImageView> imageList;   //viewPager的数据源
		private BannerPagerAdapter bannerAdapter;
		
	    //图片标题集合
		private final String[] imageDescriptions = {
				"巩俐不低俗，我就不能低俗",
				"扑树又回来啦！再唱经典老歌引万人大合唱",
				"揭秘北京电影如何升级",
				"乐视网TV版大派送",
				"热血屌丝的反杀"
		};
	
		/**
		 * 上一个页面的位置
		 */
		protected int lastPosition=0;
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.banners_lay);
		
		ViewUtils.inject(this);
		imageList = new ArrayList<ImageView>();
		initData();	 //初始化数据源
		
		bannerAdapter = new BannerPagerAdapter(this,imageList);
		viewPager.setAdapter(bannerAdapter);  //为ViewPager 设置Adapter.
		
		// 设置 ViewPager 中View 改变时的监听 
		viewPager.setOnPageChangeListener(pageChangeListener);
		
		// 让 ViewPager 实现双向循环
		viewPagerRecycle();
		
		/**
		 * . 自动循环的方法：
		 * 1. 定时器：Timer
		 * 2. 开子线程 while  true 循环
		 * 3. ClockManager
		 * 4. 用 Handler 发送延时循环，实现循环 (推荐)		 *
		 **/
		
		isRunning=true;
		handler.sendEmptyMessageDelayed(0, 2000);
		
		
	}
	
	private boolean isRunning =false;   //定义一个flag ，让这个循环退出
	
	// 使用Handler 实现循环
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(viewPager.getCurrentItem()+1);  //让ViewPager 跳到下一个页面
			if(isRunning){
				
				handler.sendEmptyMessageDelayed(0, 2000);
			}
			
		};
	};
	
	
	protected void onDestroy() {
		super.onDestroy();
		isRunning=false;    // Activity 销毁时，就不循环了
		
	};
	
	
	// 让 ViewPager 实现双向循环
	private void viewPagerRecycle() {
		// 让它直接跳到中间，就可以左右划 双向循环了;并让它显示第一张图
		viewPager.setCurrentItem(Integer.MAX_VALUE/2 -(Integer.MAX_VALUE/2)%imageList.size());
	   		
	}

	//初始化ViewPager 的数据源
	private void initData() {
		imageDesc.setText(imageDescriptions[0]);
		
		for (int i = 0; i <imageIds.length; i++) {
			//初始化图片资源
			ImageView image = new ImageView(this);
			image.setBackgroundResource(imageIds[i]); 
			imageList.add(image);      //添加到 List 中
			
			// 添加指示点,  通过 drawable 定义出来
			ImageView point =new ImageView(this);
			
			//设置参数，点与点之间的margin值，不设置会 靠在一起
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			params.rightMargin = 20;    //设置margin参数
			point.setLayoutParams(params);
			
			// 在 point_bg.xml 中设置 enable 等的背景
			point.setBackgroundResource(R.drawable.point_bg);
			if(i==0){   // 开始 第一个为选中
				point.setEnabled(true);    //改变背景
			}else{
				point.setEnabled(false);
			}
			
			// 把点添加进去
			pointGroup.addView(point);
			
		}			
	}
	
	// ViewPager 改变 时的监听对象
	private  OnPageChangeListener pageChangeListener=new  OnPageChangeListener(){

		/**
		 * 页面正在滑动的时候，回调
		 */
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {			
		}
		
		/**
		 * 页面切换后调用 
		 * position  新的页面位置
		 */
		@Override
		public void onPageSelected(int position) {
			position=position%imageList.size();   //更新它的值，不要让它超出范围
			
			imageDesc.setText(imageDescriptions[position]);  // 更改下面 TextView 描述的文字
			
			/**
			 * .  改变指示点的状态,把当前点 enable 设为true;
			 *   把上一个点设置为 false.
			 **/
			pointGroup.getChildAt(position).setEnabled(true);
			pointGroup.getChildAt(lastPosition).setEnabled(false);
			lastPosition=position;      //将当前位置记录为上一个
					
			
		}
		
		/**
		 * 当页面状态发生变化的时候，回调
		 */
		@Override
		public void onPageScrollStateChanged(int state) {
			
		}		
	};	
	
}
