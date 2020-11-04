package com.example.sportsarticlesdemo.api;

import com.example.sportsarticlesdemo.POJO.ArticlesResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportsArticlesAPIService {

    @GET("top-headlines")
    Observable<ArticlesResponse> getArticles(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey);

}
