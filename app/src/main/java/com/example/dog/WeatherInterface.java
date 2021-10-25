package com.example.dog;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {
    @GET("/data/2.5/weather")
    Call<WeatherDTO> getWeather(@Query("appid") String appid,
                                @Query("lat") double lat,
                                @Query("lon") double lon);
}
