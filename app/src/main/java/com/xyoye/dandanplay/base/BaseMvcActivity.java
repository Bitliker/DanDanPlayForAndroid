package com.xyoye.dandanplay.base;

import android.app.Dialog;

import com.jaeger.library.StatusBarUtil;
import com.xyoye.dandanplay.R;
import com.xyoye.dandanplay.ui.weight.dialog.BaseLoadingDialog;
import com.xyoye.dandanplay.utils.CommonUtils;

/**
 * Modified by xyoye on 2019/5/27.
 */
public abstract class BaseMvcActivity extends BaseAppCompatActivity {

    protected Dialog dialog;

    @Override
    protected int getToolbarColor() {
        return CommonUtils.getResColor(R.color.theme_color);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getToolbarColor(), 0);
    }

    public void showLoadingDialog() {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new BaseLoadingDialog(BaseMvcActivity.this);
            dialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog = null;
    }
}
