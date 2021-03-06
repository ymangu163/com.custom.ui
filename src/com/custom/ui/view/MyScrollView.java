package com.custom.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyScrollView extends ViewGroup {

	private Context context;
	private boolean isFling;
	
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		initView();
	}
	
	
	private void initView() {
//		myScroller = new MyScroller(context);
		myScroller = new Scroller(context);
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
				
				/**
				 * 将当前视图的基准点移动到某个点  坐标点
				 * x 水平方向X坐标
				 * Y 竖直方向Y坐标
				 *  scrollTo(x,  y);
				 */
				
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				
			}
			
			/**
			 * 发生快速滑动时的回调
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
					isFling = true;
					if(velocityX>0 && currId>0){ // 快速向右滑动
						currId--;
					}else if(velocityX<0 && currId<getChildCount()-1){ // 快速向左滑动
						currId++;
					}
					
					moveToDest(currId);				
				
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
	
	/**
	 * 当前View的ID值, 显示在屏幕上的子View的下标
	 */
	private int currId = 0;
	
	/**
	 * down 事件时的x坐标
	 */
	private int firstX = 0;
	
	/*
	 *  事件传递机制
	 *  ① view 执行 dispatchTouchEvent方法，开始分发事件
	 *  ② 执行 onInterceptTouchEvent 判断是否中断事件
	 *  ③ 执行 onTouchEvent，去处理事件
	 */
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		/*
		 *  解决拖动的时候，图片跳动的Bug
		 *   原因：onTouchEvent收到的第一个事件是MOve,不是Down
		 */
		
		
		// 把 touch事件 分发给它
		detector.onTouchEvent(event);
		
		//添加自己的事件解析
		
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					firstX = (int) event.getX();
					
					break;
				case MotionEvent.ACTION_MOVE:
					
					break;
				case MotionEvent.ACTION_UP:				
						if(!isFling){					
						
					
//						scrollTo(0, 0);  //恢复到（0，0）
					
					int nextId = 0;
					if(event.getX()-firstX>getWidth()/2 && currId>0){ // 手指向右滑动，超过屏幕的1/2  当前的currid - 1
						
						nextId = currId-1;
					}else if(firstX - event.getX()>getWidth()/2){ // 手指向左滑动，超过屏幕的1/2  当前的currid + 1
						nextId = currId+1;
					}else{
						nextId = currId;
					}
					// 移动到指定的页上
					moveToDest(nextId);
					
				}
					isFling=false;
					
					break;
				}
		
		
		return true;   // 要return  true，才往下分发事件
	}
	
//	private MyScroller myScroller;
	private Scroller myScroller;
	
	/**
	 * 移动到指定的屏幕上
	 * @param nextId	屏幕 的下标
	 */
	public  void moveToDest(int nextId) {
		/*
		 * 对 nextId 进行判断 ，确保 是在合理的范围  
		 * 即  nextId >=0  && next <=getChildCount()-1
		 */
		currId = (nextId>=0)?nextId:0;
		currId = (nextId<=getChildCount()-1)?nextId:(getChildCount()-1);
		
		//瞬间移动
//		scrollTo(currId*getWidth(), 0);
		
		//触发listener事件
				if(pageChangedListener!=null){
					pageChangedListener.moveToDest(currId);
				}
		
		
		int distance = currId*getWidth() - getScrollX(); // 最终的位置 - 现在的位置  = 要移动的距离
		
		myScroller.startScroll(getScrollX(),0,distance,0,Math.abs(distance));
		
		
		/*
		 * 刷新当前view ； 导致 onDraw()方法 的执行
		 */
		invalidate();
			
	}

	@Override
	/**
	 * invalidate();  会导致  computeScroll（）这个方法的执行
	 */
	public void computeScroll() {
		
		if(myScroller.computeScrollOffset()){
			int newX = (int) myScroller.getCurrX();
			System.out.println("newX::"+newX);
			
			scrollTo(newX, 0);
			
			invalidate();
		};
		
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
	
	/**
	 * 做为viewGroup 还有一个责任，，：计算 子view的大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		// int --> 32位，后30位是父View能给你的大小 ，前两位是模式
		int size = MeasureSpec.getSize(widthMeasureSpec);
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			v.measure(widthMeasureSpec, heightMeasureSpec);			
			
//			v.getMeasuredWidth() // 得到测量的大小
		}
			
		
	}
	
	/*
	 *  是否中断事件的传递
	 *  return false  不中断
	 *  return true 中断事件，执行自己的onTouch方法
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean result=false;
			
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			
			/*
			 *  解决拖动的时候，图片跳动的Bug
			 *   原因：onTouchEvent收到的第一个事件是MOve,不是Down
			 */
			detector.onTouchEvent(ev);
			
			// 得到的是相对于自身左上角的值
			firstX=(int)ev.getX();
			firstY = (int)ev.getY();
			
			break;
		case MotionEvent.ACTION_MOVE:
			// 手指在屏幕上水平移动的绝对值
			int disx=(int)Math.abs(ev.getX()-firstX);
			// 手指在屏幕上垂直移动的绝对值
			int disy=(int)Math.abs(ev.getY()-firstY);
			
			if(disx>disy && disx>10){
				result=true;
				
			}else{
				result=false;
			}
			
			
			break;
		case MotionEvent.ACTION_UP:
			
			break;
		
		}
		
		
		return result;
	}
	
	
	
	
	/**
	 * 页面更改时的监听接口
	 */
	public interface MyPageChangedListener{
		void moveToDest(int currid);
	}
	
	// 定义一个接口对象
	private MyPageChangedListener  pageChangedListener;
	private int firstY;

	public MyPageChangedListener getPageChangedListener() {
		return pageChangedListener;
	}

	public void setPageChangedListener(MyPageChangedListener pageChangedListener) {
		this.pageChangedListener = pageChangedListener;
	}
	
	
	
	

}
