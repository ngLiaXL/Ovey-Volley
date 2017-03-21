package com.ldroid.kwei.module.main;

import android.content.Context;

import com.ldroid.kwei.R;
import com.ldroid.kwei.common.trace.Timber;
import com.ldroid.kwei.common.ui.BaseActivity;

import butterknife.OnClick;


public class MainActivity extends BaseActivity implements  MainContract.View {


	private MainPresenter mPresenter;

	@Override
	protected void initPreparation() {
		mPresenter = new MainPresenter(this) ;
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.ac_main);
	}

	@Override
	protected void initUI() {
	}

	@Override
	protected void initListener() {

	}

	@Override
	protected void initData() {

	}


	@OnClick(R.id.send_http)
	public void onClickWeather(){
		Timber.tag("MainAc");
		Timber.d("aaaaaaaaaaaabbbbbbbbbcccccccc");
	}

	@Override
	public void onRespLogin() {

	}

	@Override
	public Context getContext() {
		return null;
	}

	@Override
	public void showLoading(String msg) {

	}

	@Override
	public void dismissLoading() {

	}

	@Override
	public void onError(String msg) {

	}
}
