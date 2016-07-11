package us.mohitarora.popularmoviesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import us.mohitarora.popularmoviesapp.MoviesAppContract.MoviesEntry;
/**
 *
 * Created by geek90 on 7/7/16.
 */

public class PopularMoviesDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "PopularMovies.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String FLOAT_TYPE = " REAL";

    private static final String BOOLEAN_TYPE = " NUMERIC";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +
                    MoviesEntry._ID + " INTEGER PRIMARY KEY," +

                    MoviesEntry.COLUMN_MOVIE_ID + TEXT_TYPE + COMMA_SEP +

                    MoviesEntry.COLUMN_MOVIE_TITLE + TEXT_TYPE + COMMA_SEP +

                    MoviesEntry.COLUMN_MOVIE_OVERVIEW + TEXT_TYPE + COMMA_SEP +

                    MoviesEntry.COLUMN_MOVIE_RELEASE_DATE + TEXT_TYPE + COMMA_SEP +

                    MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE + FLOAT_TYPE + COMMA_SEP +

                    MoviesEntry.COLUMN_MOVIE_POSTER_PATH + TEXT_TYPE + COMMA_SEP +

                    MoviesEntry.COLUMN_MOVIE_IS_FAVORITE + BOOLEAN_TYPE + COMMA_SEP +

            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME;


    public PopularMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

        //TODO do not delete all entries as some are saved data.
    }
}
