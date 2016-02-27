package com.mvvm.http;

import com.mvvm.model.AuthToken;
import com.mvvm.model.Contributor;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface GitHubApi {

//    @GET("repos/{owner}/{repo}/contributors")
//    Call<List<Contributor>> contributors(@Path("owner") String owner,
//                                         @Path("repo") String repo);
//
//    @GET()
//    Call<List<Contributor>> nextContributors(@Url String nextUrl);


    @GET("/contributor/list")
    Observable<List<Contributor>> contributors();

    @GET("/token")
    AuthToken refreshToken();


}