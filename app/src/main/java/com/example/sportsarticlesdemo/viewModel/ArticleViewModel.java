package com.example.sportsarticlesdemo.viewModel;

import android.content.SharedPreferences;
import android.util.Log;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import com.example.sportsarticlesdemo.POJO.Article;
import com.example.sportsarticlesdemo.repository.Repository;
import java.util.ArrayList;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ArticleViewModel extends androidx.lifecycle.ViewModel {
    private static final String TAG = "ArticleViewModel";
    private Repository repository;
    private MutableLiveData<ArrayList<Article>> articles = new MutableLiveData<>();

    @ViewModelInject
    public ArticleViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getArticles() {
        repository.getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> articles.setValue(result.getArticles()),
                        error -> Log.e(TAG, error.getMessage()));
    }

    public MutableLiveData<ArrayList<Article>> getArticlesList() {
        return articles;
    }

}
