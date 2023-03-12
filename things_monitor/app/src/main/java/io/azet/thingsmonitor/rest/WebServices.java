package io.azet.thingsmonitor.rest;

import android.util.Log;

import java.io.IOException;

import io.azet.thingsmonitor.rest.models.ChannelFeeds;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServices {
    private static WebServices webServices;
    public static final String BASE_URL = "https://api.thingspeak.com";

    private String cookie = "";

    private Retrofit retrofit;
    private Endpoints endpoints;

    public static WebServices getInstance() {
        if (webServices == null) {
            webServices = new WebServices();
        }
        return webServices;
    }

    private WebServices() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        endpoints = retrofit.create(Endpoints.class);

    }

    public void getPublicChannelFeeds(final onResponseListener listener, String channel, int results) {
        Call<ChannelFeeds> call = endpoints.getPublicChannelFeeds(channel, results);
        Log.d("WebService", call.request().toString());
        call.enqueue(new Callback<ChannelFeeds>() {
            @Override
            public void onResponse(Call<ChannelFeeds> call, Response<ChannelFeeds> response) {
                int statusCode = response.code();
                switch (statusCode) {
                    case 200:
                        ChannelFeeds channelFeeds = response.body();
                        listener.onSuccess(channelFeeds);
                        break;
                    default:
                        Log.d("WebService", call.request().toString() + ": " + statusCode);
                        listener.onError();
                }
            }

            @Override
            public void onFailure(Call<ChannelFeeds> call, Throwable t) {
                final int chunkSize = 2048;
                for (int i = 0; i < toString().length(); i += chunkSize) {
                    Log.d("WebService", t.toString().substring(i, Math.min(t.toString().length(), i + chunkSize)));
                }
                listener.onError();
            }

        });
    }

    public void getPrivateChannelFeeds(final onResponseListener listener, String channel, String apiKey, int results) {
        Call<ChannelFeeds> call = endpoints.getPrivateChannelFeeds(channel, apiKey, results);
        Log.d("WebService", call.request().toString());
        call.enqueue(new Callback<ChannelFeeds>() {
            @Override
            public void onResponse(Call<ChannelFeeds> call, Response<ChannelFeeds> response) {
                int statusCode = response.code();
                switch (statusCode) {
                    case 200:
                        ChannelFeeds channelFeeds = response.body();
                        listener.onSuccess(channelFeeds);
                        break;
                    default:
                        Log.d("WebService", call.request().toString() + ": " + statusCode);
                        listener.onError();
                }
            }

            @Override
            public void onFailure(Call<ChannelFeeds> call, Throwable t) {
                final int chunkSize = 2048;
                for (int i = 0; i < toString().length(); i += chunkSize) {
                    Log.d("WebService", t.toString().substring(i, Math.min(t.toString().length(), i + chunkSize)));
                }
                listener.onError();
            }

        });
    }

    public void getPublicChannelRangeFeeds(final onResponseListener listener, String channel, int results, String start, String end) {
        Call<ChannelFeeds> call = endpoints.getPublicChannelRangeFeeds(channel, results, start, end);
        Log.d("WebService", call.request().toString());
        call.enqueue(new Callback<ChannelFeeds>() {
            @Override
            public void onResponse(Call<ChannelFeeds> call, Response<ChannelFeeds> response) {
                int statusCode = response.code();
                switch (statusCode) {
                    case 200:
                        ChannelFeeds channelFeeds = response.body();
                        listener.onSuccess(channelFeeds);
                        break;
                    default:
                        Log.d("WebService", call.request().toString() + ": " + statusCode);
                        listener.onError();
                }
            }

            @Override
            public void onFailure(Call<ChannelFeeds> call, Throwable t) {
                final int chunkSize = 2048;
                for (int i = 0; i < toString().length(); i += chunkSize) {
                    Log.d("WebService", t.toString().substring(i, Math.min(t.toString().length(), i + chunkSize)));
                }
                listener.onError();
            }

        });
    }

    public void getPrivateChannelRangeFeeds(final onResponseListener listener, String channel, String apiKey, int results, String start, String end) {
        Call<ChannelFeeds> call = endpoints.getPrivateChannelRangeFeeds(channel, apiKey, results, start, end);
        Log.d("WebService", call.request().toString());
        call.enqueue(new Callback<ChannelFeeds>() {
            @Override
            public void onResponse(Call<ChannelFeeds> call, Response<ChannelFeeds> response) {
                int statusCode = response.code();
                switch (statusCode) {
                    case 200:
                        ChannelFeeds channelFeeds = response.body();
                        listener.onSuccess(channelFeeds);
                        break;
                    default:
                        Log.d("WebService", call.request().toString() + ": " + statusCode);
                        listener.onError();
                }
            }

            @Override
            public void onFailure(Call<ChannelFeeds> call, Throwable t) {
                final int chunkSize = 2048;
                for (int i = 0; i < toString().length(); i += chunkSize) {
                    Log.d("WebService", t.toString().substring(i, Math.min(t.toString().length(), i + chunkSize)));
                }
                listener.onError();
            }

        });
    }

    Interceptor interceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder().addHeader("cookie", cookie).build();
            return chain.proceed(newRequest);
        }
    };

}
