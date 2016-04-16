package app.com.ttins.contentproviderexample.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class PeopleContract {

    public static final String CONTENT_AUTHORITY = "app.com.ttins.contentproviderexample.peopledatabase";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String DATABASE_NAME = "people.db";
    public static final int DATABASE_VERSION = 1;

    //Database SQL creation statement
    public static final String DATABASE_CREATE = "CREATE TABLE " +
            PeopleContract.People.TABLE_PEOPLE + "(" +
            PeopleContract.People._ID + " integer primary key autoincrement, " +
            PeopleContract.People.COLUMN_FIRST_NAME + " text non null, " +
            PeopleContract.People.COLUMN_LAST_NAME + " text non null, " +
            PeopleContract.People.COLUMN_GENDER + " text non null);";

    public static final String PATH_PEOPLE = "people";

    public static final class People implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PEOPLE).build();

        public static final String TABLE_PEOPLE = "people";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_GENDER = "gender";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + CONTENT_URI + "/" + PATH_PEOPLE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + CONTENT_URI + "/" + PATH_PEOPLE;

        public static Uri buildPeopleUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
