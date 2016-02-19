package com.mvvm.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.mvvm.R;
import com.mvvm.adapter.BaseAdapter;
import com.mvvm.adapter.ContributorAdapter;
import com.mvvm.http.ApiServiceFactory;
import com.mvvm.http.GitHubApi;
import com.mvvm.model.Contributor;
import com.mvvm.utils.DividerItemDecoration;
import com.mvvm.utils.RecyclerViewUtils;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chiclaim on 2016/02/18
 */

public class GitHubContributorsActivity extends BaseActivity {

    private GitHubApi gitHubApi = ApiServiceFactory.createService(GitHubApi.class);

    public RecyclerView rvContent;
    public TextView tvTip;
    public BaseAdapter<Contributor> adapter;
    private boolean loading;

    private String nextPage;
    private boolean isLast;
    private String lastUrl;


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
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager)
                            rvContent.getLayoutManager();
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (!isLast && !loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = true;
                            adapter.showFooter();
                            requestContributes();
                        }
                    }
                }
            }
        };

        rvContent.addOnScrollListener(scrollListener);
    }


    public void parseLink(String link) {
        if (!TextUtils.isEmpty(link)) {
            String[] sp = link.split(",", 2);
            String next = sp[0];
            String last = sp[1];
            String[] subs = next.split(";", 2);

            if (nextPage != null) {
                isLast = nextPage.equals(lastUrl);
            }

            nextPage = subs[0].replaceAll("<|>", "");

            String[] subs2 = last.split(";", 2);

            if ("\"last\"".equals(subs2[1].split("=", 2)[1])) {
                lastUrl = subs2[0].replaceAll("<|>", "").trim();
            }

        } else {
            isLast = true;
        }
    }


    private void requestContributes() {
        Call<List<Contributor>> call;
        if (nextPage == null) {
            call = gitHubApi.contributors("square", "retrofit");
        } else {
            call = gitHubApi.nextContributors(nextPage);
        }
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                loading = false;
                hideLoadingDialog();
                if (response.code() == 200) {
                    List<Contributor> list = response.body();
                    adapter.appendItems(list);
                    parseLink(response.headers().get("Link"));
                    if (isLast) {
                        adapter.hideFooter();
                    }
                } else {
                    try {
                        String error = response.errorBody().string();
                        tvTip.setText(response.code() + error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                loading = false;
                hideLoadingDialog();
                t.printStackTrace();
                tvTip.setText(t.getClass().getName() + "\n" + t.getMessage());

                //java.net.SocketTimeoutException
                //java.net.UnknownHostException
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rvContent.removeOnScrollListener(scrollListener);
    }
}