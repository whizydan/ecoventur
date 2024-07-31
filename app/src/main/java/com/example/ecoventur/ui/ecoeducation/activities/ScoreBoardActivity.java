package com.example.ecoventur.ui.ecoeducation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ecoventur.R;
import com.example.ecoventur.ui.ecoeducation.adapters.BadgeAdapter;
import com.example.ecoventur.ui.ecoeducation.models.Badges;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ArrayList<Badges> badges = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(uid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    badges.add(snapshot.getValue(Badges.class));
                }
                recyclerView.setAdapter(new BadgeAdapter(ScoreBoardActivity.this,badges));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ScoreBoardActivity.this,error.getDetails(),Toast.LENGTH_LONG).show();
            }
        });

    }
}