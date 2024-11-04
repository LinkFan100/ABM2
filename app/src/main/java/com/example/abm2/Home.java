package com.example.abm2;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abm2.Adapters.HomeRVAdapter;
import com.example.abm2.Adapters.RecycleViewInterface;
import com.example.abm2.Databases.TermsDatabase;
import com.example.abm2.Model.Terms;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements RecycleViewInterface {

    private ArrayList<Terms> termsArray;
    private TermsDatabase termsDatabase;
    private HomeRVAdapter homeRVAdapter;
    private RecyclerView homeRec;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);






        termsArray = new ArrayList<>();
        termsDatabase = new TermsDatabase(Home.this);

        termsArray = termsDatabase.readTerms();
        homeRVAdapter = new HomeRVAdapter(termsArray, Home.this, this);
        homeRec = findViewById(R.id.homeRecView);
        homeRec.setLayoutManager(new LinearLayoutManager(this));
        homeRec.setItemAnimator(new DefaultItemAnimator());
        homeRec.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        homeRec.setAdapter(homeRVAdapter);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void toTermCreateAct(View view) {
        Intent i = new Intent(Home.this, TermCreate.class);
        startActivity(i);
    }

    @Override
    public void selectedItemClicked(int position) {

        Intent i = new Intent(Home.this, TermsDetailedView.class);
        String name = termsArray.get(position).getTermName();
        int id = Integer.parseInt(termsDatabase.getTermId(name));
        i.putExtra("tName", name);
        i.putExtra("idTerm",id);
        startActivity(i);


    }

}

