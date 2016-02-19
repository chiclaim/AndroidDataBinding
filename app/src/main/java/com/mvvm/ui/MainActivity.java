package com.mvvm.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.mvvm.R;

/**
 * 1, 用户修改用户名 , 如果多界面都使用了用户名,则需要在使用的界面同步更新.
 * 2, 界面列表使用dataBinding
 */
public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    public void simpleSample(View view) {
        launchActivity(DataBindSimpleActivity.class);
    }

    public void observableSample(View view) {
        UpdateUserActivity.launch(this);
    }

    public void dataBindingList(View view) {
        GitHubContributorsActivity.launch(this);
    }

    public void elSample(View view) {
        ELActivity.launch(this);
    }


}
