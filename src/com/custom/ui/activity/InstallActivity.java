package com.custom.ui.activity;

import java.io.File;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import com.custom.ui.R;

/*
 *  分析 系统的PackageInstallerActivity，路过显示界面的两个Acitity,
 *  实际静默安装 ---  这是用源码实现的，不需要root权限，但要编译实现
 */

public class InstallActivity extends Activity {
	private final int INSTALL_COMPLETE=1;
	final static int SUCCESS=1;
	final static int FAILED=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.install_lay);
		
		String file=Environment.getExternalStorageDirectory()+"/"+"UV.apk";
		Uri uri=Uri.fromFile(new File(file));
		
		int installFlags=0;   //强制安装
		PackageManager pm=getPackageManager();
		try {
			PackageInfo pi=pm.getPackageInfo("com.example.slidingmenudemo",
					PackageManager.GET_UNINSTALLED_PACKAGES);
			if(pi!=null){
//				installFlags|=PackageManager.INSTALL_REPLACE_EXISTING;
			}
			
			
		} catch (NameNotFoundException e) {
		}
//		PackageInstallObserver observer=new PackageInstallObserver();
//		pm.installPakage(uri,observer,installFlags,"com.example.slidingmenudemo");
	}
	
	

}
