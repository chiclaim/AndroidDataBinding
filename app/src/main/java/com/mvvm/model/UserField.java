package com.mvvm.model;

import android.databinding.ObservableField;

/**
 * Created by chiclaim on 2016/02/18
 */
public class UserField {

    public final ObservableField<String> realName = new ObservableField<>();
    public final ObservableField<String> mobile = new ObservableField<>();

}
