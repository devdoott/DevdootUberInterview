package com.devdoot.androidInterview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.devdoot.androidInterview.adapter.ImageAdapter2;
import com.devdoot.androidInterview.myModel.Result;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL="https://api.flickr.com/";
    private Map<String, String> queryMap=new HashMap<>();
    private Retrofit retrofit;
    private ImageAdapter2 imageAdapter2;
    private ImageService imageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_interview);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        GridView gridview = (GridView) findViewById(R.id.gridview);

        imageAdapter2=new ImageAdapter2(this,new Picasso.Builder(this).build());
        gridview.setAdapter(imageAdapter2);


        //https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&text=kittens

        queryMap.put("method","flickr.photos.search");
        queryMap.put("api_key","3e7cc266ae2b0e0d78e279ce8e361736");
        queryMap.put("format","json");
        queryMap.put("nojsoncallback","1");
        GsonConverterFactory gsonConverterFactory=GsonConverterFactory.create(new GsonBuilder().create());
        retrofit=new Retrofit.Builder().addConverterFactory(gsonConverterFactory).baseUrl(BASE_URL).build();
        imageService=retrofit.create(ImageService.class);
    }

    public void getImages() {


        Call<Result> imageCall = imageService.getImagesApi(queryMap);

        imageCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful()) {
                    imageAdapter2.setItems(response.body().getPhotos().getPhoto());

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                queryMap.put("text",s.trim());
                getImages();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
