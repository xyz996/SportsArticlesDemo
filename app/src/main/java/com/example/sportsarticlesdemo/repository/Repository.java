package com.example.sportsarticlesdemo.repository;


import com.example.sportsarticlesdemo.POJO.ArticlesResponse;
import com.example.sportsarticlesdemo.api.SportsArticlesAPIService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private SportsArticlesAPIService service;
   public static final String API_KEY = ""; // Your API_KEY 

    @Inject
    public Repository(SportsArticlesAPIService service) {
        this.service = service;
    }

    public Observable<ArticlesResponse> getArticles() {
        return service.getArticles("us", "sports", API_KEY);
    }

}
