package com.smathcadet.nytsearch.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.smathcadet.nytsearch.R;
import com.smathcadet.nytsearch.models.Article;

public class ArticleActivity extends AppCompatActivity {

    private WebView wvArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        wvArticle = findViewById(R.id.wvArticle);

        Article article = (Article) getIntent().getSerializableExtra("article");

        wvArticle.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wvArticle.loadUrl(article.getWebUrl());
    }

    public static Intent newIntent(Context context, Article article){
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra("article", article);
        return intent;
    }

}
