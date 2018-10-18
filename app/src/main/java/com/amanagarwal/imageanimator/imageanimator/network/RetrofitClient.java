package com.amanagarwal.imageanimator.imageanimator.network;

import android.util.Log;

import com.amanagarwal.imageanimator.imageanimator.network.models.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
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
        Call<ResponseBody> call = api.getImageItems();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(LOG_TAG, "onResponse");
                String jsonStr;

                try {
                    jsonStr = response.body().string();
                    if (!jsonStr.isEmpty()) {
                        Gson gson = new Gson();
                        jsonStr = jsonStr.replace("jsonFlickrFeed(", "");
                        JSONObject jsonObject = new JSONObject(jsonStr);

                        Image image = gson.fromJson(jsonObject.toString(), Image.class);
                        imageDataListener.onDataFetch(image);
                    }
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage());
                    imageDataListener.onDataFetch(null);
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage());
                    imageDataListener.onDataFetch(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(LOG_TAG, "onFailure");
                Log.e(LOG_TAG, t.toString());
                imageDataListener.onFailure();
            }
        });
    }
}
