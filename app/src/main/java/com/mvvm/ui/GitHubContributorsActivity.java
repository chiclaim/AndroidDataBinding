package com.mvvm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mvvm.R;
import com.mvvm.adapter.BaseAdapter;
import com.mvvm.adapter.ContributorAdapter;
import com.mvvm.exception.AccessDenyException;
import com.mvvm.http.ApiServiceFactory;
import com.mvvm.http.GitHubApi;
import com.mvvm.http.NetErrorType;
import com.mvvm.model.AuthToken;
import com.mvvm.model.Contributor;
import com.mvvm.utils.DividerItemDecoration;
import com.mvvm.utils.RecyclerViewUtils;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * RxJava+Retrofit1.9, Authorization by token  <br/><br/>
 * <B>Created by chiclaim on 2016/02/18</B>
 */

public class GitHubContributorsActivity extends BaseActivity {

    private GitHubApi gitHubApi = ApiServiceFactory.createService(GitHubApi.class);

    public RecyclerView rvContent;
    public TextView tvTip;
    public BaseAdapter<Contributor> adapter;
    private boolean loading;


    private RecyclerView.OnScrollListener scrollListener;


    public static void launch(Context context) {
        Intent intent = new Intent(context, GitHubContributorsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_contributors);
        initViews();

        showLoadingDialog();
        requestContributes();
    }

    private void initViews() {

        tvTip = (TextView) findViewById(R.id.tv_tips);
        rvContent = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new ContributorAdapter(this);
        RecyclerViewUtils.setLinearManagerAndAdapter(rvContent, adapter);
        rvContent.addItemDecoration(DividerItemDecoration.newVertical(this,
                R.dimen.list_divider_height, R.color.divider_color));
        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //check for scroll down
                if (adapter.getModelSize() == 0) {
                    return;
                }
                if (dy > 0) {
//                    LinearLayoutManager mLayoutManager = (LinearLayoutManager)
//                            rvContent.getLayoutManager();
//                    int visibleItemCount = mLayoutManager.getChildCount();
//                    int totalItemCount = mLayoutManager.getItemCount();
//                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
//                    if (!isLast && !loading) {
//                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//                            loading = true;
//                            adapter.showFooter();
//                            requestContributes();
//                        }
//                    }
                }
            }
        };

        rvContent.addOnScrollListener(scrollListener);
    }

    public Observable<AuthToken> refreshToken() {
        return Observable.create(new Observable.OnSubscribe<AuthToken>() {
            @Override
            public void call(Subscriber<? super AuthToken> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        observer.onNext(gitHubApi.refreshToken());
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    private <T> Func1<Throwable, ? extends Observable<? extends T>> refreshTokenAndRetry(final Observable<T> toBeResumed) {
        return new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                throwable.printStackTrace();
                // Here check if the error thrown really is a 401
                if (isHttp401Error(throwable)) {
                    return refreshToken().flatMap(new Func1<AuthToken, Observable<? extends T>>() {
                        @Override
                        public Observable<? extends T> call(AuthToken token) {
                            return toBeResumed;
                        }
                    });
                }
                // re-throw this error because it's not recoverable from here
                return Observable.error(throwable);
            }

            public boolean isHttp401Error(Throwable throwable) {
                return throwable instanceof AccessDenyException;
            }

        };
    }

    private void requestContributes() {
        Observable<List<Contributor>> observable = gitHubApi.contributors();
        observable.onErrorResumeNext(refreshTokenAndRetry(observable))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Contributor>>() {
                    @Override
                    public void onCompleted() {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable t) {
                        hideLoadingDialog();
                        t.printStackTrace();
                        loading = false;
                        tvTip.setText(t.getClass().getName() + "\n" + t.getMessage());

                        NetErrorType.ErrorType error = NetErrorType.getErrorType(t);
                        tvTip.append("\n");
                        tvTip.append(error.msg);
                    }

                    public void onNext(List<Contributor> response) {
                        adapter.appendItems(response);
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rvContent.removeOnScrollListener(scrollListener);
    }
}