package com.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.mvvm.BR;

import java.io.Serializable;

/**
 * Created by chiclaim on 2016/02/18
 */
public class User extends BaseObservable{

    private String userName;
    private String realName;
    private String mobile;
    private int age;

    /**
     * 注意: 在BR里对应的常量为follow
     */
    private boolean isFollow;


    public User(String realName, String mobile) {
        this.realName = realName;
        this.mobile = mobile;
        Log.d("User", "user construct invoked");
    }

    public User() {
    }

    @Bindable
    public boolean isFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
        notifyPropertyChanged(BR.follow);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
        notifyPropertyChanged(BR.realName);
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }
}
