package com.g53mdp.recipebook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class RecipeContentProvider extends ContentProvider {

    private DBHelper dbHelper = null;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RecipeContentProviderContract.AUTHORITY, RecipeContentProviderContract.TABLE_NAME, 1);
        uriMatcher.addURI(RecipeContentProviderContract.AUTHORITY, RecipeContentProviderContract.TABLE_NAME + "/#", 2);
    }

    @Override
    public boolean onCreate() {
        this.dbHelper = new DBHelper(this.getContext(), DBHelper.DB_NAME, null, 1);
        return true;
    }

    
    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {

        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();

        Log.d("query", uri.toString() + " " + uriMatcher.match(uri));

        switch (uriMatcher.match(uri)){
            case 2:
                selection = RecipeContentProviderContract._ID + "=" + uri.getLastPathSegment();
            case 1:
                Cursor cursorReturn = sqlDB.query(RecipeContentProviderContract.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                return cursorReturn;
            default:
                return null;

        }
    }


    @Override
    public String getType(Uri uri) {

        if (uri.getPathSegments() == null){
            return RecipeContentProviderContract.CONTENT_TYPE_MULTIPLE;
        } else {
            return RecipeContentProviderContract.CONTENT_TYPE_SINGLE;
        }
    }

    
    @Override
    public Uri insert( Uri uri,  ContentValues values) {

        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        long id = sqlDB.insert(RecipeContentProviderContract.TABLE_NAME, null, values);

        Uri newUri = ContentUris.withAppendedId(uri, id);
        getContext().getContentResolver().notifyChange(newUri, null);

        Log.d("inserted", newUri.toString());

        return newUri;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        Log.d("query", uri.toString() + " " + uriMatcher.match(uri));

        switch (uriMatcher.match(uri)){
            case 2:
                selection = RecipeContentProviderContract._ID + "=" + uri.getLastPathSegment();
                int rowAffected = sqlDB.update(RecipeContentProviderContract.TABLE_NAME, values, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return rowAffected;
            default:
                return 0;
        }

    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        Log.d("query", uri.toString() + " " + uriMatcher.match(uri));

        switch (uriMatcher.match(uri)){
            case 2:
                selection = RecipeContentProviderContract._ID + "=" + uri.getLastPathSegment();
                int rowAffected = sqlDB.delete(RecipeContentProviderContract.TABLE_NAME, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return rowAffected;
            default:
                return 0;
        }

    }


}
