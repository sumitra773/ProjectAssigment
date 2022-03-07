package com.example.android.projectassigment.network;

import com.example.android.projectassigment.BuildConfig;
import com.example.android.projectassigment.api.ApiInterface;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://mekvahan.com";
    private static final long CONNECTION_TIMEOUT = 60000L;
    private static final long READ_TIMEOUT = 60000L;
    private static final long WRITE_TIMEOUT = 60000L;

    private static RetrofitClient retrofitClient;
    private final Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                ))
                .client(mClient())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    private static HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if(BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    private static OkHttpClient mClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool())
                .addInterceptor(loggingInterceptor())
                .build();
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }
}
