package com.ldroid.kwei.common.ui.lib.pop;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 底部菜单 采用BottomSheetDialog
 * Created by Gordn on 2017/3/23.
 */
public class BottomMenuDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    public static final String TAG = "BottomMenuDialog";

    private ArrayList<String> mTitles;
    private Map<String, View.OnClickListener> mListener;
    private int mTextColor = Color.parseColor("#3BCF67");

    private LinearLayout mRootView;
    private Dialog mDialog;
    private Context mContext;

    private BottomSheetBehavior mBehavior;

    public BottomMenuDialog() {
    }

    @SuppressLint("ValidFragment")
    public BottomMenuDialog(BottomMenuBuilder builder) {
        mTitles = builder.titles;
        mListener = builder.listeners;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.5f;
        window.setAttributes(windowParams);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = new BottomSheetDialog(getContext(), getTheme());
        if (mRootView == null) {
            //缓存下来的View 当为空时才需要初始化 并缓存
            initRootView();
        }
        mDialog.setContentView(mRootView);
        mBehavior = BottomSheetBehavior.from((View) mRootView.getParent());
        ((View) mRootView.getParent()).setBackgroundColor(Color.TRANSPARENT);
        mRootView.post(new Runnable() {
            @Override
            public void run() {
                /**
                 * PeekHeight默认高度256dp 会在该高度上悬浮
                 * 设置等于view的高 就不会卡住
                 */
                mBehavior.setPeekHeight(mRootView.getHeight());
            }
        });

        return mDialog;
    }

    private void initRootView() {
        mRootView = new LinearLayout(mContext);
        mRootView.setOrientation(LinearLayout.VERTICAL);
        mRootView.setPadding(dp2px(mContext, 10), 0, dp2px(mContext, 10), dp2px(mContext, 10));

        if (mTitles == null || mTitles.size() == 0) {
            return;
        }
        if (mTitles.size() == 1) {
            View childView = initView(mTitles.get(0), 1, false, false);
            childView.setBackgroundDrawable(getDrawableListByType(true, true, true, true));
            return;
        }

        if (mTitles.size() == 2) {
            View childView = initView(mTitles.get(0), 1, false, true);
            childView.setBackgroundDrawable(getDrawableListByType(true, true, true, true));
            childView = initView(mTitles.get(1), 2, false, false);
            childView.setBackgroundDrawable(getDrawableListByType(true, true, true, true));
            return;
        }

        for (int i = 0; i < mTitles.size(); i++) {
            View childView = null;
            if (i == 0) {
                childView = initView(mTitles.get(0), i + 1, true, false);
                childView.setBackgroundDrawable(getDrawableListByType(true, true, false, false));
                continue;
            }
            if (i == (mTitles.size() - 2)) {
                childView = initView(mTitles.get(i), i + 1, false, true);
                childView.setBackgroundDrawable(getDrawableListByType(false, false, true, true));
                continue;
            }
            if (i == (mTitles.size() - 1)) {
                //false, false, true, true
                childView = initView(mTitles.get(i), i + 1, false, false);
                childView.setBackgroundDrawable(getDrawableListByType(true, true, true, true));
                continue;
            }

            childView = initView(mTitles.get(i), i + 1, true, false);
            childView.setBackgroundDrawable(getDrawableListByType(false, false, false, false));
        }

    }

    private View initView(String button, int position, boolean hasBottomLine, boolean hasBottomGap) {
        TextView childView = new TextView(mContext);
        childView.setText(button);
        childView.setTextSize(18);
        childView.setTextColor(mTextColor);
        childView.setTag(position);
        childView.setGravity(Gravity.CENTER);
        childView.setOnClickListener(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp2px(mContext, 50));
        if (hasBottomGap) params.bottomMargin = dp2px(mContext, 10);
        mRootView.addView(childView, params);

        if (hasBottomLine) {
            View line = new View(mContext);
            line.setBackgroundColor(Color.LTGRAY);
            mRootView.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        }

        return childView;
    }


    public void setTextColor(int color) {
        this.mTextColor = color;
    }

    public StateListDrawable getDrawableListByType(boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable selectDrawable = getWhitShape(5, 0xffCCCCCC, leftTop, rightTop, rightBottom, leftBottom);
        Drawable defaultDrawable = getWhitShape(5, 0xffffffff, leftTop, rightTop, rightBottom, leftBottom);

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, selectDrawable);
        stateListDrawable.addState(new int[]{}, defaultDrawable);
        return stateListDrawable;
    }

    public Drawable getWhitShape(int radius, int bgColor, boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom) {
        float r = dp2px(getContext(), radius);
        float a1 = 0, a2 = 0, a3 = 0, a4 = 0, a5 = 0, a6 = 0, a7 = 0, a8 = 0;
        if (leftTop) {
            a1 = r;
            a2 = r;
        }
        if (rightTop) {
            a3 = r;
            a4 = r;
        }

        if (rightBottom) {
            a5 = r;
            a6 = r;
        }

        if (leftBottom) {
            a7 = r;
            a8 = r;
        }

        float[] outerRadii = new float[]{a1, a2, a3, a4, a5, a6, a7, a8};
        RoundRectShape rrs = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable sd = new ShapeDrawable(rrs);
        sd.getPaint().setColor(bgColor);
        return sd;
    }

    public void show(FragmentManager manager) {
        if (!this.isAdded())
            show(manager, TAG);
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        Object o = v.getTag();
        if (o == null) {
            return;
        }
        String key = String.valueOf(o);
        if (mListener.get(key) != null) {
            mListener.get(key).onClick(v);
        }
        dismiss();
    }

    public static class BottomMenuBuilder {
        private ArrayList<String> titles;
        private Map<String, View.OnClickListener> listeners;

        public BottomMenuBuilder() {
            titles = new ArrayList<>();
            listeners = new HashMap<>();
        }

        public BottomMenuDialog build() {
            if (titles == null || titles.isEmpty()) {
                Log.e(TAG, "can not empty mTitles");
            }
            return new BottomMenuDialog(this);
        }

        public BottomMenuBuilder addItem(String title, View.OnClickListener listener) {
            titles.add(title);
            listeners.put(String.valueOf(titles.size()), listener);
            return this;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除缓存View和当前ViewGroup的关联
        ((ViewGroup) (mRootView.getParent())).removeView(mRootView);
    }

}