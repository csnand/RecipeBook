package com.g53mdp.recipebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.BufferUnderflowException;

public class RecipeDeatilsActivity extends AppCompatActivity {

    private long recipeID;
    private int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_deatil);

        Bundle extras = getIntent().getExtras();
        recipeID = extras.getLong("recipeID");

        init();
    }

    private void init(){
        TextView recipeID = (TextView) findViewById(R.id.recipeIDView);
        TextView recipeTitle = (TextView) findViewById(R.id.getTitleView);
        TextView recipeContent = (TextView) findViewById(R.id.getContentView);

        recipeID.setText(this.recipeID + "");

        Cursor cursor = getContentResolver().query(
                Uri.parse(RecipeContentProviderContract.RECIPELIST_URI + "/" + this.recipeID),
                projection, null, null, null);

        cursor.moveToFirst();
        recipeTitle.setText(cursor.getString(cursor.getColumnIndex(RecipeContentProviderContract.TITLE)));
        recipeContent.setText(cursor.getString(cursor.getColumnIndex(RecipeContentProviderContract.CONTENT)));

        int currRating = cursor.getInt(cursor.getColumnIndex(RecipeContentProviderContract.RATING));
        rating = currRating;
        final TextView progressText = (TextView) findViewById(R.id.progressTextView);

        final SeekBar ratingBar = (SeekBar) findViewById(R.id.ratingSeekBar);
        ratingBar.setMax(5);
        ratingBar.setProgress(currRating);
        progressText.setText(currRating + " / 5" );

        ratingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressText.setText(progress + "/" + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rating = seekBar.getProgress();
            }
        });


    }

    public void onSubmitRatingClick(View view) {
        ContentValues cValues = new ContentValues();
        cValues.put(RecipeContentProviderContract.RATING, rating);
        int rowAffected = getContentResolver().update(
                Uri.parse(RecipeContentProviderContract.RECIPELIST_URI + "/" + this.recipeID), cValues, null, null);

        if (rowAffected == 1){
            Toast.makeText(getApplicationContext(), "Rating Updated", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void onDeleteClick(View view) {

        int rowAffected = getContentResolver().delete(
                Uri.parse(RecipeContentProviderContract.RECIPELIST_URI + "/" + this.recipeID), null, null);

        if (rowAffected == 1){
            Toast.makeText(getApplicationContext(), "Recipe Deleted", Toast.LENGTH_LONG).show();
            finish();
        }

    }


    private String[] projection = {
            RecipeContentProviderContract._ID,
            RecipeContentProviderContract.TITLE,
            RecipeContentProviderContract.CONTENT,
            RecipeContentProviderContract.RATING
    };



}
