package us.mohitarora.popularmoviesapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<MovieItem> movieList = new ArrayList<>();

    MovieItem interstellar = new MovieItem("something" , 0);

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_main );

        for (int i=0; i< 20; i++){
            movieList.add(interstellar);
        }

        GridView gridView = (GridView) findViewById(R.id.movie_grid);

        MovieAdapter movieAdapter = new MovieAdapter(this, movieList);

        if (gridView != null) {
            gridView.setAdapter(movieAdapter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated");

        String popularMovies = builder.build().toString();
    }
}
