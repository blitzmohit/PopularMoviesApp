package us.mohitarora.popularmoviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 5/29/2016.
 *
 */

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.detail_container);


        Bundle bundle = getIntent().getBundleExtra("movieItem");

        if( fragment == null ){
            fragment = new DetailsViewFragment();

            fragment.setArguments(bundle);

            fm.beginTransaction()
                    .add( R.id.detail_container, fragment )
//                    .addToBackStack("movie-details")
                    .commit();
        }

    }
}
