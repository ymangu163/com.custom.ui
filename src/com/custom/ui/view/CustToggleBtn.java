package com.custom.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.custom.ui.R;

/**
 * .  一个View 对象要显示在屏幕上，有几个重要步骤呢？
 * 1. 调用 构造函数 创建对象
 * 2. 测量View 的大小    ---- onMeasure(int,int)
 * 3. 确定View的位置，View自身有一些建议权，决定权在父View手中  --- onLayout()
 * 4. 绘制View 的内容  --- onDraw(Canvas)
 **/
public class CustToggleBtn extends View implements OnClickListener {
	
	
	private Bitmap backgroudBitmap;
	private Bitmap slideBtn;
	private Paint paint;
	private float slideBtn_left;   // 滑动按钮的左边界
	private boolean currState=false;  //当前开关的状态，true为开

	/**
	 * 在代码里面创建对象的时候，使用此构造方法
	 */
	public CustToggleBtn(Context context) {
		super(context);
	}
	
	/**
	 * 在布局文件中声名的view，创建时由系统自动调用。
	 * @param context	上下文对象
	 * @param attrs		属性集
	 */
	public CustToggleBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initView();
	}

	
	
	// 初始化
	private void initView() {
		/**
		 * 做为背景的图片
		 */
		backgroudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
		//可以滑动的图片
		slideBtn = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
		
		//初始化 画笔
		paint = new Paint();
		paint.setAntiAlias(true); // 打开抗矩齿
		
		// 添加 onclick事件监听
		setOnClickListener(this);   
	}

	/**
	 * 测量尺寸时的回调方法 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/**
		 * 设置当前view的大小
		 * width  :view的宽度
		 * height :view的高度   （单位：像素）
		 */
		setMeasuredDimension(backgroudBitmap.getWidth(), backgroudBitmap.getHeight());
		
	}
	
	

	//确定位置的时候调用此方法
	//自定义view的时候，作用不大
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}
	
	/**
	 * 绘制当前view的内容
	 */
	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);   //空的
		
		/*
		 * backgroundBitmap	要绘制的图片
		 * left , top	图片的左上角的位置
		 * paint 绘制图片要使用的画笔
		 */
		canvas.drawBitmap(backgroudBitmap, 0, 0, paint) ;   //绘制背景
		
		//绘制 可滑动的按钮
		/**
		 * . 左边距：
		 *  ① 初始状态  slideBtn 左边为0
		 *  ② 开的时候  slideBtn left值 为blackGround.width - slideBtn.width
		 **/
		canvas.drawBitmap(slideBtn, slideBtn_left, 0, paint);
		
		
		
		
	}
	
	
	/*
	 * 判断是否发生拖动，
	 * 如果拖动了，就不再响应 onclick 事件
	 */
	private boolean isDrag = false;
	
	/*
	 * . onTouchEvent 与 onclick 的事件冲突
	 *  ① onclick事件 其实也是 onTouchEvent 事件,onclick事件是在	super.onTouchEvent(event);中解析的
	 *   系统对onclick 事件的解析，过于简陋，只要有down 事件, up 事件，系统即认为 发生了click 事件
	 *  ② 通过定义一个 flag --isDrag 来判断是否拖动了，拖动了，就不再响应Onclick
	 */		
	@Override
	public void onClick(View v) {
		/*
		 * 如果没有拖动，才执行改变状态的动作,解决onTouchEvent 与 onclick 的事件冲突
		 */
		if(!isDrag){
			currState = !currState;    //状态flag更新
			flushState();     //刷新
			
		}
	}

	/**
	 * 刷新当前状态
	 */
	private void flushState() {
		// 根据开关状态，设置相应的位置
		if(currState){
			slideBtn_left=backgroudBitmap.getWidth()-slideBtn.getWidth();		
			
		}else{
			slideBtn_left=0;
		}
		flushView();
		
	}
	
	/**
	 * down 事件时的x值
	 */
	private int firstX;
	/**
	 * touch 事件的上一个x值
	 */
	private int lastX;
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			firstX = lastX =(int) event.getX();   //记录下按下时的x轴坐标
			isDrag = false;
			break;
		case MotionEvent.ACTION_MOVE:			
			
			//判断是否发生拖动
			if(Math.abs(event.getX()-firstX)>5){
				isDrag = true;
			}
			
			//计算 手指在屏幕上移动的距离
			int dis = (int) (event.getX() - lastX);
			//将本次的位置 设置为lastX
			lastX = (int) event.getX();
			
			//根据手指移动的距离，改变slideBtn_left 的值
			slideBtn_left = slideBtn_left+dis;
			
			break;
		case MotionEvent.ACTION_UP:			
			//在发生拖动的情况下，根据最后的位置，判断当前开关的状态; 解决拖动时停在中间的问题
			if (isDrag) {
				int maxLeft = backgroudBitmap.getWidth() - slideBtn.getWidth(); // slideBtn 左边界最大值
				/*
				 * 根据 slideBtn_left 判断，当前应是什么状态
				 */
				if (slideBtn_left > maxLeft / 2) { // 此时应为 打开的状态
					currState = true;
				} else {
					currState = false;
				}
				flushState();  //刷新状态，而不是刷新View
				
			}
			
			
			break;
		
		
		}
		
		// 刷新 视图
		
		flushView();  
		
		return true;  //返回true,表示这个事件被消费掉了
	}
	
	/*
	 * 刷新当前视图
	 *  对 slideBtn_left  的值进行判断 ，确保其在合理的位置 即       0<=slideBtn_left <=  maxLeft 
	 */
	private void flushView() {

		int maxLeft = backgroudBitmap.getWidth()-slideBtn.getWidth();	//	slideBtn 左边届最大值

		//确保 slideBtn_left >= 0
		slideBtn_left = (slideBtn_left>0)?slideBtn_left:0;
		
		//确保 slideBtn_left <=maxLeft
		slideBtn_left = (slideBtn_left<maxLeft)?slideBtn_left:maxLeft;		
		
		invalidate();  //刷新整个View 
	}
		
	
}
