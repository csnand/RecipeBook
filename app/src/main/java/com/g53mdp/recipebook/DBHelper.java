package com.g53mdp.recipebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "recipeListDB";

    public DBHelper(Context context, String name, CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE recipeList ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(255), content VARCHAR(255), rating INTEGER DEFAULT NULL); ");

        db.execSQL( " INSERT INTO recipeList (title, content, rating) VALUES ('test Recipe One',   'test Content One',   10) " );
        db.execSQL( " INSERT INTO recipeList (title, content, rating) VALUES ('test Recipe Two',   'test Content Two',   9) " );
        db.execSQL( " INSERT INTO recipeList (title, content, rating) VALUES ('test Recipe Three', 'test Content Three', 8) " );
        db.execSQL( " INSERT INTO recipeList (title, content, rating) VALUES ('test Recipe Four',  'test Content Four',  7) " );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS recipaList ");
        onCreate(db);
    }
}
