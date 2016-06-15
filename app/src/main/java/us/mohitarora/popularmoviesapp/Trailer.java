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
public class Trailer {
    public String key;

    public String name;

    public static ArrayList<Trailer> parseTrailer(JSONObject response) {
        ArrayList<Trailer> trailers = new ArrayList<>(4);

        try {
            JSONArray results = response.getJSONArray("results");

            for( int i=0; i<results.length(); i++ ){
                Trailer trailer =  new Trailer();

                JSONObject trailerItem = results.getJSONObject(i);

                trailer.key = trailerItem.getString("key");

                trailer.name = trailerItem.getString("name");

                trailers.add(trailer);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;
    }
}
