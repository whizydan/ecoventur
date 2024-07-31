package com.example.ecoventur;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ecoventur.databinding.ActivityMainBinding;
import com.example.ecoventur.ui.ecoeducation.database.DataBaseHandler;
import com.example.ecoventur.ui.ecoeducation.database.TinyDB;
import com.example.ecoventur.ui.ecoeducation.dialogs.TipsDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TinyDB tinyDB = new TinyDB(this);
        DataBaseHandler db = new DataBaseHandler(this);
        if(!Objects.equals(tinyDB.getString("started"), "yes")){
            db.addTips();
            db.addQuizes();
            db.addContent();
            tinyDB.putString("started","yes");
        }
        //show the tip
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        if(!Objects.equals(tinyDB.getString("date"), formattedDate)){
            TipsDialog tipsDialog = new TipsDialog(this,db.getTip());
            tipsDialog.show();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_transit, R.id.navigation_greenspace, R.id.navigation_user, R.id.navigation_ecoeducation, R.id.navigation_ecorewards)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}