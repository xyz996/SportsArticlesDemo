package com.example.sportsarticlesdemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.example.sportsarticlesdemo.POJO.Article;
import com.example.sportsarticlesdemo.R;
import com.example.sportsarticlesdemo.adapters.SportsArticleAdapter;
import com.example.sportsarticlesdemo.databinding.ActivityArticlesBinding;
import com.example.sportsarticlesdemo.viewModel.ArticleViewModel;
import com.example.sportsarticlesdemo.util.ItemClickListener;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import rezwan.pstu.cse12.youtubeonlinestatus.recievers.NetworkChangeReceiver;

@AndroidEntryPoint
public class ArticlesActivity extends AppCompatActivity implements ItemClickListener {


    private ActivityArticlesBinding binding;
    private ArticleViewModel viewModel;
    boolean isDark = false;


    private ArrayList<Article> articlesList = new ArrayList<>();

    private SportsArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_articles);
        binding.setLifecycleOwner(this);


        isDark = getThemeStatePref();


        viewModel = new ViewModelProvider(this).get(ArticleViewModel.class);


        if (IsConnectedToInternet(this)) {
            loadDataFromServer();
        }else{
            NetworkChangeReceiver changeReceiver = new NetworkChangeReceiver(this);
            changeReceiver.build();
        }
        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataFromServer();
                binding.swipeToRefresh.setRefreshing(false);
            }
        });

        binding.fabAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticlesActivity.this, AboutActivity.class));
            }
        });

        // load theme state
        if (isDark) {
            // Switch to Dark Theme
            binding.rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            // Switch to Light Theme
            binding.rootLayout.setBackgroundColor(getResources().getColor(R.color.white));

        }


        binding.SportsArticlesRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SportsArticleAdapter(this, this, isDark);
        binding.SportsArticlesRecycler.setAdapter(adapter);


        loadDataFromServer();


        binding.fabSwitcher.setOnClickListener(v -> {
            isDark = !isDark;
            if (isDark) {
                binding.rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
            } else {
                binding.rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
            }
            adapter = new SportsArticleAdapter(articlesList, this, this, isDark);

            binding.SportsArticlesRecycler.setAdapter(adapter);

            saveThemeStatePref(isDark);

        });

    }

    private void loadDataFromServer() {

        viewModel.getArticles();
        viewModel.getArticlesList().observe(this, articles -> {
            adapter.setList(articles);
            articlesList.clear();
            articlesList.addAll(articles);
        });
    }

    @Override
    public void onItemClick(View view, int position, boolean isLongClick) { //Implement OnItemClicked Listener

        Intent intent = new Intent(this, ArticleDetailsActivity.class);
        intent.putExtra("MyClass", articlesList.get(position)); // Pass Clicked Articles Data Into Details Activity
        startActivity(intent);

    }

    private void saveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark", isDark);
        editor.apply();
    }

    private boolean getThemeStatePref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        return pref.getBoolean("isDark", false);

    }



    public static boolean IsConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

}
