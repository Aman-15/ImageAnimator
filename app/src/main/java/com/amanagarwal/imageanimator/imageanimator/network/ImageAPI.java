package com.amanagarwal.imageanimator.imageanimator.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ImageAPI {
    @GET("photos_public.gne?format=json")
    Call<ResponseBody> getImageItems();
}
