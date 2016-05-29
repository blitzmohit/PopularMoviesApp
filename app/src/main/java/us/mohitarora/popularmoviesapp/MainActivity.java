package us.mohitarora.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_main );

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById( R.id.main_container);

        if( fragment == null ){
            fragment = new MovieGridFragment();

            fm.beginTransaction()
                    .add( R.id.main_container, fragment )
//                    .addToBackStack("movie-grid")
                    .commit();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPosterSelected(MovieItem movieItem) {
        Bundle bundle = new Bundle();

        bundle.putParcelable("movieItem", Parcels.wrap(movieItem));

        Intent intent = new Intent(this, DetailsActivity.class);

        intent.putExtra("movieItem", bundle);

        startActivity(intent);
    }
}
