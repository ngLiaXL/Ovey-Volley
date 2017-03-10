/**
 * 
 */
package com.ldroid.kwei;

import android.app.Application;
import android.content.Context;

import com.ldroid.kwei.common.trace.Timber;

public class MainApp extends Application {

	private static Context sCtx;

	@Override
	public void onCreate() {
		super.onCreate();
		sCtx = getApplicationContext();

		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
	}

	public static Context getContext() {
		return sCtx;
	}

}
