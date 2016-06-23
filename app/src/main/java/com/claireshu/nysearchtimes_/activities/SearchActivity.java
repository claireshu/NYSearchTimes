package com.claireshu.nysearchtimes_.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.claireshu.nysearchtimes_.Article;
import com.claireshu.nysearchtimes_.ArticleActivity;
import com.claireshu.nysearchtimes_.ArticleArrayAdapter;
import com.claireshu.nysearchtimes_.FilterActivity;
import com.claireshu.nysearchtimes_.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    RecyclerView rvArticles;
    StaggeredGridLayoutManager gridLayoutManager;

    int page;
    String newsDesk;
    String sort;
    String beginDate;

    String url;
    boolean initialLoad = true;

    Typeface font;

    private final String URL_SEARCH = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
    private final String URL_TOP = "https://api.nytimes.com/svc/topstories/v2/home.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        font = Typeface.createFromAsset(getAssets(), "fonts/sourcesanspro.otf");

        // sets the action bar text font to customFont
        SpannableString s = new SpannableString("NYTimesSearch");
        com.claireshu.nysearchtimes_.TypefaceSpan typeface = new com.claireshu.nysearchtimes_.TypefaceSpan(this, "sourcesanspro.otf");
        s.setSpan(typeface, 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(s);


        setUpViews();

        refresh(0, null, null, null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        TextView searchText = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTypeface(font);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                initialLoad = false;
                // perform query here
                search(query);

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //no inspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpViews() {

        rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        rvArticles.setAdapter(adapter);

        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(gridLayoutManager);

        ItemClickSupport.addTo(rvArticles).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //create an intent to display the article
                        Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                        // get the article to display
                        Article article = articles.get(position);
                        // pass in that article into intent
                        intent.putExtra("article", Parcels.wrap(article));
                        // launch the activity
                        startActivity(intent);
                    }
                }
        );


    }

    private void search(final String query) {
        rvArticles.clearOnScrollListeners();
        rvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                refresh(page, query, beginDate, sort, newsDesk);
            }
        });

        refresh(0, query, beginDate, sort, newsDesk);
    }
    private void refresh(int num, String query, String begin_date, String sort, String news_desk) {
        page = num;
        Log.d("PAGENUM", Integer.toString(page));
        AsyncHttpClient client = new AsyncHttpClient();
        if (initialLoad) {
            url = URL_TOP;
        } else {
            url = URL_SEARCH;
        }


        RequestParams params = new RequestParams();
        params.put("api-key", "7f160c48b3fa45c5bd259583a5ba8502");

        if (!initialLoad) {
            params.put("page", page);
            params.put("q", query);

            if (begin_date != null) params.put("begin_date", begin_date);
            if (sort != null) params.put("sort", sort);
            if (news_desk != null) {
                // params.put("fq", "news_desk:(\"Fashion & Style\")");.
                params.put("fq", "news_desk:(\"" + news_desk + "\")");
            }
        }

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    if (initialLoad) {
                        articleJsonResults = response.getJSONArray("results");
                    } else {
                        articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    }

                    //int curSize = adapter.getItemCount();

                    ArrayList<Article> newArticles = Article.fromJSONArray(articleJsonResults, initialLoad);

                    if (page == 0) {
                        articles.clear();
                    }

                    articles.addAll(newArticles);
                    adapter.notifyDataSetChanged();
                    //adapter.notifyItemRangeInserted(0, articles.size() - 1);

                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }


    private final int REQUEST_CODE = 20;

    public void onFilter(MenuItem item) {
        Intent intent = new Intent(SearchActivity.this, FilterActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            newsDesk = data.getStringExtra("news_desk");
            sort = data.getStringExtra("sort");
            beginDate = data.getStringExtra("date");

        } else {
            // error handling
        }
    }
}
