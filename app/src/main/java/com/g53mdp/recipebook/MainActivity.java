package com.g53mdp.recipebook;


import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private String currSortBy = RecipeContentProviderContract.TITLE;

    Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getContentResolver().
                registerContentObserver(
                        RecipeContentProviderContract.MAIN_URI,
                        true,
                        new ChangeObserver(h));

        queryAllRecipes(currSortBy);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupSorting);
        radioGroup.check(R.id.radioTitle);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioTitle){
                    currSortBy = RecipeContentProviderContract.TITLE;
                } else if (checkedId == R.id.radioRating){
                    currSortBy = RecipeContentProviderContract.RATING + " DESC";
                }
                queryAllRecipes(currSortBy);
                Log.d("currSortBy", currSortBy);
            }
        });

        ListView lv = (ListView) findViewById(R.id.MainListView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent recipeDetails = new Intent(getApplicationContext(), RecipeDeatilsActivity.class);
                Bundle extras = new Bundle();
                extras.putLong("recipeID", id);

                recipeDetails.putExtras(extras);
                startActivity(recipeDetails);

            }
        });

    }

    public void onClickAddNewRecipe(View view) {
        Intent addNewRecipe = new Intent(getApplicationContext(), AddRecipeActivity.class);
        startActivity(addNewRecipe);

   }


    protected class ChangeObserver extends ContentObserver {

        public ChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            queryAllRecipes(currSortBy);
        }
    }


    private void queryAllRecipes(String orderBy){

        Cursor cursor = getContentResolver().query(
                RecipeContentProviderContract.RECIPELIST_URI, projection,
                null, null, orderBy);


        SimpleCursorAdapter dataAdapter;
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.data_layout,
                cursor, colDisplay, disViewID, 0
        );


        ListView lv = (ListView) findViewById(R.id.MainListView);
        lv.setAdapter(dataAdapter);

    }

    private String[] projection = {
            RecipeContentProviderContract._ID,
            RecipeContentProviderContract.TITLE,
            RecipeContentProviderContract.CONTENT,
            RecipeContentProviderContract.RATING
    };

    private  String[] colDisplay= {
            RecipeContentProviderContract._ID,
            RecipeContentProviderContract.TITLE,
            RecipeContentProviderContract.RATING
    };

    private int[] disViewID = {
            R.id.idView,
            R.id.titleView,
            R.id.ratingView
    };
}
