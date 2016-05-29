package us.mohitarora.popularmoviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

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
                    .addToBackStack(null)
                    .commit();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

                Log.d(TAG, "in here count is "+ backStackEntryCount);
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null){
                    if(backStackEntryCount > 1){
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }else{
                        actionBar.setDisplayHomeAsUpEnabled(false);
                    }
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPosterSelected(MovieItem movieItem) {
        Bundle bundle = new Bundle();

        bundle.putParcelable("movieItem", Parcels.wrap(movieItem));

        DetailsViewFragment nextFrag= new DetailsViewFragment();

        nextFrag.setArguments(bundle);

        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, nextFrag)
                .addToBackStack(null)
                .commit();
    }

    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();

        if( actionBar != null ){
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            finish();
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
