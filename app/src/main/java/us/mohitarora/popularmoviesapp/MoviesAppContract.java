package us.mohitarora.popularmoviesapp;

import android.provider.BaseColumns;

/**
 * Created by geek90 on 7/7/16.
 *
 */

public final class MoviesAppContract {
        // To prevent someone from accidentally instantiating the contract class,
        // give it an empty constructor.
        public MoviesAppContract() {}

        /* Inner class that defines the table contents */
        public static abstract class MoviesEntry implements BaseColumns {
            public static final String TABLE_NAME = "moviesdb";
            public static final String COLUMN_MOVIE_ID = "movieid";
            public static final String COLUMN_MOVIE_TITLE = "title";
            public static final String COLUMN_MOVIE_OVERVIEW = "overview";
            public static final String COLUMN_MOVIE_RELEASE_DATE = "releasedate";
            public static final String COLUMN_MOVIE_VOTE_AVERAGE = "voteaverage";
            public static final String COLUMN_MOVIE_POSTER_PATH = "posterpath";
            public static final String COLUMN_MOVIE_IS_FAVORITE = "isfavorite";
        }
}
