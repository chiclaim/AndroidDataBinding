package com.mvvm.http;

import com.mvvm.model.Contributor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GitHubApi {

    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(@Path("owner") String owner,
                                         @Path("repo") String repo);

    @GET()
    Call<List<Contributor>> nextContributors(@Url String nextUrl);

}