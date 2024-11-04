package com.example.abm2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm2.Adapters.RecycleViewInterface;
import com.example.abm2.Adapters.TermDetailedRVAdapter;
import com.example.abm2.Databases.TermsDatabase;
import com.example.abm2.Model.Courses;

import java.util.ArrayList;

public class TermsDetailedView extends AppCompatActivity implements RecycleViewInterface {
    private ArrayList<Courses> coursesArrayList;
    private TermsDatabase termsDatabase;
    private TermDetailedRVAdapter termDetailedRVAdapter;
    private RecyclerView homeRec;
    String termIdGet;
    String name;
    int id;

    Button createCourse;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_term_detailed_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        termsDatabase = new TermsDatabase(TermsDetailedView.this);
        Intent i = getIntent();
         name = i.getStringExtra("tName");
         id  = i.getIntExtra("idTerm",0);
         String editedTermName = i.getStringExtra("editedTermName");
         String cDTermName = i.getStringExtra("tDName");
         String tNBID = i.getStringExtra("tDBINAME");
        if(name == null){
            if(editedTermName != null){
                name = editedTermName;
            }
            if (cDTermName != null) {
                name = cDTermName;

            }
            if(tNBID != null){
                name = tNBID;
            }

        }
        ((TextView) findViewById(R.id.tDetailName)).setText(name);
        termIdGet = termsDatabase.getTermId(name);


        coursesArrayList = new ArrayList<>();


        coursesArrayList = termsDatabase.readCoursesByTermId(Integer.parseInt(termIdGet));
        termDetailedRVAdapter = new TermDetailedRVAdapter(coursesArrayList, TermsDetailedView.this, this);
        homeRec = findViewById(R.id.termCourseRecDetail);
        homeRec.setLayoutManager(new LinearLayoutManager(this));
        homeRec.setItemAnimator(new DefaultItemAnimator());
        homeRec.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        homeRec.setAdapter(termDetailedRVAdapter);

        createCourse = findViewById(R.id.addCourses);
        //Setting Start Date and End date
        String s1 = name;
        String s2 = termsDatabase.setTermStartEnd(s1);
        String sub1 = s2.substring(0, s2.indexOf(" "));
        String sub2 = s2.substring(s2.indexOf(" ")+1);
        ((TextView)findViewById(R.id.detailStartDate)).setText(sub1);
        ((TextView)findViewById(R.id.detailEndDate)).setText(sub2);

        //Getting Term Id



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
    }
    //code for making menu items and events attached to them
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.menuEdit).setTitle("Edit Term");
        menu.findItem(R.id.menuDelete).setTitle("Delete Term");
        menu.findItem(R.id.menuAlarmSet).setVisible(false);
        menu.findItem(R.id.menuNoteCreate).setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuEdit) {

            Intent i = new Intent(this, TermEdit.class);
            String nameD = ((TextView) findViewById(R.id.tDetailName)).getText().toString();
            String termID = termsDatabase.getTermId(nameD);
            i.putExtra("tDName", nameD);
            i.putExtra("termID",termID);
            startActivity(i);
            return true;
        }

        else if (id==R.id.menuDelete)
        {
            AlertDialog dialog = deleteAlert();

            if(termDetailedRVAdapter.getItemCount() != 0) {
                Toast.makeText(this,"Term has courses",Toast.LENGTH_LONG).show();
            }
            else
                dialog.show();
        }
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this,Home.class);
            startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);


    }
    AlertDialog deleteAlert(){
        AlertDialog.Builder alertB = new AlertDialog.Builder(this);
        alertB.setMessage("Do you wish to proceed?");
        alertB.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                termsDatabase.deleteTerm(name);
                Toast.makeText(TermsDetailedView.this, name + " has been Deleted", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(TermsDetailedView.this, Home.class);
                startActivity(i2);

            }
        });
        alertB.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog dialog = deleteAlert();
                dialog.dismiss();
            }
        });
        return alertB.create();
    }

    public void goToCoursesCreate(View view) {
        String s3 =((TextView) findViewById(R.id.tDetailName)).getText().toString();
        termIdGet = termsDatabase.getTermId(s3);
        Intent i = new Intent(this, CourseSelect.class);
        i.putExtra("tID",termIdGet);
        i.putExtra("cTermName", name);
        startActivity(i);
    }
    @Override
    public void selectedItemClicked(int position) {

        Intent i = new Intent(TermsDetailedView.this, CourseDetailedView.class);
        String name = coursesArrayList.get(position).getCourseName();
        i.putExtra("cName", name);
        i.putExtra("tName",((TextView) findViewById(R.id.tDetailName)).getText().toString());
        startActivity(i);

    }
}