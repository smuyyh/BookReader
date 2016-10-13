package com.justwayward.reader.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.support.RefreshCollectionListEvent;
import com.justwayward.reader.manager.SettingManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xiaoshu on 2016/10/9.
 * 性别选择弹窗 用户没有选择性别则进入app提示
 */
public class GenderPopupWindow extends PopupWindow {
    private View mContentView;
    private Activity mActivity;

    private Button mBtnMale;
    private Button mBtnFemale;
    private ImageView mIvClose;

    public GenderPopupWindow(Activity activity) {
        mActivity = activity;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mContentView = LayoutInflater.from(activity).inflate(R.layout.layout_gender_popup_window, null);
        setContentView(mContentView);
        setFocusable(true);
        setOutsideTouchable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        setAnimationStyle(R.style.LoginPopup);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                lighton();
                EventBus.getDefault().post(new RefreshCollectionListEvent());
            }
        });
        mBtnMale = (Button) mContentView.findViewById(R.id.mBtnMale);
        mBtnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingManager.getInstance().saveUserChooseSex(Constant.Gender.MALE);
                dismiss();
            }
        });
        mBtnFemale = (Button) mContentView.findViewById(R.id.mBtnFemale);
        mBtnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingManager.getInstance().saveUserChooseSex(Constant.Gender.FEMALE);
                dismiss();
            }
        });
        mIvClose = (ImageView) mContentView.findViewById(R.id.mIvClose);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void lighton() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        mActivity.getWindow().setAttributes(lp);
    }

    private void lightoff() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        lightoff();
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        lightoff();
        super.showAtLocation(parent, gravity, x, y);
    }
}
