package com.example.ecoventur.ui.ecoeducation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ecoventur.R;
import com.example.ecoventur.ui.ecoeducation.adapters.CategoriesAdapter;
import com.example.ecoventur.ui.ecoeducation.database.DataBaseHandler;

public class QuizCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);
        RecyclerView categories = findViewById(R.id.categories);
        DataBaseHandler db = new DataBaseHandler(this);

        categories.setAdapter(new CategoriesAdapter(this,db.getQuizCategories()));
        categories.setLayoutManager(new GridLayoutManager(this,3));
    }
}