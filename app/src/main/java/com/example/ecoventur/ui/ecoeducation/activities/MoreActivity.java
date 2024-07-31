package com.example.ecoventur.ui.ecoeducation.activities;

import android.os.Bundle;

import com.example.ecoventur.ui.ecoeducation.Article;
import com.example.ecoventur.ui.ecoeducation.adapters.ArticlesAdapter;
import com.example.ecoventur.ui.ecoeducation.adapters.MoreAdapter;
import com.example.ecoventur.ui.ecoeducation.database.DataBaseHandler;
import com.example.ecoventur.ui.ecoeducation.models.Articles;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoventur.databinding.ActivityMoreBinding;

import com.example.ecoventur.R;

import java.util.ArrayList;

public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        RecyclerView universalRecyclerView = findViewById(R.id.universalRecyclerView);
        String op = getIntent().getStringExtra("op");
        ArrayList<Articles> universalList = new ArrayList<>();
        DataBaseHandler db = new DataBaseHandler(this);


        if(op.equals("t")){
            //load the tutorials
            universalList = db.getTutorials();
        }else{
            //load the articles
            universalList = db.getArticles();
        }
        universalRecyclerView.setAdapter(new MoreAdapter(this,universalList));
        universalRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
}