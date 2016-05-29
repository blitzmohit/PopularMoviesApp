package us.mohitarora.popularmoviesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by geek90 on 5/21/16.
 */
@Parcel
public class MovieItem {
    public String id;
    public String title;
    public String overview;
    public String posterPath;
    public Double voteAverage;
    public String releaseDate;

    public MovieItem() {
    }

    public static ArrayList<MovieItem> parseMovieItems(JSONObject response) {
        try {
            JSONArray results = response.getJSONArray("results");

            ArrayList<MovieItem> movieItems = new ArrayList<>(results.length());

            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonMovieItem = results.getJSONObject(i);

                MovieItem movieItem = new MovieItem();

                movieItem.title = jsonMovieItem.getString(MovieDbUtil.TITLE);

                movieItem.id = jsonMovieItem.getString(MovieDbUtil.ID);

                movieItem.voteAverage = jsonMovieItem.getDouble(MovieDbUtil.VOTE_AVERAGE);

                movieItem.overview = jsonMovieItem.getString(MovieDbUtil.OVERVIEW);

                movieItem.posterPath = jsonMovieItem.getString(MovieDbUtil.POSTER_PATH);

                movieItem.releaseDate = jsonMovieItem.getString(MovieDbUtil.RELEASE_DATE);

                movieItems.add(movieItem);
            }

            return movieItems;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUri() {
        return MovieDbUtil.BASE_IMAGE_URI + "w185" + posterPath;
    }

    public String getPosterUri(String width) {
        return MovieDbUtil.BASE_IMAGE_URI + width + posterPath;
    }

    public String getVoteAverage() {
        return String.valueOf(voteAverage) + "/10";
    }

    public String getYear() {
        return releaseDate.split("-")[0];
    }
}
