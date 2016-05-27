package us.mohitarora.popularmoviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieSelectedListener {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_main );

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById( R.id.container );

        if( fragment == null ){
            fragment = new MovieGridFragment();

            fm.beginTransaction()
                    .add( R.id.container, fragment )
                    .commit();
        }
    }

    @Override
    public void onPosterSelected(MovieItem movieItem) {
        Bundle bundle = new Bundle();

        bundle.putParcelable("movieitem", Parcels.wrap(movieItem));

        DetailsViewFragment nextFrag= new DetailsViewFragment();

        nextFrag.setArguments(bundle);

        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, nextFrag)
                .addToBackStack(null)
                .commit();
    }
}
