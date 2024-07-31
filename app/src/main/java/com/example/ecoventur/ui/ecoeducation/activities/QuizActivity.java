package com.example.ecoventur.ui.ecoeducation.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecoventur.R;
import com.example.ecoventur.ui.ecoeducation.adapters.QuizAdapter;
import com.example.ecoventur.ui.ecoeducation.database.DataBaseHandler;
import com.example.ecoventur.ui.ecoeducation.dialogs.BadgeDialog;
import com.example.ecoventur.ui.ecoeducation.models.Badges;
import com.example.ecoventur.ui.ecoeducation.models.Quiz;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class QuizActivity extends AppCompatActivity {

    String category = "";
    TextView question;
    ArrayList<Quiz> quizes;
    int currentQuiz = 0;
    int totalCorrect = 0;
    MaterialButton a;
    MaterialButton b;
    MaterialButton c;
    MaterialButton d;
    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question = findViewById(R.id.question);
        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);

        db = new DataBaseHandler(this);
        category = getIntent().getStringExtra("category");
        quizes = db.getQuizes(category);

        //default view is question 0
        showNext();

        a.setOnClickListener(view->{
            onChoiceClicked("a");
        });
        b.setOnClickListener(view->{
            onChoiceClicked("b");
        });
        c.setOnClickListener(view->{
            onChoiceClicked("c");
        });
        d.setOnClickListener(view->{
            onChoiceClicked("d");
        });
    }

    private void showNext(){
        //load the next question to the UI(s)
        if(quizes != null){
            Quiz currentQuestion = quizes.get(currentQuiz);
            question.setText(currentQuestion.getQuestion());
            a.setText(currentQuestion.getA());
            b.setText(currentQuestion.getB());
            c.setText(currentQuestion.getC());
            d.setText(currentQuestion.getD());
        }else{
            Toast.makeText(this,"Empty list",Toast.LENGTH_LONG).show();
        }
    }
    private void onChoiceClicked(String choice){
        //update that this item has been answered before incrementing the counter
        setProgress();

        //check if answer is correct
        if(quizes.get(currentQuiz).getAnswer().equals(choice)){
            //correct choice, change colour of buttons
            totalCorrect ++;
            switch (choice){
                case "a":
                    a.setBackgroundColor(Color.GREEN);
                    break;
                case "b":
                    b.setBackgroundColor(Color.GREEN);
                    break;
                case "c":
                    c.setBackgroundColor(Color.GREEN);
                    break;
                case "d":
                    d.setBackgroundColor(Color.GREEN);
                    break;
            }
        }else{
            //wrong choice ,highlight selected choice in bright red and show the answer in bright green colour
            switch (choice){
                case "a":
                    a.setBackgroundColor(Color.RED);
                    break;
                case "b":
                    b.setBackgroundColor(Color.RED);
                    break;
                case "c":
                    c.setBackgroundColor(Color.RED);
                    break;
                case "d":
                    d.setBackgroundColor(Color.RED);
                    break;
            }
            switch (quizes.get(currentQuiz).getAnswer()){
                case "a":
                    a.setBackgroundColor(Color.GREEN);
                    break;
                case "b":
                    b.setBackgroundColor(Color.GREEN);
                    break;
                case "c":
                    c.setBackgroundColor(Color.GREEN);
                    break;
                case "d":
                    d.setBackgroundColor(Color.GREEN);
                    break;
            }
            //set pointer to the next item and

        }
        //check if the end of questions
        if(quizes.size() < currentQuiz){
            currentQuiz = currentQuiz + 1;
            showNext();
        } else if (quizes.size() == currentQuiz) {
            //at exactly the last item,show how many success if successes are 8 award badge,then save to firebase database
            //repurposed this dialog to show successful completion of {courses} and out of....
            BadgeDialog badgeDialog = new BadgeDialog(this);
            badgeDialog.show();
            currentQuiz = currentQuiz + 1;
            showNext();
            //check if all corect quizes add p to more than 7
            if(totalCorrect > 7){
                updateFirebase();
            }
        }

    }
    private void updateFirebase(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        Badges badge = new Badges();
        badge.setDate(Calendar.getInstance().getTime().toString());
        badge.setName("Green Card");
        badge.setImage("https://previews.123rf.com/images/arcady31/arcady311812/arcady31181200155/114186175-green-badge-icon.jpg");
        badge.setDescription("Having completed 8+ quizes in a week");
        String GUID = generateRandomGUID();
        badge.setId(GUID);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(uid);
        reference.child(GUID).setValue(badge);

    }
    private void setProgress(){
        //get the week number then display badges
        db.updateProgress(quizes.get(currentQuiz).getId());
    }
    public static String generateRandomGUID() {
        UUID uuid = UUID.randomUUID();
        String fullUUID = uuid.toString();
        return fullUUID.substring(0, 12);
    }
}