package com.mvvm.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.mvvm.R;

/**
 * created by chiclaim
 */
public class LoadingDialog extends ProgressDialog {
    private String tip;

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_loading);
        if (!TextUtils.isEmpty(tip)) {
            TextView tvTip = (TextView) findViewById(android.R.id.message);
            tvTip.setText(tip);
        }
        setCancelable(false);
    }

    @Override
    public void setMessage(CharSequence message) {
        tip = message.toString();
    }
}