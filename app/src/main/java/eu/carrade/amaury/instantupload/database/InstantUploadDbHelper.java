package eu.carrade.amaury.instantupload.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import eu.carrade.amaury.instantupload.database.InstantUploadContract.HistoryEntry;
import eu.carrade.amaury.instantupload.database.InstantUploadContract.ServiceEntry;


public class InstantUploadDbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "instant_upload.db";

    private static final String SQL_CREATE_TABLE_SERVICES =
            "CREATE TABLE " + ServiceEntry.TABLE_NAME + " ("
            + ServiceEntry._ID + " INTEGER PRIMARY KEY, "
            + ServiceEntry.COLUMN_NAME_NAME + " TEXT NOT NULL, "
            + ServiceEntry.COLUMN_NAME_TYPE + " TEXT NOT NULL, "
            + ServiceEntry.COLUMN_NAME_PRIMARY + " INTEGER NOT NULL, "
            + ServiceEntry.COLUMN_NAME_SETTINGS + " TEXT"
            + ")"
            ;

    private static final String SQL_CREATE_TABLE_HISTORY =
            "CREATE TABLE " + HistoryEntry.TABLE_NAME + " ("
            + HistoryEntry._ID + " INTEGER PRIMARY KEY, "
            + HistoryEntry.COLUMN_NAME_SERVICE_ID + " INTEGER NOT NULL, "
            + HistoryEntry.COLUMN_NAME_DATE + " INTEGER NOT NULL, "
            + HistoryEntry.COLUMN_NAME_URI + " TEXT NOT NULL, "
            + "FOREIGN KEY(" + HistoryEntry.COLUMN_NAME_SERVICE_ID + ") REFERENCES " + ServiceEntry.TABLE_NAME + "(" + ServiceEntry._ID + ") ON DELETE CASCADE ON UPDATE CASCADE"
            + ")"
            ;

    public InstantUploadDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.beginTransaction();
        db.execSQL(SQL_CREATE_TABLE_SERVICES);
        db.execSQL(SQL_CREATE_TABLE_HISTORY);
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
