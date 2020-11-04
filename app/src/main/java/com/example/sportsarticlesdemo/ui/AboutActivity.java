package com.example.sportsarticlesdemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.sportsarticlesdemo.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_baseline_supervised_user_circle_24)
                .setDescription(getString(R.string.app_name))
                .addItem(MadeFor())
                .addItem(DevelopedBy())
                .addGroup(getString(R.string.contact_me))
                .addItem(callMe())
                .addEmail("abood.gharaibeh96@gmail.com", getString(R.string.email_me))
                .addItem(backToHomeActivity())
                .create();
        setContentView(aboutPage);
    }

    private Element MadeFor() {
        Element madeFor = new Element();
        final String madeForString = getString(R.string.made_for);
        madeFor.setTitle(madeForString);
        madeFor.setGravity(Gravity.CENTER);
        madeFor.setOnClickListener(v -> Toast.makeText(AboutActivity.this, madeForString, Toast.LENGTH_SHORT).show());
        return madeFor;
    }

    private Element DevelopedBy() {
        Element developedBy = new Element();
        final String developedByString = getString(R.string.developed_by);
        developedBy.setTitle(developedByString);
        developedBy.setGravity(Gravity.CENTER);
        developedBy.setOnClickListener(v -> Toast.makeText(AboutActivity.this, developedByString, Toast.LENGTH_SHORT).show());
        return developedBy;
    }

    private Element callMe() {
        Element callus = new Element();
        final String call = getString(R.string.call_me);
        callus.setTitle(call).setIconDrawable(R.drawable.ic_call_black_24dp);
        callus.setOnClickListener(view -> {
            final Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + "+962790070862"));
            view.getContext().startActivity(intent);
        });
        return callus;
    }

    private Element backToHomeActivity() {
        Element backElement = new Element();
        backElement.setTitle(getString(R.string.sports_article)).setIconDrawable(R.drawable.ic_home_black_24dp);
        backElement.setGravity(Gravity.CENTER);
        backElement.setOnClickListener(v -> onBackPressed());
        return backElement;

    }








}