package com.necistudio.vigerpdf.network;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


public class RestClient  {

    private static ApiInterface ApiInterface;

    public static ApiInterface getClient() {
        if (ApiInterface == null) {
            OkHttpClient okclient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(50,TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .addHeader("Accept", "Application/JSON").build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            Retrofit client = new Retrofit.Builder()
                    .client(okclient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://necistudio.com")
                    .build();
            ApiInterface = client.create(ApiInterface.class);

        }
        return ApiInterface;
    }


    public interface ApiInterface {
        @Streaming
        @GET
        Call<ResponseBody> streamFile(@Url String fileUrl);
    }


}

