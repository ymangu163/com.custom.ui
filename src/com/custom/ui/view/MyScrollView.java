package com.custom.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MyScrollView extends ViewGroup {

	private Context context;
	
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		initView();
	}
	
	
	private void initView() {
		
		detector=new GestureDetector(context, new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				
				
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				
			}
			
			/**
			 * 响应手指在屏幕上的滑动事件
			 * e1 ---- Down 时的 MotionEvent
			 * e2 ---- Move 时的 MotionEvent
			 */
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				System.out.println("distanceX::"+distanceX);
				/**
				 * 移动当前view内容 ；移动一段距离
				 * disX	 X方向移的距离		为正是，图片向左移动，为负时，图片向右移动 
				 * disY  Y方向移动的距离
				 */
				scrollBy((int) distanceX, 0);
				
				
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				
				
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {

				return false;
			}
		});
		
		
		
		
		
	}


	/**
	 * 手势识别的工具类
	 */
	private GestureDetector  detector;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 把 touch事件 分发给它
		detector.onTouchEvent(event);
		
		
		
		return true;   // 要return  true，才往下分发事件
	}
	
	
	
	
	/**
	 * 对子view进行布局，确定子view的位置
	 * changed  若为true ，说明布局发生了变化
	 * l\t\r\b\  是指当前viewgroup 在其父view中的位置 
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i); // 取得下标为I的子view
			/**
			 * 父view 会根据子view的需求，和自身的情况，来综合确定子view的位置,(确定他的大小)
			 */
			//指定子view的位置  ,  左，上，右，下，是指在viewGround坐标系中的位置
			view.layout(0+i*getWidth(), 0, getWidth()+i*getWidth(), getHeight());	
			
//			view.getWidth();  得到view的真实的大小。		
		
		}
		
		
	}
	
	
	
	

}
