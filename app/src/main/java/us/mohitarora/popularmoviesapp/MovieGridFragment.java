package us.mohitarora.popularmoviesapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by admin on 5/25/2016.
 *
 */

public class MovieGridFragment extends Fragment {
    ArrayList<MovieItem> movieList = new ArrayList<>();

    MovieItem interstellar = new MovieItem("something" , 0);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.movie_grid_fragment,  container, false );

        GridView gridView = (GridView) view.findViewById( R.id.movie_grid );

        MovieAdapter movieAdapter = new MovieAdapter( getActivity(), movieList );

        for (int i=0; i< 20; i++){
            movieList.add(interstellar);
        }

        if ( gridView != null ) {
            gridView.setAdapter( movieAdapter );
        }

        return view;
    }
}