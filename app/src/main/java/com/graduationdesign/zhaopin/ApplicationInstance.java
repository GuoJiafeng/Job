package com.graduationdesign.zhaopin;

import java.util.ArrayList;

import com.graduationdesign.zhaopin.exception.CrashHandler;

import com.graduationdesign.zhaopin.model.ZhaoPinInfo;

import android.app.Application;
import android.content.Context;

public class ApplicationInstance extends Application {

//	private Context context;
	
	

	

	

	@Override
	public void onCreate() {
		super.onCreate();
//		context = getApplicationContext();
		CrashHandler crashHandler = CrashHandler.getInstance();
		// 注册crashHandler
		crashHandler.init(getApplicationContext());
		Thread.setDefaultUncaughtExceptionHandler(crashHandler);
	}

	
	

	private static ZhaoPinInfo zhaopinInfo;
	
	public  ZhaoPinInfo getZhaoPinInfo() {
		return zhaopinInfo;
	}

	public  void setZhaoPinInfo(ZhaoPinInfo zhaopinInfo) {
		this.zhaopinInfo = zhaopinInfo;
	}
}
