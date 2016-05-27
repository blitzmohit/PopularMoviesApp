package us.mohitarora.popularmoviesapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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
    ArrayList<MovieItem> movieList;

    GridView gridView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
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
                (Request.Method.GET, popularMoviesNetworkUri.toString(), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("JSON","Response: " + response.toString());
                        // Create Movie items from response.

                        movieList = MovieItem.parseMovieItems(response);

                        MovieAdapter movieAdapter = new MovieAdapter( getActivity(), movieList );

                        if ( gridView != null && movieList != null) {
                            Log.d(TAG, "Movie list size is "+ movieList.size());

                            gridView.setAdapter( movieAdapter );
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("JSON","Response: error");

                        Log.d("JSON", error.getMessage());
                    }
                });

        NetworkRequest.getInstance(getActivity()).addToRequestQueue(jsObjRequest);

    }

}