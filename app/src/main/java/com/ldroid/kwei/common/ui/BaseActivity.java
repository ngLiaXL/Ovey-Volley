/**
 *
 */
package com.ldroid.kwei.common.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.ldroid.kwei.R;

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

    public void setFloating(android.support.v7.widget.Toolbar toolbar, @StringRes int details) {
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.height = getResources().getDimensionPixelSize(R.dimen.floating_height);
            params.width = getResources().getDimensionPixelSize(R.dimen.floating_width);
            params.alpha = 1.0f;
            params.dimAmount = 0.6f;
            params.flags |= 2;
            getWindow().setAttributes(params);

            if (details != 0) {
                toolbar.setTitle(details);
            }
            //toolbar.setNavigationIcon(R.drawable.ic_close);
            setFinishOnTouchOutside(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

}
