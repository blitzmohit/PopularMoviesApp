package us.mohitarora.popularmoviesapp;

import android.net.Uri;

/**
 * Created by admin on 5/22/2016.
 *
 */

class MovieDbUtil {

    static final String RELEASE_DATE = "release_date";

    static final String TOP_RATED_MOVIES = "top_rated";

    static final String POPULAR_MOVIES = "popular";

    static final String UPCOMING_MOVIES= "upcoming";

    static final int POPULARITY_SORT = 1;

    static final int RATING_SORT = 2;

    static final int FAVORITE_SORT = 3;

    static final String ID = "id";

    static final String TITLE = "title";

    static final String POSTER_PATH = "poster_path";

    static final String OVERVIEW = "overview";

    static final String VOTE_AVERAGE = "vote_average";

    static final String BASE_IMAGE_URI = "http://image.tmdb.org/t/p/";

    static final String VIDEOS = "videos";

    static final String REVIEWS = "reviews";

    static Uri getNetworkUri( String... requestType ){

        Uri.Builder builder = new Uri.Builder();

        String BASE_URL = "api.themoviedb.org";
        String BASE_PATH = "3";
        builder.scheme("https")
                .authority(BASE_URL)
                .appendPath(BASE_PATH)
                .appendPath("movie");

        for( String request : requestType ){
            builder.appendPath(request);
        }
        builder.appendQueryParameter("api_key",APIKEY.API_KEY);

        return builder.build();
    }
}
