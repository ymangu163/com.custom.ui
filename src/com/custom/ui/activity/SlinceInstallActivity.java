package com.custom.ui.activity;

import java.io.File;
import java.io.PrintStream;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import com.custom.ui.R;

/*
 *  安装的方式：
 *  ① 通过 Intent 调用系统的安装 
 *  ② adb 命令
 *  ③ 第三方，360，应用宝等
 *  ④ 静默安装  ,google play   pm
 */

public class SlinceInstallActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touch_lay);
		
		
		new Thread(){
			private PrintStream pStream;
			private Process process;
			
			@Override
			public void run() {
				super.run();
				
				try {
					// 申请Root权限
					process=Runtime.getRuntime().exec("su");
					pStream=new PrintStream(process.getOutputStream()); // 获得输入流
					pStream.println("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
					/*
					 *  我们工程包下的 /lib目录下，先导入 libcom.so
					 *  libcom.so 其实就是一个 普通的apk，改一下后缀名为".so",这是为了迷惑别人
					 */
					pStream.println("pm install -r  /data/data/com.custom.ui/lib/libcom.so");
					
					
				} catch (Exception e) {
					e.printStackTrace();
					pStream.close();
				}finally{
					pStream.flush();
					pStream.close();
					
				}
				
				
			}
			
			
		};
		
		
		/*
		 * . 使用 Intent 进行安装
		 */
		String file=Environment.getExternalStorageDirectory()+"/"+"a.apk";
		installApk(new File(file));
		
		
	}

	private void installApk(File file) {
		Intent intent=new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		finish();
		startActivity(intent);
		
	}
	
	
	
	
	
	
	
	

}
