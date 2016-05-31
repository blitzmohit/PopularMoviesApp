package us.mohitarora.popularmoviesapp;

        import android.graphics.Bitmap;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.ImageRequest;

        import org.json.JSONException;
        import org.json.JSONObject;
        import org.parceler.Parcels;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;

        import butterknife.BindView;
        import butterknife.ButterKnife;

/**
 * Created by geek90 on 5/27/16.
 *
 */
public class DetailsViewFragment extends Fragment {
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

    @BindView(R.id.detail_mark_favorite)
    TextView markFavorite;

    private MovieItem movieItem;

    private String TAG = DetailsViewFragment.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, view);

        movieItem = Parcels.unwrap(getArguments().getParcelable("movieItem"));

        markFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteToFile writeToFile = new WriteToFile();

                writeToFile.execute();
            }
        });

        if (movieItem != null) {
            showMovie(movieItem);
        }

        return view;
    }



    public void showMovie( MovieItem movie ) {
        if (movie != null) {
            movieItem = movie;

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

    private class WriteToFile extends AsyncTask<Void,Void, Void>{
        FileOutputStream fileOutputStream;
        @Override
        protected Void doInBackground(Void... params) {
            String message = null;

            JSONObject storageObject = new JSONObject();
            try {
                storageObject.put(MovieDbUtil.TITLE, movieItem.getTitle());

                storageObject.put(MovieDbUtil.OVERVIEW, movieItem.getOverview());

                storageObject.put(MovieDbUtil.ID, movieItem.getId());

                storageObject.put(MovieDbUtil.RELEASE_DATE, movieItem.getDate());

                storageObject.put(MovieDbUtil.POSTER_PATH, movieItem.getPosterPath());

                storageObject.put(MovieDbUtil.VOTE_AVERAGE, movieItem.getVoteAverage());

                message = storageObject.toString();

                Log.d(TAG, movieItem.getId());

                Log.d(TAG, message);

                File directory = new File(getActivity().getFilesDir(), "movies");

                directory.mkdirs();

                File file = new File(directory, movieItem.getId());

                if (!file.exists()) {
                    Log.d(TAG,"file does not exist");

                    fileOutputStream = new FileOutputStream(file);

                    fileOutputStream.write(message.getBytes());

                    fileOutputStream.flush();

                    fileOutputStream.close();

                }
            }catch( IOException e ){
                Log.d(TAG, e.getMessage());
            }catch (JSONException exception){
                Log.d(TAG, exception.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getActivity(), "Favorite Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
