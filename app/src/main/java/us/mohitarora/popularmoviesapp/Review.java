package us.mohitarora.popularmoviesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by admin on 6/3/2016.
 *
 */
@Parcel
public class Review {

    public String author;

    public String content;

    public String url;

    public static ArrayList<Review> parseReview(JSONObject response) {
        ArrayList<Review> reviews = new ArrayList<>(4);

        try {
            JSONArray results = response.getJSONArray("results");

            for( int i=0; i<results.length(); i++ ){
                Review review =  new Review();

                JSONObject reviewItem = results.getJSONObject(i);

                review.author = reviewItem.getString("author");

                review.content = reviewItem.getString("content");

                review.url = reviewItem.getString("url");

                reviews.add(review);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}
