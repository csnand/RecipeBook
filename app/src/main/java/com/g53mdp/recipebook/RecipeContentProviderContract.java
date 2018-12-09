package com.g53mdp.recipebook;

import android.net.Uri;

public class RecipeContentProviderContract {

    public static final String AUTHORITY = "com.g53mdp.recipebook.RecipeContentProvider";

    public static final Uri RECIPELIST_URI = Uri.parse("content://"+AUTHORITY+"/recipeList");
    public static final Uri MAIN_URI = Uri.parse("content://"+AUTHORITY+"/");

    public static final String TABLE_NAME = "recipeList";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String RATING = "rating";

    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/RecipeContentProvider.recipeList";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/RecipeContentProvider.recipeList";
}
