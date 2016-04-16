package app.com.ttins.contentproviderexample.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PeopleDBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = PeopleDBHelper.class.getSimpleName();

    public PeopleDBHelper (Context context) {
        super(context, PeopleContract.DATABASE_NAME, null, PeopleContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PeopleContract.DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "Upgrading DB from version" + oldVersion + " to version " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + PeopleContract.People.TABLE_PEOPLE);
        onCreate(db);
    }
}
