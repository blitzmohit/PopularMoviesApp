package us.mohitarora.popularmoviesapp;

import android.net.Uri;

/**
 * Created by admin on 5/22/2016.
 *
 */

class MovieDbUtil {

    public static final String RELEASE_DATE = "release_date";

    public static String TOP_MOVIES = "top_rated";

    public static String POPULAR_MOVIES = "popular";

    public static String UPCOMING_MOVIES= "upcoming";

    public static String ID = "id";

    public static String TITLE = "title";

    public static String POSTER_PATH = "poster_path";

    public static String OVERVIEW = "overview";

    public static String VOTE_AVERAGE = "vote_average";

    public static String BASE_IMAGE_URI = "http://image.tmdb.org/t/p/";

    public static Uri getNetworkUri( String requestType ){

        Uri.Builder builder = new Uri.Builder();

        String BASE_URL = "api.themoviedb.org";
        String BASE_PATH = "3";
        builder.scheme("https")
                .authority(BASE_URL)
                .appendPath(BASE_PATH)
                .appendPath("movie")
                .appendPath(requestType)
                .appendQueryParameter("api_key",APIKEY.API_KEY);

        return builder.build();
    }
}
