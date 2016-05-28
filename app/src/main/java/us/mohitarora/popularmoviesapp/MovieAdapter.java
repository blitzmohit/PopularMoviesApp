package us.mohitarora.popularmoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by geek90 on 5/16/16.
 *
 */
class MovieAdapter extends ArrayAdapter<MovieItem>{
    private ImageLoader mImageLoader;

    private OnMovieSelectedListener listener;

    private Context context;


    public MovieAdapter(Context context, List<MovieItem> objects) {
        super(context, 0 , objects);

        this.context = context;

        this.listener = (OnMovieSelectedListener) context;

        mImageLoader = NetworkRequest.getInstance( context ).getImageLoader();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MovieItem movieItem = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }

        NetworkImageView poster = (NetworkImageView)convertView.findViewById(R.id.poster);

        poster.setImageUrl( movieItem.getPosterUri(), mImageLoader );


        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPosterSelected(movieItem);
            }
        });

        return convertView;
    }



    interface OnMovieSelectedListener {
        void onPosterSelected(MovieItem movieItem);
    }
}
