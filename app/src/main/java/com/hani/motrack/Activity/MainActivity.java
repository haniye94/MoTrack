package com.hani.motrack.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hani.motrack.Adapter.MovieAdapter;
import com.hani.motrack.Model.MovieModel;
import com.hani.motrack.R;
import com.hani.motrack.Service.ConnectionManager;
import com.hani.motrack.Service.Util;
import com.hani.motrack.Utils.PaginationScrollListener;

import java.util.ArrayList;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ConnectionManager.IConnectionManager {

    private static final String TAG = "MainActivity";

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 2;
    private int currentPage = PAGE_START;

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    final ArrayList<MovieModel> movieList = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;

    Util util = Util.getInstance(this);
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        url = "http://www.omdbapi.com/?apikey=bb0ed857&s=car&" + "page=" + currentPage;
        setUpRecyclerView();
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        loadFirstPage();
    }

    private void setUpRecyclerView() {
        adapter = new MovieAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
        util.getConnectionRequest(this, 0, url, null);
    }

    private void loadNextPage() {
        util.getConnectionRequest(this, 0, url, null);
    }

    @Override
    public void onConnectionResponse(int requestCode, String response) {
        if (response == null || response.isEmpty()) {
            return;
        }
        if (requestCode == 0) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("Search");
                ParsArrayToModel(jsonArray);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (currentPage == 1) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        adapter.addAll(movieList);
                        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                        else isLastPage = true;
                    }
                });

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removeLoadingFooter();
                        isLoading = false;
                        adapter.addAll(movieList);
                        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                        else isLastPage = true;
                    }
                });
            }
        }
    }

    @Override
    public void onConnectionFailure(int requestCode, String response) {

    }

    private void ParsArrayToModel(JSONArray jsonArray) {


        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObjectData = jsonArray.getJSONObject(i);
                MovieModel movieModel = new MovieModel();
                movieModel.setTitle(jsonObjectData.getString(MovieModel.KEY.TITLE_KEY));
                movieModel.setYear(jsonObjectData.getString(MovieModel.KEY.YEAR_KEY));
                movieModel.setImdbID(jsonObjectData.getString(MovieModel.KEY.IMDBID_KEY));
                movieModel.setType(jsonObjectData.getString(MovieModel.KEY.TYPE_KEY));
                movieModel.setPoster(jsonObjectData.getString(MovieModel.KEY.POSTER_URL_KEY));
                movieList.add(movieModel);

            } catch (Exception ex) {

            }
        }

    }
}