package com.custom.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

import com.custom.ui.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/*
 *  Touch 事件
1. 给按键设置 bt.setOnTouchListener() 和 bt.setonClickListener. 
 OnTouchListener 中会实现onTouch()方法，系统还有一个方法叫onTouchEvent()，为什么要定义两个呢？有什么区别？

肯定是先执行 OnTouchListener() ,再执行onClickListener； 且 onClick 是在 OnTouchEvent中执行的；
① Button  在onTouch里面  如果返回true，就会把事件消费掉，onClick就不会执行;
    所以要在 onTouch中要返回 false, onClick 才会执行。
② ListView 如果放上 imageView 图片的话  不会去抢焦点，如果放上Button， 就会去抢焦点。解决方法： 把Button  Enable设置为False;
  imageView 没有点击状态(比如Button可以设置为disable)，那就更没有点击事件了。那为什么imageView能设置.setonClickListener呢？
  查看源码，定论在View的 setOnClickListener()
  public void setOnClickListener(OnClickListener l) {
        if (!isClickable()) {
            setClickable(true);
        }
        getListenerInfo().mOnClickListener = l;
    }
 发现系统会强制把它的点击状态设为true.

③ 如果希望我们的 touch事件能继续向下分发，onTouch必须返回true.  要不然触发一次就没有了，就调用完Down就没有了，
up都不能执行。 
 现象： ImageView  onTouch返回false,只打印一次；返回true,则打印两次。
 Button onTouch 返回 true和false 都打印两次；但返回 true时，onclick不执行；
  那 ① 和 ③ 不是冲突了吗，请看④点。

if (li != null && li.mOnTouchListener != null && (mViewFlags & ENABLED_MASK) == ENABLED
         && li.mOnTouchListener.onTouch(this, event)) {
         return true;
}
 if (onTouchEvent(event)) {
           return true;
}
在 onTouchEvent(event) 中  Button 返回了 true; ImageView　返回了false; 

④ 为什么 button 的onTouch() 事件返回false，也还会向下执行，Up,onckick呢？
   mOnTouchListener.onTouch(this, event))返回false, 就会去执行 onTouchEvent(event),进入这个方法查看源码，
   可以发现 在 switch (event.getAction())之后 强制返回了true. 所以它还会继续分发。

问2：onTouch()与 onTouchEvent()区别？
①mOnTouchListener.onTouch(this, event) 在onTouchEvent()前面执行，如果 onTouch()执行成功的话，就返回true了，
onTouchEvent()就不执行。 
②onTouchEvent()多一个move方法。
 *  
 */
public class TouchActivity extends Activity {
	@ViewInject(R.id.share_btn)
	private Button shareBtn;
	@ViewInject(R.id.iv_test)
	private ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touch_lay);
		
		ViewUtils.inject(this);
		
		
		shareBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				LogUtils.d("Button clicked");
				
			}
		});
		
		/*
		 *  Button 的OnTouch在 OnClick前执行
		 */
		shareBtn.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				LogUtils.d("Button Touch =="+event.getAction());
				
				
				return false;
			}
		});
		
		/*
		 *  ImageView 的 Touch事件
		 */
		imageView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				LogUtils.d("ImageView  Touch =="+event.getAction());
				
				
				return false;
			}
		});
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		LogUtils.d("onTouchEvent =="+event.getAction());
		return super.onTouchEvent(event);
	}
	
	/*
	 *  任何View,触摸屏幕时第一个执行的方法
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		LogUtils.d("dispatchTouchEvent =="+ev.getAction());
		
		return super.dispatchTouchEvent(ev);
	}
	
	

}
