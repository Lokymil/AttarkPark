package fr.esiea.mobile.attrackpark.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Christophe on 20/11/2015.
 */
public class ParkContentProvider extends ContentProvider {

    // Table's fields
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "key_name";
    public static final String KEY_DESCRIPTION = "key_description";
    public static final String KEY_URL = "key_url";
    public static final String KEY_PAYS = "key_pays";
    public static final String KEY_LATITUDE = "key_latitude";
    public static final String KEY_LONGITUDE = "key_longitude";
    public static final String KEY_IMG_URL = "key_img_url";

    // Exposition URI
    public static final Uri CONTENT_URI = Uri.parse("content://fr.esiea.mobile.attrackpark");

    // Element to identifie request
    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;

    // URI matcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("fr.esiea.mobile.attrakpark","elements",ALLROWS);
        uriMatcher.addURI("fr.esiea.mobile.attrakpark","elements/#",SINGLE_ROW);

    }

    // DB helper
    private ParksDBHelper parksHelper;

    @Override
    public boolean onCreate() {
        parksHelper = new ParksDBHelper(getContext(), ParksDBHelper.DATABASE_NAME, null, ParksDBHelper.DATABASE_VERSION);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Open DB
        SQLiteDatabase db = parksHelper.getWritableDatabase();

        // request parameters
        String groupBy = null;
        String having = null;

        // Construct request
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // Ajout de la table
        queryBuilder.setTables(ParksDBHelper.DATABASE_TABLE);

        switch (uriMatcher.match(uri)){
            case SINGLE_ROW :
                String rowId = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID + "=" + rowId);
                break;
            default :
                break;
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){
            case ALLROWS :
                return "vnd.android.cursor.dir/vnd.paad.elemental";
            case SINGLE_ROW :
                return "vnd.android.cursor.item/vnd.paad.elemental";
            default:
                throw new IllegalArgumentException("URI not recognized");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = parksHelper.getWritableDatabase();

        String nullColumnHack = null;

        long id = db.insert(ParksDBHelper.DATABASE_TABLE, nullColumnHack, values);

        if (id > -1){
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI,id);
            getContext().getContentResolver().notifyChange(insertedId,null);
            return insertedId;
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = parksHelper.getWritableDatabase();

        switch(uriMatcher.match(uri)){
            case SINGLE_ROW :
                String rowId = uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? "AND ("+selection+')' : "");
                break;
            default:
                break;
        }

        if (selection == null) {
            selection = "1";
        }

        int deleteCount = db.delete(ParksDBHelper.DATABASE_TABLE, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = parksHelper.getWritableDatabase();

        switch(uriMatcher.match(uri)){
            case SINGLE_ROW :
                String rowId = uri.getPathSegments().get(1);
                selection = KEY_ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND ("+selection+')' : "");
                break;

            default:
                break;
        }

        if (selection == null){
            selection = "1";
        }

        int updateCount = db.update(ParksDBHelper.DATABASE_TABLE, values, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }

    private static class ParksDBHelper extends SQLiteOpenHelper {

        // DB name
        private static final String DATABASE_NAME = "AttrackParkDB";

        // Table name
        private static final String DATABASE_TABLE = "Parks";

        // DB version
        private static final int DATABASE_VERSION = 1;

        // DB creation script
        private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE
                + " (" + KEY_ID + " integer primary key autoincrement, "
                + KEY_NAME + " text not null, "
                + KEY_DESCRIPTION + " text not null, "
                + KEY_URL + " text not null, "
                + KEY_PAYS + " text not null, "
                + KEY_LATITUDE + " double not null, "
                + KEY_LONGITUDE + " double not null, "
                + KEY_IMG_URL + " text not null); ";


        public ParksDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public ParksDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("DB", "Mise à jour de la version " + oldVersion
                    + " vers la version " + newVersion
                    + ": toutes les données seront perdues");
             db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}
