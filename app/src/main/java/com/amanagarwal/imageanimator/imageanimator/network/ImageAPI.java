package com.amanagarwal.imageanimator.imageanimator.network;

import com.amanagarwal.imageanimator.imageanimator.network.models.Image;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageAPI {
    @GET("photos_public.gne?format=json")
    Call<Image> getImageItems();
}
