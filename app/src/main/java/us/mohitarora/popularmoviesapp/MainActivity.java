package us.mohitarora.popularmoviesapp;

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
}
