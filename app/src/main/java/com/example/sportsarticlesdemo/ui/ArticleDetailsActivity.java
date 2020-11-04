package com.example.sportsarticlesdemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.example.sportsarticlesdemo.POJO.Article;
import com.example.sportsarticlesdemo.R;
import com.example.sportsarticlesdemo.databinding.ActivityArticleDetailsBinding;

import java.io.Serializable;
import java.util.Objects;

public class ArticleDetailsActivity extends AppCompatActivity implements Serializable {


    private ActivityArticleDetailsBinding binding;
    boolean isDark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_details);
        binding.setLifecycleOwner(this);


        setSupportActionBar(binding.toolBar);

        //Show Up The Back Arrow
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Article currentItem = (Article) getIntent().getSerializableExtra("MyClass");


        isDark = getThemeStatePref();
        {

            if (isDark) {
                // Switch to Dark Theme
                binding.detailsRootLayout.setBackgroundColor(getResources().getColor(R.color.black));

            } else {
                // Switch to Light Theme
                binding.detailsRootLayout.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }


        if (currentItem != null)
            initView(currentItem);
    }

    private void initView(Article currentItem) {

        binding.ArticleImage.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_transition_animation));
        binding.container.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_scale_animation));

        if (currentItem.getUrlToImage() != null && !currentItem.getUrlToImage().isEmpty())
            Glide.with(this).load(currentItem.getUrlToImage())
                    .into(binding.ArticleImage);

        binding.title.setText(currentItem.getTitle());
        binding.author.setText(currentItem.getAuthor());
        binding.date.setText(currentItem.getPublishedAt());
        binding.Description.setText(currentItem.getDescription());
        binding.content.setText(currentItem.getContent());
        binding.url.setMovementMethod(LinkMovementMethod.getInstance());

        String ArticleLink = "\n Read the full article published By " + currentItem.getSource().getName() + ":\n " + currentItem.getUrl();
        binding.url.setText(ArticleLink);
        binding.ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sports App");
                i.putExtra(Intent.EXTRA_TEXT, currentItem.getUrl());
                startActivity(Intent.createChooser(i, "Share With"));
            }
        });
    }

    private boolean getThemeStatePref() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        return pref.getBoolean("isDark", false);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); //Back to the ArticlesActivity When press the back arrow
        return true;
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
        super.onBackPressed();
    }


}