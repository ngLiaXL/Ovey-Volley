package com.ldroid.kwei.common.net;

import android.content.res.Resources;

import com.ldroid.kwei.MainApp;
import com.ldroid.kwei.R;

public class AppAssembly {

	private AppAssembly() {
	}

	public static Resources getResources() {
		return MainApp.getContext().getResources();
	}


	public static String getUrl() {
		return null ;
	}

}
