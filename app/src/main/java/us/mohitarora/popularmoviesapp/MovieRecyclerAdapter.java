package us.mohitarora.popularmoviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 6/15/2016.
 *
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {
    private final ImageLoader mImageLoader;

    private final Context mCtx;

    private ArrayList<MovieItem> mDataSet;

    private MovieRecyclerAdapter.OnMovieSelectedListener listener;

    private static String selected;

    private int last_selected = 0;

    private Object mLock = new Object();

    private boolean mDualPane;


    MovieRecyclerAdapter( Context context, ArrayList<MovieItem> objects ){
        mDataSet = objects;

        this.listener = (MovieRecyclerAdapter.OnMovieSelectedListener) context;

        mImageLoader = NetworkRequest.getInstance( context ).getImageLoader();

        mCtx = context;

    }

    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieRecyclerAdapter.ViewHolder holder, int position) {
        final MovieItem movieItem =  mDataSet.get(position);

        holder.poster.setImageUrl( movieItem.getPosterUri(), mImageLoader );

        if( movieItem.getId().equals( selected ) ){
            if( mDualPane ) {
                holder.bottomRibbon.setVisibility(View.VISIBLE);

                holder.topRibbon.setVisibility(View.VISIBLE);
            }
        }else{
            holder.bottomRibbon.setVisibility(View.GONE);

            holder.topRibbon.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView poster;

        View topRibbon;

        View bottomRibbon;


        public ViewHolder(View itemView) {
            super(itemView);

            poster = (NetworkImageView) itemView.findViewById(R.id.poster);

            topRibbon = itemView.findViewById(R.id.top_ribbon);

            bottomRibbon = itemView.findViewById(R.id.bottom_ribbon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MovieItem movieItem = mDataSet.get(getAdapterPosition());

                    listener.onPosterSelected(movieItem);

                    selected = movieItem.getId();

                    mDualPane = mCtx.getResources().getBoolean(R.bool.has_two_panes);

                    if( mDualPane ){
                        Log.d("Adapter","it is dualpane");

                        notifyItemChanged(last_selected);

                        notifyItemChanged( getAdapterPosition() );

                        last_selected = getAdapterPosition();


                    }

                }
            });
        }
    }

    public void selectPoster(){
        listener.onPosterSelected( mDataSet.get(0) );
    }


    interface OnMovieSelectedListener {
        void onPosterSelected(MovieItem movieItem);
    }

    public void clear(){
        synchronized (mLock) {
            if (mDataSet != null) {
                mDataSet.clear();
            }
        }
        notifyDataSetChanged();
    }

    public void addAll( ArrayList<MovieItem> newDataSet ){
        synchronized (mLock) {
            if (mDataSet!=null){
                mDataSet.addAll(newDataSet);
            }
        }
    }
}
