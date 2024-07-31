package com.example.ecoventur.ui.ecoeducation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecoventur.R;
import com.example.ecoventur.ui.ecoeducation.activities.MoreActivity;
import com.example.ecoventur.ui.ecoeducation.activities.QuizCategoryActivity;
import com.example.ecoventur.ui.ecoeducation.activities.ScoreBoardActivity;
import com.example.ecoventur.ui.ecoeducation.adapters.ArticlesAdapter;
import com.example.ecoventur.ui.ecoeducation.database.DataBaseHandler;
import com.example.ecoventur.ui.ecoeducation.models.Articles;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EcoEducationFragment extends Fragment {

    private DatabaseReference articleRef;
    private DatabaseReference tutorialRef;
    private RecyclerView articleRecyclerView;
    private RecyclerView tutorialRecyclerView;
    public static EcoEducationFragment newInstance() {
        return new EcoEducationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ecoeducation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton quiz = view.findViewById(R.id.QuizText);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView moreArticles = view.findViewById(R.id.moreArticles);
        TextView moreTutorials = view.findViewById(R.id.moreTutorials);
        RecyclerView articleRecyclerView = view.findViewById(R.id.articleRecyclerView);
        RecyclerView tutorialRecyclerView = view.findViewById(R.id.tutorialRecyclerView);
        DataBaseHandler db = new DataBaseHandler(requireContext());
        ArrayList<Articles> allArticles = db.getArticles();
        ArrayList<Articles> allTutorials = db.getTutorials();

        tutorialRecyclerView.setAdapter(new ArticlesAdapter(requireContext(),allTutorials));
        tutorialRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));

        articleRecyclerView.setAdapter(new ArticlesAdapter(requireContext(),allArticles));
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));

        quiz.setOnClickListener(quizView->{
            Intent intent = new Intent(requireContext(), QuizCategoryActivity.class);
            startActivity(intent);
        });
        imageView.setOnClickListener(viewScoreBoard->{
            Intent intent = new Intent(requireContext(), ScoreBoardActivity.class);
            startActivity(intent);
        });
        moreArticles.setOnClickListener(viewMoreArticles->{
            Intent intent = new Intent(requireContext(), MoreActivity.class);
            intent.putExtra("op","a");
            startActivity(intent);

        });
        moreTutorials.setOnClickListener(viewMoreTutorials->{
            Intent intent = new Intent(requireContext(), MoreActivity.class);
            intent.putExtra("op","t");
            startActivity(intent);
        });
    }
}