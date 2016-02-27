//package com.mvvm.http;
//
//import com.mvvm.exception.AccessDenyException;
//
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Type;
//
//import retrofit2.Call;
//import retrofit2.CallAdapter;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
//public class ErrorCallAdapterFactory extends CallAdapter.Factory {
//    public static CallAdapter.Factory create() {
//        return new ErrorCallAdapterFactory();
//    }
//
//    @Override
//    public CallAdapter<Object> get(final Type returnType, Annotation[] annotations, Retrofit retrofit) {
//        // if returnType is retrofit2.Call, do nothing
//        //if (getRawType(returnType) == Call.class) {
//        //    return null;
//        //}
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
//                    Response res = call.execute();
//                    getCallResponseType(res.code());
//                    return res.body();
//                } catch (IOException e) {
//                    throw new RuntimeException(); // do something better
//                }
//            }
//        };
//    }
//
//    static void getCallResponseType(int code) {
//        if (code == 401) {
//            throw new AccessDenyException();
//        }
//    }
//}