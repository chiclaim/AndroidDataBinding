//package com.mvvm.http;
//
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//import retrofit2.Call;
//import retrofit2.CallAdapter;
//import retrofit2.Retrofit;
//
//public class SynchronousCallAdapterFactory extends CallAdapter.Factory {
//    public static CallAdapter.Factory create() {
//        return new SynchronousCallAdapterFactory();
//    }
//
//    @Override
//    public CallAdapter<Object> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
//        // if returnType is retrofit2.Call, do nothing
//        if (getRawType(returnType) == Call.class) {
//            return null;
//        }
//
//        return new CallAdapter<Object>() {
//            @Override
//            public Type responseType() {
//                return returnType;
//            }
//
//            @Override
//            public <R> Object adapt(Call<R> call) {
//                try {
//                    return call.execute().body();
//                } catch (IOException e) {
//                    throw new RuntimeException(); // do something better
//                }
//            }
//        };
//    }
//}