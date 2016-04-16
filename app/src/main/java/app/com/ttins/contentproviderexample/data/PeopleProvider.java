package app.com.ttins.contentproviderexample.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class PeopleProvider extends ContentProvider {

    private static final String LOG_TAG = PeopleProvider.class.getSimpleName();

    private static final int PEOPLE = 100;
    private static final int PEOPLE_ID = 101;

    private PeopleDBHelper peopleDBHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        String content = PeopleContract.CONTENT_AUTHORITY;
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, PeopleContract.PATH_PEOPLE, PEOPLE);
        matcher.addURI(content, PeopleContract.PATH_PEOPLE + "/#", PEOPLE_ID);

        return matcher;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)) {
            case PEOPLE:
                return PeopleContract.People.CONTENT_TYPE;
            case PEOPLE_ID:
                return PeopleContract.People.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = peopleDBHelper.getWritableDatabase();
        int rows;

        switch (uriMatcher.match(uri)) {
            case PEOPLE:
                rows = db.update(PeopleContract.People.TABLE_PEOPLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unable to update rows into: " + uri);
        }

        if (rows != 0) {
            try {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            catch (NullPointerException e) {
                Log.d(LOG_TAG, "Content resolver is null");
            }
        }

        return rows;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = peopleDBHelper.getWritableDatabase();
        long id;
        Uri retUri;

        switch(uriMatcher.match(uri)) {
            case PEOPLE:
                id = db.insert(
                        PeopleContract.People.TABLE_PEOPLE,
                        null,
                        values);

                if (id > 0) {
                    retUri = PeopleContract.People.buildPeopleUri(id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into : " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        return retUri;
    }

    @Override
    public boolean onCreate() {
        peopleDBHelper = new PeopleDBHelper(getContext());
        return true;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = peopleDBHelper.getWritableDatabase();
        int rows;

        switch(uriMatcher.match(uri)) {
            case PEOPLE:
                rows = db.delete(PeopleContract.People.TABLE_PEOPLE,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unable to delete rows from " + uri);
        }

        if (selection == null || rows != 0) {
            try {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            catch (NullPointerException e) {
                Log.d(LOG_TAG, "Content resolver is null");
            }
        }

        return rows;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = peopleDBHelper.getWritableDatabase();
        Cursor cursor;

        switch (uriMatcher.match(uri)) {
            case PEOPLE:
                cursor = db.query(
                        PeopleContract.People.TABLE_PEOPLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case PEOPLE_ID:
                long id = ContentUris.parseId(uri);
                cursor = db.query(
                        PeopleContract.People.TABLE_PEOPLE,
                        projection,
                        PeopleContract.People._ID + " = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        try {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        catch (NullPointerException e) {
            Log.d(LOG_TAG, "Content resolver is null");
        }


        return cursor;
    }


}
