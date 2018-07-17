package com.smathcadet.nytsearch.views;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smathcadet.nytsearch.R;
import com.smathcadet.nytsearch.controllers.ArticleAdapter;
import com.smathcadet.nytsearch.controllers.FilterFragment;
import com.smathcadet.nytsearch.controllers.SearchFragment;
import com.smathcadet.nytsearch.models.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnDataPass, FilterFragment.OnDataPass{

    private ArrayList<Article> mArticles;
    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;

    private String searchQuery;
    private String dateQuery;
    private String orderQuery;
    private ArrayList<String> newsDeskArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        newsDeskArray = new ArrayList<>();


        if (savedInstanceState != null) {
            mArticles = (ArrayList<Article>) savedInstanceState.getSerializable("articles");
        } else {
            mArticles = new ArrayList<>();
        }
        mRecyclerView = findViewById(R.id.rvArticles);
        mAdapter = new ArticleAdapter(mArticles, this);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        dataRequest();
    }

    private void dataRequest() {
        mArticles.clear();
        mAdapter.notifyDataSetChanged();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();

        params.put("api-key", "4c3a1140c7a34710bc2973c35b5167a5");
        params.put("q", searchQuery);
        params.put("begin_date", dateQuery);
        params.put("sort", orderQuery);
        if (!newsDeskArray.isEmpty()){
            String filterQuery = "news_desk:(";
            for (int i = 0; i < newsDeskArray.size(); i++){
                filterQuery += "\"";
                filterQuery += newsDeskArray.get(i);
                filterQuery += "\"";
                if (i < newsDeskArray.size() - 1){
                    filterQuery += "%20";
                }
            }
            filterQuery +=")";
            params.put("fq", filterQuery);
        }
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray arrayResults = null;
                try {
                    arrayResults = response.getJSONObject("response").getJSONArray("docs");
                    mArticles.addAll(Article.fromJSONArray(arrayResults));
                    mAdapter.notifyDataSetChanged();
                    Log.d("result", mArticles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("articles", mArticles);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager = getSupportFragmentManager();
        switch (item.getItemId()){
            case R.id.itemSearch:
                SearchFragment dialog = new SearchFragment();
                dialog.show(manager, "DialogSearch");
                return true;
            case R.id.itemFilter:
                FilterFragment filter = new FilterFragment();
                filter.show(manager, "DialogFilter");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataPass(String data) {
        searchQuery = data;
        dataRequest();
    }

    @Override
    public void onDataPass(String date, String order, ArrayList<String> newsDesk) {
        dateQuery = date;
        orderQuery = order;
        newsDeskArray = newsDesk;
        dataRequest();
    }
}
