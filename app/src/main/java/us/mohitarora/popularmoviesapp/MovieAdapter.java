package us.mohitarora.popularmoviesapp;

import android.content.Context;
import android.util.Log;
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

    private NetworkImageView poster;

    public static String selected;

    private View topBanner;

    private View bottomBanner;

    private boolean mDualPane;


    public MovieAdapter(Context context, List<MovieItem> objects) {
        super(context, 0 , objects);

        this.context = context;

        this.listener = (OnMovieSelectedListener) context;

        mImageLoader = NetworkRequest.getInstance( context ).getImageLoader();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MovieItem movieItem = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }

        topBanner = convertView.findViewById(R.id.top_ribbon);

        bottomBanner = convertView.findViewById(R.id.bottom_ribbon);

        if( !movieItem.getId().equals( selected ) ){
            topBanner.setVisibility(View.GONE);

            bottomBanner.setVisibility(View.GONE);
        }else{
            if( mDualPane ){
                topBanner.setVisibility(View.VISIBLE);

                bottomBanner.setVisibility(View.VISIBLE);
            }
        }

        poster = (NetworkImageView)convertView.findViewById(R.id.poster);

        poster.setImageUrl( movieItem.getPosterUri(), mImageLoader );


        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDualPane = context.getResources().getBoolean(R.bool.has_two_panes);

                if( mDualPane ){
                    Log.d("Adapter","it is dualpane");

                    notifyDataSetChanged();
                }

                listener.onPosterSelected(movieItem);

                selected = movieItem.getId();
    }
});

        return convertView;
    }

    public void selectPoster(){
        listener.onPosterSelected( getItem(0) );
    }

    interface OnMovieSelectedListener {
        void onPosterSelected(MovieItem movieItem);
    }
}
