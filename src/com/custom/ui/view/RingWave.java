package com.custom.ui.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

// 水波纹效果
public class RingWave extends View {

	/**
	 * 二个相临波浪中心点的最小距离
	 */
	private static final int DIS_SOLP = 13;
	protected boolean isRunning = false;
	private ArrayList<Wave> wList;
	
	public RingWave(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		wList = new ArrayList<RingWave.Wave>();
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			int x = (int) event.getX();   //得到圆心
			int y = (int) event.getY();
				
			addPoint(x,y);  //把圆心保存起来			
			break;		
		}	
		
		return true;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		
		for (int i = 0; i < wList.size(); i++) {
			Wave wave = wList.get(i);
			canvas.drawCircle(wave.cx, wave.cy, wave.radius, wave.p);
		}		
		
	}
	
	
	// 添加新的波浪中心点
	private void addPoint(int x, int y) {
		if(wList.size() == 0){
			addPoint2List(x,y);
			/*
			 * 第一次启动动画
			 */
			isRunning = true;
			handler.sendEmptyMessage(0);
		}else{
			// 如果不是第一个，比较与上一个的距离，大小就不画了
			Wave w = wList.get(wList.size()-1);
			
			if(Math.abs(w.cx - x)>DIS_SOLP || Math.abs(w.cy-y)>DIS_SOLP){
				addPoint2List(x,y);
			}
			
		};
		
		
	}




	private int [] colors = new int[]{Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN};
    // 添加新的波浪
	private void addPoint2List(int x, int y) {
		
		Wave w = new Wave();
		w.cx = x;
		w.cy=y;
		Paint pa=new Paint();
		pa.setColor(colors[(int)(Math.random()*4)]);
		pa.setAntiAlias(true);
		pa.setStyle(Style.STROKE);

		w.p = pa;
		
		wList.add(w);   //把要画的波浪保存下来		
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {

			//刷新数据
			flushData();
			//刷新页面
			invalidate();
			//循环动画
			if (isRunning) {
				handler.sendEmptyMessageDelayed(0, 50);
			}

		};
	};

	protected void flushData() {
		for (int i = 0; i < wList.size(); i++) {
			Wave w = wList.get(i);
			//如果透明度为 0 从集合中删除
			int alpha = w.p.getAlpha();
			if(alpha == 0){
				/*
				 * .删除i 以后，i的值应该再减1 否则会漏掉一个对象，不过，在此处影响不大
				，效果上看不出来。
				 */
				wList.remove(i);	
				continue;
			}
			alpha-=5;
			if(alpha<5){
				alpha =0;
			}
			//降低透明度
			w.p.setAlpha(alpha);
			
			//扩大半径
			w.radius = w.radius+3;
			//设置半径厚度
			w.p.setStrokeWidth(w.radius/3);			
			
		}
		
		/*
		 * 如果集合被清空，就停止刷新动画
		 */
		if(wList.size() == 0){
			isRunning = false;
		}
		
		
	}
	

	/**
	 * 定义一个波浪
	 */
	private class Wave {
		//圆心
		int cx;
		int cy;
		
		//画笔
		Paint p;
		//半径
		int radius;
	}
}
