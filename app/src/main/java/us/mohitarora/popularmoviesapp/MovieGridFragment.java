package us.mohitarora.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 5/25/2016.
 *
 */

public class MovieGridFragment extends Fragment {
    private static String TAG = MovieGridFragment.class.getSimpleName();

    @BindView(R.id.movie_grid)
    GridView gridView;

    @BindView(R.id.main_progress_bar)
    ProgressBar progressBar;

    private MovieAdapter mMovieAdapter;

    private boolean showFirst;

    private int sortOrder = MovieDbUtil.POPULARITY_SORT; //default sort order is by popularity

    private Response.ErrorListener jsonErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("JSON","Response: error");

            progressBar.setVisibility( View.GONE );

            Toast.makeText(getContext(), "Could not fetch data from the API", Toast.LENGTH_SHORT).show();
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

            if ( gridView != null && movieList != null ) {
                Log.d(TAG, "Movie list size is "+ movieList.size());

                gridView.setAdapter( mMovieAdapter );

                //We are in dual pane mode, select the first item as selected
                if( showFirst ){
                    mMovieAdapter.selectPoster();

                    showFirst = false;
                }


                gridView.setVisibility( View.VISIBLE );

                progressBar.setVisibility( View.GONE );
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_movie_grid,  container, false );

        ButterKnife.bind(this,view);

        if( getArguments() != null){
            showFirst = getArguments().getBoolean("selectFirst");
        }

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
        menu.clear();

        inflater.inflate(R.menu.grid_fragment_menu, menu);

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
        progressBar.setVisibility( View.VISIBLE );

        gridView.setVisibility( View.GONE );

        if (sortOrder == MovieDbUtil.FAVORITE_SORT){

            ReadFromFile readFromFile = new ReadFromFile();

            readFromFile.execute();

        }else {

            String requestType = null;
            switch (sortOrder) {
                case MovieDbUtil.POPULARITY_SORT:
                    requestType = MovieDbUtil.POPULAR_MOVIES;
                    break;
                case MovieDbUtil.RATING_SORT:
                    requestType = MovieDbUtil.TOP_RATED_MOVIES;
                    break;
                default:
                    requestType = MovieDbUtil.POPULAR_MOVIES;
            }

            Uri networkUri = MovieDbUtil.getNetworkUri(requestType);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, networkUri.toString(), null, jsonResponseListener, jsonErrorListener);

            NetworkRequest.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
        }
    }

    private class ReadFromFile extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            File directory = new File(getActivity().getFilesDir(), "movies");

            FileInputStream fileInputStream;

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("{" +
                    "  \"page\": 1," +
                    "  \"results\": [" +
                    "    ");

            if (directory.exists() && directory.isDirectory()){

                File[] files = directory.listFiles();

                for (int i =0; i< files.length ; i++) {
                    File file = files[i];

                    try {
                        fileInputStream = new FileInputStream(file);

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

                        String strLine;

                        while ((strLine = bufferedReader.readLine()) != null) {
                            stringBuilder.append(strLine);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if( i < files.length -1 ){
                        stringBuilder.append(",");
                    }
                }
                stringBuilder.append("]}");

                try{
                    return new JSONObject(stringBuilder.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if(jsonObject !=null){
                jsonResponseListener.onResponse(jsonObject);
            }else{
                jsonErrorListener.onErrorResponse(new VolleyError());
            }

        }
    }

    public void setSelected( MovieItem movieItem ){
        if( mMovieAdapter != null ){
            mMovieAdapter.selected = movieItem.getId() ;
        }
    }
}