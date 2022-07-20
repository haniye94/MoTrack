package com.hani.motrack.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hani.motrack.Model.MovieDetail;
import com.hani.motrack.R;
import com.hani.motrack.Service.ConnectionManager;
import com.hani.motrack.Service.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity implements ConnectionManager.IConnectionManager {

    TextView txtDetail;
    ImageView imgPoster;
    Util util;
    String i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        util = Util.getInstance(this);
        txtDetail = findViewById(R.id.txt_detail);
        imgPoster = findViewById(R.id.img_poster);
        Bundle bundle = getIntent().getExtras();
        i = bundle.getString("i");
        String url = "http://www.omdbapi.com/?apikey=bb0ed857&" + "i=" + i;
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
                MovieDetail movieDetail = new MovieDetail();
                movieDetail.setPoster(jsonObject.getString("Poster"));
                String details = "Title :" + jsonObject.getString("Title") + "\n" +
                        "Year :" + jsonObject.getString("Year") + "\n" +
                        "Rated :" + jsonObject.getString("Rated") + "\n" +
                        "Released :" + jsonObject.getString("Released") + "\n" +
                        "Runtime :" + jsonObject.getString("Runtime") + "\n" +
                        "Genre :" + jsonObject.getString("Genre") + "\n" +
                        "Director :" + jsonObject.getString("Director") + "\n" +
                        "Writer :" + jsonObject.getString("Writer") + "\n" +
                        "Actors :" + jsonObject.getString("Actors") + "\n" +
                        "Language :" + jsonObject.getString("Language") + "\n" +
                        "Country :" + jsonObject.getString("Country") + "\n";

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(getApplicationContext()).load(movieDetail.getPoster()).into(imgPoster);
                        txtDetail.setText(details);

                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onConnectionFailure(int requestCode, String response) {

    }

}