package com.custom.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

public class RingView extends View {

	
	private int cx;		//圆环圆心的X坐标	 
	private int cy;  //圆环圆心的Y坐标

	private Paint paint;
	private float radius; //圆环的半径
	private float strokeWidth;  //线条的厚度
	
	
	public RingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initView();
	
	
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);	

		/**
		 * 绘制圆环
		 */
		canvas.drawCircle(cx, cy, radius, paint);
	}
	

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 点击，获得圆环的中心
			cx = (int) event.getX();
			cy = (int) event.getY();
			//初始化画笔
			initView();
			flushState();
			
//			invalidate(); 
			handler.sendEmptyMessage(0);
			break;
		}				
		
		return true;    // 向下分发事件
	}

	// 刷新状态
	private void flushState() {
		this.radius+=10;
		this.strokeWidth = radius/4;
		paint.setStrokeWidth(strokeWidth);
		int nextAlpha = paint.getAlpha()-20;
		
		/*
		 *  如果 Alpha 是负数，它会给加上 255
		 */		
		if(nextAlpha<=20){
			nextAlpha = 0;
		}
		paint.setAlpha(nextAlpha);
		
	}

	private void initView() {
		//初始化paint 
		paint = new Paint();
		paint.setAntiAlias(true); // 抗矩齿
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE); // FILL 实心；  STROKE  空心，画线条
		paint.setStrokeWidth(strokeWidth); //设置条线的厚度
		paint.setAlpha(255); //设置透明度 ，0--255  0代表完全透明
		
		this.radius =0;
		strokeWidth = 0; 
		
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			flushState();
			// 刷新页面 执行onDraw()方法 
			invalidate();
			if(paint.getAlpha() !=0){
				handler.sendEmptyMessageDelayed(0, 100);
			}
		};
		
	};
	
	
	
}

