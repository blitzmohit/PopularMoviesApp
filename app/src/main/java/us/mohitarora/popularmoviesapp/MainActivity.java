package us.mohitarora.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.parceler.Parcels;

import butterknife.BindBool;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager fragmentManager;

    @BindBool(R.bool.has_two_panes)

    boolean isTwoPane;

        @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.main_layout );

        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();
    }



    @Override
    public void onPosterSelected(MovieItem movieItem) {
        Log.d( TAG, "inside on poster selected " + movieItem.getTitle() );

        Bundle bundle = new Bundle();

        bundle.putParcelable("movieItem", Parcels.wrap(movieItem));

        if( isTwoPane ){
            DetailsViewFragment detailsViewFragment = new DetailsViewFragment();

                detailsViewFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container, detailsViewFragment)
                        .commit();
        }else{
            Intent intent = new Intent(this, DetailsActivity.class);

            intent.putExtra("movieItem", bundle);

            startActivity(intent);
        }
    }
}
