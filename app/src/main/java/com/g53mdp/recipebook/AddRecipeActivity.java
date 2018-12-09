package com.g53mdp.recipebook;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
    }

    public void onSubmitClick(View view) {

        EditText addTitle = (EditText) findViewById(R.id.editAddTitle);
        EditText addContent = (EditText) findViewById(R.id.editAddContent);

        if (addTitle.getText().toString().equals("") || addContent.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Title or Content Cannot Be Empty", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues cValues = new ContentValues();
        cValues.put(RecipeContentProviderContract.TITLE, addTitle.getText().toString());
        cValues.put(RecipeContentProviderContract.CONTENT, addContent.getText().toString());

        getContentResolver().insert(RecipeContentProviderContract.RECIPELIST_URI, cValues);

        finish();
    }
}
