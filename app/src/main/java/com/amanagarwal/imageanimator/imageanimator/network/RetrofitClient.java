package com.amanagarwal.imageanimator.imageanimator.network;

import android.util.Log;

import com.amanagarwal.imageanimator.imageanimator.network.models.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String LOG_TAG = RetrofitClient.class.getName();
    private static String BASE_URL = "https://api.flickr.com/services/feeds/";
    private static Retrofit retrofit = null;

    private static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public static void buildImages(final ImageDataListener imageDataListener) {
        Log.e(LOG_TAG, "buildImages");

        if (retrofit == null)
            retrofit = getClient();

        ImageAPI api = retrofit.create(ImageAPI.class);
        Call<Image> call = api.getImageItems();

        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Log.e(LOG_TAG, "onResponse");
                Image image = response.body();
                imageDataListener.onDataFetch(image);
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.e(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, t.toString());
                imageDataListener.onFailure();
            }
        });
    }
}
