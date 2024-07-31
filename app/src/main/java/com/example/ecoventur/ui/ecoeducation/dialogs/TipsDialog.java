package com.example.ecoventur.ui.ecoeducation.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ecoventur.R;
import com.example.ecoventur.ui.ecoeducation.database.DataBaseHandler;
import com.example.ecoventur.ui.ecoeducation.database.TinyDB;
import com.example.ecoventur.ui.ecoeducation.models.Tips;
import com.google.android.material.button.MaterialButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TipsDialog extends Dialog {
    private Tips tip;
    public TipsDialog(Context context, Tips givenTip) {
        super(context);
        tip = givenTip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips);
        MaterialButton more = findViewById(R.id.more);
        MaterialButton cancel = findViewById(R.id.cancel);
        TextView tips = findViewById(R.id.tip);
        LottieAnimationView animation = findViewById(R.id.animation);
        DataBaseHandler db = new DataBaseHandler(getContext());
        db.updateTip(tip.getTip());
        TinyDB tinyDB = new TinyDB(getContext());
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);

        tinyDB.putString("date",formattedDate);

        tips.setText(tip.getTip());

        animation.setOnClickListener(view->{
            if(animation.isAnimating()){
                animation.pauseAnimation();
            }else{
                animation.playAnimation();
            }
        });
        cancel.setOnClickListener(view->{
            dismiss();
        });
        more.setOnClickListener(view->{
            //open url with the appropriate article
            String url = tip.getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            getContext().startActivity(intent);
        });
    }

}
