package us.mohitarora.popularmoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by geek90 on 5/16/16.
 *
 */
public class MovieAdapter extends ArrayAdapter<MovieItem>{

    public MovieAdapter(Context context, List<MovieItem> objects) {
        super(context, 0 , objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieItem movieItem = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }

        ImageView poster = (ImageView)convertView.findViewById(R.id.poster);

//        poster.setImageResource(movieItem.resourceId);

        return convertView;
    }
}
