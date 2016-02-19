package com.mvvm.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chiclaim on 2016/01/26
 */
public class ApiServiceFactory {

    private static final String BASE_URL = "https://api.github.com/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClazz) {
        return createService(serviceClazz, null);
    }

    public static <S> S createService(Class<S> serviceClazz, final String authorization) {

        httpClient.interceptors().clear();

        if (!TextUtils.isEmpty(authorization)) {
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    //addHead()
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", authorization)
                            .header("Accept", "applicaton/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(logging);

        OkHttpClient hClient = httpClient.build();
        //Log.d("Api", hClient.interceptors().size() + "");
        Retrofit retrofit = builder.client(hClient).build();
        return retrofit.create(serviceClazz);
    }

}
