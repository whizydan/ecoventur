package com.example.ecoventur.ui.ecoeducation.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ecoventur.R;
import com.example.ecoventur.ui.ecoeducation.database.DataBaseHandler;
import com.example.ecoventur.ui.ecoeducation.database.TinyDB;
import com.example.ecoventur.ui.ecoeducation.models.Tips;
import com.google.android.material.button.MaterialButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BadgeDialog extends Dialog {
    private Tips tip;
    public BadgeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badge);
        TextView name = findViewById(R.id.name);
        TextView desc = findViewById(R.id.desc);

    }

}
