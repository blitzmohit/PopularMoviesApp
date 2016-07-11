package us.mohitarora.popularmoviesapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by geek90 on 7/9/16.
 */

public class MovieDataSource {

    private SQLiteDatabase sqLiteDatabase;

    private PopularMoviesDbHelper popularMoviesDbHelper;

    public MovieDataSource(Context context){
        popularMoviesDbHelper = new PopularMoviesDbHelper(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = popularMoviesDbHelper.getWritableDatabase();
    }

    public void close(){
        popularMoviesDbHelper.close();
    }


}
