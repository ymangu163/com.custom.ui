package com.custom.ui.activity;

import android.app.Activity;

/*
 * .为新控件添加自定义的属性 主要步骤：
 * 1、在attrs.xml文件中声明属性，有属性名：name和格式：format 。如：
    <declare-styleable name="MyToggleBtn">  
        <attr name="current_state" format="boolean"/>  
    </declare-styleable> 
2、在布局文件中使用新属性，使用之前必须先声明命名空间,如：xmlns:heima="http://schemas.android.com/apk/res/com.itheima.mytogglebtn"
3、在自定义view的构造方法当中，通过解析 AttributeSet 对象，获得所需要的属性值。
 */

public class AttrActivity extends Activity {
	
	
	
	
	
	

}
