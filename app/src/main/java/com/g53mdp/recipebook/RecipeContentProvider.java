package com.g53mdp.recipebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;

public class RecipeContentProvider extends ContentProvider {

    private DBHelper dbHelper = null;


    @Override
    public boolean onCreate() {

        this.dbHelper = new DBHelper(this.getContext(), "recipeContentProvider", null, 1);
        return true;
    }

    
    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        return null;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }

    
    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        return null;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }


}
