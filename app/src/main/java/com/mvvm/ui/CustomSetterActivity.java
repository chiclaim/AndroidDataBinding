package com.mvvm.ui;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvvm.R;
import com.mvvm.databinding.CustomSetterBinding;
import com.mvvm.model.User;
import com.squareup.picasso.Picasso;

/**
 *
 *
 * Created by chiclaim on 2016/02/23
 */
public class CustomSetterActivity extends BaseActivity {

    //private User user;
    private CustomSetterBinding binding;
    private String errorAvatar = "http error";
    private String avatar1 = "https://avatars.githubusercontent.com/u/133019?v=3";
    private String avatar2 = "https://avatars.githubusercontent.com/u/18877?v=3";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_setter);
        User user = new User();
        binding.setLeftPadding(20);
        binding.setAvatar(avatar1);
        binding.setErrorAvatar("error url");
    }

    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int padding) {
        Log.d("BindingAdapter", "setPaddingLeft(View view, int padding)");
        view.setPadding(padding,
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    // @dimen/padding value is a float
    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, float padding) {
        Log.d("BindingAdapter", "setPaddingLeft(View view, float padding)");
        view.setPadding((int) padding,
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int oldPadding, int newPadding) {
        Log.d("BindingAdapter", "setPaddingLeft(View view, int oldPadding, int newPadding)");
        if (oldPadding != newPadding) {
            view.setPadding(newPadding,
                    view.getPaddingTop(),
                    view.getPaddingRight(),
                    view.getPaddingBottom());
        }
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Log.d("BindingAdapter", "loadImage(ImageView view, String url)");
        Log.d("BindingAdapter", url + "");
        Picasso.with(view.getContext()).load(url).into(view);
    }

    /**
     * 在自定义setter方法,通过binding设置属性的时候,都会有oldValue和newValue,
     * 如果需要用到oldValue的时候,可以使用类似签名的函数
     *
     * @param view
     * @param url
     * @param newUrl
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url, String newUrl) {
        Log.d("BindingAdapter", "loadImage(ImageView view, String url,String newUrl)");
        Log.d("BindingAdapter", "oldUrl" + url);
        Log.d("BindingAdapter", "newUrl" + newUrl);
        Picasso.with(view.getContext()).load(newUrl).into(view);
    }

    @BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Log.d("BindingAdapter", "loadImage(ImageView view, String url,Drawable error)");
        Picasso.with(view.getContext()).load(url).error(error).into(view);
    }

    @BindingAdapter("spanText")
    public static void setText(TextView textView, String value) {
        Log.d("BindingAdapter", "setText(TextView textView,String value)");
        SpannableString styledText = new SpannableString(value);
        styledText.setSpan(new TextAppearanceSpan(textView.getContext(), R.style.style0),
                0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(textView.getContext(), R.style.style1),
                5, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(textView.getContext(), R.style.style0),
                12, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    public void setLeftPadding(View view) {
        binding.setLeftPadding(binding.getLeftPadding() == 20 ? 40 : 20);
    }


    public void loadOtherImage(View view) {
        //test old value and new value
        if (binding.getAvatar().equals(avatar1)) {
            binding.setAvatar(avatar2);
        } else {
            binding.setAvatar(avatar1);
        }
    }

    public void loadRightImage(View view) {
        if (errorAvatar.equals(binding.getErrorAvatar())) {
            binding.setErrorAvatar(avatar1);
        } else {
            binding.setErrorAvatar(errorAvatar);
        }
    }

}
