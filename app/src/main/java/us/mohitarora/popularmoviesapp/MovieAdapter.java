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

    private static View prevTopRibbon;

    private static View prevBottomRibbon;

    private NetworkImageView poster;
    private static int selected;


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

        poster = (NetworkImageView)convertView.findViewById(R.id.poster);

        final View topRibbon = convertView.findViewById(R.id.top_ribbon);

        final View bottomRibbon = convertView.findViewById(R.id.bottom_ribbon);

        if( position == selected ) {
            topRibbon.setVisibility(View.VISIBLE);

            bottomRibbon.setVisibility(View.VISIBLE);

            if( prevTopRibbon == null && prevBottomRibbon ==null ){
                prevTopRibbon = topRibbon;

                prevBottomRibbon = bottomRibbon;
            }
        }else{
            topRibbon.setVisibility(View.GONE);

            bottomRibbon.setVisibility(View.GONE);
        }

        poster.setImageUrl( movieItem.getPosterUri(), mImageLoader );


        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPosterSelected(movieItem);

                selected = position;

                prevBottomRibbon.setVisibility(View.GONE);

                prevTopRibbon.setVisibility(View.GONE);

                prevTopRibbon = topRibbon;

                prevBottomRibbon = bottomRibbon;

                topRibbon.setVisibility(View.VISIBLE);

                bottomRibbon.setVisibility(View.VISIBLE);
    }
});

        return convertView;
    }

    public void selectPoster(){
        listener.onPosterSelected( getItem(0));
    }

    interface OnMovieSelectedListener {
        void onPosterSelected(MovieItem movieItem);
    }
}
