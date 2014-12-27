package com.custom.ui.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BannerPagerAdapter extends PagerAdapter {
	
	private Context context;
	private List<ImageView> list;
	
	public  BannerPagerAdapter(Context context,List<ImageView> list){
		this.context=context;
		this.list=list;
	}
	
	
	/**
	 * 获得页面的总数
	 */
	@Override
	public int getCount() {
		// ① 返回一个最大值，目的是 无限循环
		return Integer.MAX_VALUE;
		
		// ② 返回list 长度，不能循环
//		return list.size();
	}

	/**
	 * 判断 view和object的对应关系 
	 *Object  --  instantiateItem  返回的 Object
	 * View --  ViewPager中的View
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0==arg1;
	}
	
	/**
	 * . viewPager有自动销毁机制，并不是有多少个页面，就创建多少个
	 * 默认情况下，它只会预创建当前页面的下一个页面，所以只会有上一个，当前，下一个 共3个页面
	 *  划动中它会先销毁上上个页面，再预创建下一个页面
	 *  这样做是为了防止 OOM
	 **/
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// 方法一
//		ViewPager vp=(ViewPager) container;
//		View view=list.get(position);
//		vp.removeView(view);
		
		// 方法二
		container.removeView((View) object);
		object = null;
	}
	
	/**
	 * 获得相应位置上的view
	 * container  view的容器，其实就是viewpager自身
	 * position 	相应的位置
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ViewPager viewPager=(ViewPager)container;  //将ViewGroup转换成ViewPager
//		View view=list.get(position);	 //得到位置，获得list中相应位置的Item
		// 让它循环
		View view=list.get(position%list.size());	
		viewPager.addView(view);   // 将得到的View添加到ＶiewPager中
				
		return view;   //返回得到的view
	}
	
	
	
	
	

}
