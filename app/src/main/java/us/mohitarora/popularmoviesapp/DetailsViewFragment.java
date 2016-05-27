package us.mohitarora.popularmoviesapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by geek90 on 5/27/16.
 */
public class DetailsViewFragment extends Fragment {
    MovieItem movie;

    @BindView(R.id.detail_year)
    TextView year;

    @BindView(R.id.detail_rating)
    TextView rating;

    @BindView(R.id.detail_runtime)
    TextView runtime;

    @BindView(R.id.detail_overview)
    TextView overview;

    @BindView(R.id.detail_title)
    TextView title;

    @BindView(R.id.detail_poster)
    ImageView poster;

    @BindView(R.id.detail_part)
    View part;

    String TAG;

    ImageLoader mImageLoader;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();

        mImageLoader = NetworkRequest.getInstance(getActivity()).getImageLoader();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_view_fragment, container, false);

        MovieItem movieItem = Parcels.unwrap(getArguments().getParcelable("movieitem"));

        if (movieItem != null) {
            this.movie = movieItem;
        }

        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (movie != null) {

            title.setText(movie.getTitle());

            overview.setText(movie.getOverview());

            year.setText(movie.getYear());

            rating.setText(movie.getVoteAverage());

/*            ImageRequest request = new ImageRequest(movie.getPosterUri("w154"),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            Log.d(TAG, "poster height is "+poster.getHeight());

                            Log.d(TAG, "poster width is "+poster.getWidth());

                            Log.d(TAG,"Height is "+bitmap.getHeight());

                            Log.d(TAG,"Width is "+ bitmap.getWidth());
                            poster.setImageBitmap(bitmap);

                        }
                    }, 0, 0, ImageView.ScaleType.CENTER, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
//                            poster.setImageResource(R.drawable.image_load_error);
                        }
                    });

            NetworkRequest.getInstance(getContext()).addToRequestQueue(request);*/

//            poster.setImageUrl(movie.getPosterUri(), mImageLoader);

        } else {
            Log.d(TAG, "movie is null");
        }
    }
}
