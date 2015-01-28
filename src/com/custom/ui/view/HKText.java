package com.custom.ui.view;

import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class HKText extends View {

	private char[] counts = new char[]{'A','B','C','D','E','F','G','H','J','K','L','M','N','O','P','Q'};
	private Paint paint;
	private Context ctx;	
	
	public HKText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = context;
		
		initView();
	}
	
	private int textSize = 25;  //像素值
	private void initView() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLUE);
		paint.setTextSize(textSize);//PX值
		paint.setTextAlign(Align.LEFT);
		
		paint.setStyle(Style.FILL);		
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		LogUtils.d("getWidth()::"+getWidth());
		LogUtils.d("getHeight()::"+getHeight());
		
		textSize = getWidth()/5;
	}
	
	public float left = 150;
	public float left_bottom = 200;
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg){
			
			invalidate();   // 又会触发onDraw()方法，所以能够循环
			
		}
		
	};
	
	private int seed = 0;	
	private int stepCount = 11;
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		left = 10;
		left_bottom = 400;
		for (int i = 0; i < 20; i++) {
			int seed_tem = seed;
			int alpha = 255 - (i + seed_tem) * 25;
			paint.setAlpha(alpha);// 0是没有
			canvas.drawText(counts, i % counts.length, 1, left, left_bottom,paint);
			left_bottom = (float) (left_bottom - textSize * 0.6);
			
		}
		
		seed = (seed + 1) % stepCount;
		handler.sendEmptyMessageDelayed(seed, 500);		
		
	}
	
	
	

}
