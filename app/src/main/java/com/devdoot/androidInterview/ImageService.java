package com.devdoot.androidInterview;

import com.devdoot.androidInterview.myModel.Result;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ImageService {
    @GET("services/rest")
    Call<Result> getImagesApi(@QueryMap(encoded = true)Map<String,String> map);
}
