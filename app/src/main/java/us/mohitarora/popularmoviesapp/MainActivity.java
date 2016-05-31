package us.mohitarora.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.parceler.Parcels;

import butterknife.BindBool;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager fragmentManager;

    private DetailsViewFragment detailsViewFragment;

    private MovieGridFragment movieGridFragment;

    @BindBool(R.bool.has_two_panes)
    boolean isTwoPane;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.main_layout );

        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        if( movieGridFragment == null ){
            movieGridFragment = new MovieGridFragment();
            if( isTwoPane ){
                Log.d(TAG, " yes this is true too");
                Bundle bundle = new Bundle();

                bundle.putBoolean("selectFirst",true);

                movieGridFragment.setArguments(bundle);
            }

            fragmentManager.beginTransaction()
                    .add( R.id.main_container, movieGridFragment )
//                    .addToBackStack("movie-grid")
                    .commit();
        }

         View view = findViewById(R.id.detail_container);

        if( view != null && view.getVisibility() == View.VISIBLE){
            Log.d(TAG,"Yes yes it is visible");
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

        if( isTwoPane ){
            if( detailsViewFragment == null){
                detailsViewFragment = new DetailsViewFragment();

                detailsViewFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container, detailsViewFragment)
                        .addToBackStack("movie-detail")
                        .commit();
            }else{
                detailsViewFragment.showMovie(movieItem);
            }
        }else{
            Intent intent = new Intent(this, DetailsActivity.class);

            intent.putExtra("movieItem", bundle);

            startActivity(intent);
        }
    }
}
