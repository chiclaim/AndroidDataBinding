//package com.mvvm.http;
//
//import android.text.TextUtils;
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by chiclaim on 2016/01/26
// */
//public class ApiServiceFactory2 {
//
//    //private static final String BASE_URL = "https://api.github.com/";
//    private static final String BASE_URL = "http://192.168.1.109:8080/JavaWebHttp2/";
//
//    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//
//
//    private static Retrofit.Builder builder = new Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            //.addCallAdapterFactory(ErrorCallAdapterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .addCallAdapterFactory(SynchronousCallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create());
//
//    public static <S> S createService(Class<S> serviceClazz) {
//        return createService(serviceClazz, null);
//    }
//
//    static {
//        // set your desired log level
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.interceptors().add(logging);
//
//        //httpClient.addNetworkInterceptor
//
//    }
//
//
//    public static <S> S createService(Class<S> serviceClazz, final String authorization) {
//
//        //Gson gson = new GsonBuilder()
//        //        .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
//        //        .create();
//
//        //httpClient.interceptors().clear();
//
//        if (!TextUtils.isEmpty(authorization)) {
//            httpClient.interceptors().add(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request original = chain.request();
//                    //addHead()
//                    Request.Builder requestBuilder = original.newBuilder()
//                            .header("Authorization", authorization)
//                            .header("Accept", "applicaton/json")
//                            .method(original.method(), original.body());
//
//                    Request request = requestBuilder.build();
//                    return chain.proceed(request);
//                }
//            });
//        }
//
//
//        OkHttpClient hClient = httpClient.build();
//        Retrofit retrofit = builder.client(hClient).build();
//        return retrofit.create(serviceClazz);
//    }
//
//}
