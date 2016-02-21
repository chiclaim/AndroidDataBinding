package com.mvvm.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import com.mvvm.R;
import com.mvvm.databinding.ActivityElBinding;
import com.mvvm.event.UserFollowEvent;
import com.mvvm.model.User;

/**
 * Created by chiclaim on 2016/02/19
 */
public class ELActivity extends BaseActivity implements UserFollowEvent{

    //Null Coalescing Operator
    //Collections
    //String Literals
    //Resources

    ActivityElBinding binding;

    public User user;

    public int index;

    public SparseArray<String> sparseArray = new SparseArray<>();


    public static void launch(Context context) {
        Intent intent = new Intent(context, ELActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_el);
        user = new User();
        user.setUserName("Johnny");
        user.setRealName(null);
        binding.setUser(user);

        sparseArray.put(0, "one");
        sparseArray.put(1, "two");
        sparseArray.put(2, "three");

        binding.setIndex(index);
        binding.setSparse(sparseArray);

        binding.setEvent(this);
    }


    public void collectionSample(View view) {
        if (index >= sparseArray.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        binding.setIndex(index);
        binding.setSparse(sparseArray);
    }

    public void coalescingSample(View view) {
        if (user.getRealName() != null) {
            user.setRealName(null);
            user.setUserName("Johnny");
        } else {
            user.setRealName("张三");
            user.setUserName(null);
        }
    }

    @Override
    public void follow(View view) {
        user.setIsFollow(true);
    }

    @Override
    public void unFollow(View view) {
        user.setIsFollow(false);
    }
}
