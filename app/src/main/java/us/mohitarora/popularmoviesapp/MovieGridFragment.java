package us.mohitarora.popularmoviesapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 5/25/2016.
 *
 */

public class MovieGridFragment extends Fragment {
    private static String TAG;

    private GridView gridView;

    private MovieAdapter mMovieAdapter;

    private int sortOrder = MovieDbUtil.POPULARITY_SORT; //default sort order is by popularity

    private Response.ErrorListener jsonErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("JSON","Response: error");

            Log.d("JSON", error.getMessage());
        }
    };
    private Response.Listener jsonResponseListener = new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            // Create Movie items from response.
            ArrayList<MovieItem> movieList = MovieItem.parseMovieItems(response);

            if (mMovieAdapter == null){
                mMovieAdapter = new MovieAdapter( getActivity(), movieList);
            }else{
                mMovieAdapter.clear();

                mMovieAdapter.addAll(movieList);
            }

            if ( gridView != null && movieList != null) {
                Log.d(TAG, "Movie list size is "+ movieList.size());

                gridView.setAdapter( mMovieAdapter );
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.movie_grid_fragment,  container, false );

        gridView = (GridView) view.findViewById( R.id.movie_grid );

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Uri popularMoviesNetworkUri = MovieDbUtil.getNetworkUri(MovieDbUtil.POPULAR_MOVIES);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, popularMoviesNetworkUri.toString(), null, jsonResponseListener ,jsonErrorListener );

        NetworkRequest.getInstance(getActivity()).addToRequestQueue(jsObjRequest);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        Menu subMenu = menu.findItem(R.id.sort).getSubMenu();

        subMenu.add( Menu.NONE, R.id.menuSortFavorites, Menu.NONE, R.string.sort_by_favorites );

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ){
            case R.id.menuSortPopularity:
                Toast.makeText(getContext(), "SORT BY NEWEST", Toast.LENGTH_SHORT).show();

                if( sortOrder != MovieDbUtil.POPULARITY_SORT ){
                    sortOrder = MovieDbUtil.POPULARITY_SORT;

                    sort();
                }

                return true;
            case R.id.menuSortRating:
                Toast.makeText(getContext(), "SORT BY RATING", Toast.LENGTH_SHORT).show();

                if( sortOrder != MovieDbUtil.RATING_SORT ){
                    sortOrder = MovieDbUtil.RATING_SORT;

                    sort();
                }
                return true;

            case R.id.menuSortFavorites:
                Toast.makeText(getContext(), "SORT BY FAVORITES", Toast.LENGTH_SHORT).show();

                if( sortOrder != MovieDbUtil.FAVORITE_SORT ){
                    sortOrder = MovieDbUtil.FAVORITE_SORT;

                    sort();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sort() {
        String requestType = null ;
        switch ( sortOrder ){
            case MovieDbUtil.POPULARITY_SORT:
                requestType = MovieDbUtil.POPULAR_MOVIES;
                break;
            case MovieDbUtil.RATING_SORT:
                requestType = MovieDbUtil.TOP_RATED_MOVIES;
                break;
            case MovieDbUtil.FAVORITE_SORT:
                //
                break;
            default:
                requestType = MovieDbUtil.POPULAR_MOVIES;
        }

        Uri networkUri = MovieDbUtil.getNetworkUri(requestType);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, networkUri.toString(), null, jsonResponseListener ,jsonErrorListener );

        NetworkRequest.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
    }
}