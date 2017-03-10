/**
 *
 */
package com.ldroid.kwei.common.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends FragmentActivity {

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initPreparation();
        setContentView();
        ButterKnife.bind(this) ;
        initUI();
        initListener();
        initData();
    }

    protected abstract void initPreparation();

    protected abstract void setContentView();

    protected abstract void initUI();

    protected abstract void initListener();

    protected abstract void initData();


    public void startAnimActivity(Intent intent) {
        startActivity(intent);
    }

    public void startAnimActivity(Class<?> cla) {
        startActivity(new Intent(this, cla));
    }

}
