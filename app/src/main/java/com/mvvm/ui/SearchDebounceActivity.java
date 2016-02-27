package com.mvvm.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.mvvm.R;
import com.mvvm.adapter.SearchAdapter;
import com.mvvm.databinding.SearchDebounceBinding;
import com.mvvm.http.ApiServiceFactory;
import com.mvvm.http.SearchApi;
import com.mvvm.utils.DividerItemDecoration;
import com.mvvm.utils.RecyclerViewUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by chiclaim on 2016/02/26
 */
public class SearchDebounceActivity extends BaseActivity {

    private SearchApi searchApi = ApiServiceFactory.createService(SearchApi.class);

    private SearchAdapter adapter;

    private Subscription subscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchDebounceBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_search_debounce);

        adapter = new SearchAdapter(this);
        RecyclerViewUtils.setLinearManagerAndAdapter(binding.recyclerView, adapter);
        binding.recyclerView.addItemDecoration(DividerItemDecoration.newVertical(this,
                R.dimen.list_divider_height, R.color.divider_color));

        //===========================@TODO
        //1,避免EditText没改变一次就请求一次.
        //2,避免频繁的请求,多个导致结果顺序错乱,最终的结果也就有问题.

        // 但是对于第二个问题,也不能彻底的解决. 比如停止输入400毫秒后,
        // 那么肯定会开始请求Search接口, 但是用户又会输入新的关键字,
        // 这个时候上个请求还没有返回, 新的请求又去请求Search接口.
        // 这个时候有可能最后的一个请求返回, 第一个请求最后返回,导致搜索结果不是想要的.
        //===========================@TODO

        subscription = RxTextView.textChangeEvents(binding.inputSearch)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSearchObserver());
    }


    private Observer<TextViewTextChangeEvent> getSearchObserver() {
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {
                Log.d("getSearchObserver", "--------- onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("getSearchObserver", e.getMessage());
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                String key = onTextChangeEvent.text().toString();
                Log.d("getSearchObserver", "--------- onNext:" + key);
                if (TextUtils.isEmpty(key)) {
                    adapter.removeAll();
                    return;
                }
                searchApi.search(key)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<List<String>>() {
                            @Override
                            public void call(List<String> o) {
                                adapter.removeAll();
                                adapter.appendItems(o);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable o) {
                                o.printStackTrace();
                            }
                        });
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
