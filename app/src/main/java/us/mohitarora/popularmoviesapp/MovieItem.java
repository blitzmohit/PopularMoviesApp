package us.mohitarora.popularmoviesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by geek90 on 5/21/16.
 *
 */
public class MovieItem {
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getPosterUri(){
        return MovieDbUtil.BASE_IMAGE_URI + "w185" + posterPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    String id;

    String title;

    String overview;

    String posterPath;

    Double popularity;

    public static ArrayList<MovieItem> parseMovieItems(JSONObject response) {
        try {
            JSONArray results = response.getJSONArray("results");

            ArrayList<MovieItem> movieItems = new ArrayList<>(results.length());

            for ( int i=0; i<results.length(); i++ ) {
                JSONObject jsonMovieItem = results.getJSONObject(i);

                MovieItem movieItem = new MovieItem();

                movieItem.title = jsonMovieItem.getString(MovieDbUtil.TITLE);

                movieItem.id = jsonMovieItem.getString(MovieDbUtil.ID);

                movieItem.popularity = jsonMovieItem.getDouble(MovieDbUtil.POPULARITY);

                movieItem.overview = jsonMovieItem.getString(MovieDbUtil.OVERVIEW);

                movieItem.posterPath = jsonMovieItem.getString(MovieDbUtil.POSTER_PATH);

                movieItems.add(movieItem);
            }

            return movieItems;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
