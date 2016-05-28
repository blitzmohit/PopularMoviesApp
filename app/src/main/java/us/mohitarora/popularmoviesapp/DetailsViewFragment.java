package us.mohitarora.popularmoviesapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by geek90 on 5/27/16.
 */
public class DetailsViewFragment extends Fragment {
    private MovieItem movie;

    @BindView(R.id.detail_year)
    private
    TextView year;

    @BindView(R.id.detail_rating)
    private
    TextView rating;

    @BindView(R.id.detail_runtime)
    TextView runtime;

    @BindView(R.id.detail_overview)
    private
    TextView overview;

    @BindView(R.id.detail_title)
    private
    TextView title;

    @BindView(R.id.detail_poster)
    private
    ImageView poster;

    @BindView(R.id.detail_part)
    View part;

    private String TAG;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();

        ImageLoader mImageLoader = NetworkRequest.getInstance(getActivity()).getImageLoader();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_view_fragment, container, false);

        MovieItem movieItem = Parcels.unwrap(getArguments().getParcelable("movieItem"));

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

            ImageRequest request = new ImageRequest(movie.getPosterUri("w154"),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            poster.setImageBitmap(bitmap);

                        }
                    }, 0, 0, ImageView.ScaleType.FIT_CENTER, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
//                            poster.setImageResource(R.drawable.image_load_error);
                        }
                    });

            NetworkRequest.getInstance(getContext()).addToRequestQueue(request);

//            poster.setImageUrl(movie.getPosterUri(), mImageLoader);

        } else {
            Log.d(TAG, "movie is null");
        }
    }
}
