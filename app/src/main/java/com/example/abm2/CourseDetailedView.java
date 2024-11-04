package com.example.abm2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm2.Adapters.CourseRVAdapter;
import com.example.abm2.Adapters.RecycleViewInterface;
import com.example.abm2.Alert.StartEndAlert;
import com.example.abm2.Databases.TermsDatabase;
import com.example.abm2.Model.Assessments;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CourseDetailedView extends AppCompatActivity implements RecycleViewInterface {
    private ArrayList<Assessments> assessmentsArrayList;
    private TermsDatabase termsDatabase;
    private CourseRVAdapter courseRVAdapter;
    private RecyclerView homeRec;
    String termIdGet;
    String name;

    String id,s1,editedcourseName,noteId,aCourseName,termName,termCourseId,termNameById,startDateToTime,endDateToTime;
    private FloatingActionButton noteView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_detailed_view);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(true);
        termsDatabase = new TermsDatabase(CourseDetailedView.this);

        Intent i = getIntent();
        name = i.getStringExtra("cName");
        aCourseName = i.getStringExtra("tmName");
        editedcourseName = i.getStringExtra("cEName");

        if(name == null){
            if(editedcourseName != null){
                name = editedcourseName;
            }
            if (aCourseName != null) {
                name = aCourseName;

            }

        }
            ((TextView) findViewById(R.id.tDetailName)).setText(name);

        id = termsDatabase.getCourseId(name);
        noteId = termsDatabase.getCourseId(name);
        termName = i.getStringExtra("tName");
        termCourseId = termsDatabase.getCourseTermId(name);
         termNameById = termsDatabase.getTermName(termCourseId);
         noteView = findViewById(R.id.fltViewNotesBtn);
         //Temp Code 1
         noteView.setOnClickListener(view -> {
             Bundle bundle = new Bundle();
             bundle.putString("bundleId", id);
             bundle.putString("nameBundle",name);
             NoteDisplay noteDisplay = new NoteDisplay();
             noteDisplay.setArguments(bundle);
             noteDisplay.show(getSupportFragmentManager(), "display_dialog");

         });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        assessmentsArrayList = new ArrayList<>();

        assessmentsArrayList = termsDatabase.readAssessmentsByCourseId(id);
        courseRVAdapter = new CourseRVAdapter(assessmentsArrayList,CourseDetailedView.this,this);
        homeRec = findViewById(R.id.assessmentsRecDetail);

        homeRec.setLayoutManager(new LinearLayoutManager(this));
        homeRec.setItemAnimator(new DefaultItemAnimator());
        homeRec.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        homeRec.setAdapter(courseRVAdapter);

        //Setting DetailedView Fields
         s1 =((TextView) findViewById(R.id.tDetailName)).getText().toString();
        String[] s4 = termsDatabase.setCourseDetails2(s1);
        ((TextView)findViewById(R.id.detailCourseStartDate)).setText(s4[0]);
        ((TextView)findViewById(R.id.detailCourseEndDate)).setText(s4[1]);
        ((TextView)findViewById(R.id.courseStatus)).setText(s4[2]);
        ((TextView)findViewById(R.id.insNameDetail)).setText(s4[3]);
        ((TextView)findViewById(R.id.insEmailDetail)).setText(s4[4]);
        ((TextView)findViewById(R.id.insPhone)).setText(s4[5]);
        startDateToTime = ((TextView)findViewById(R.id.detailCourseStartDate)).getText().toString();
        endDateToTime = ((TextView)findViewById(R.id.detailCourseEndDate)).getText().toString();



    }

    //code for making menu items and events attached to them
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        menu.findItem(R.id.menuEdit).setTitle("Edit Course");
        menu.findItem(R.id.menuDelete).setTitle("Delete Course");
        menu.findItem(R.id.menuAlarmSet).setTitle("Set Course Alarms");
        menu.findItem(R.id.menuNoteCreate).setTitle("Create Course Notes");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuEdit) {

            Intent i = new Intent(this, CourseEdit.class);
            String nameD = ((TextView) findViewById(R.id.tDetailName)).getText().toString();
            i.putExtra("tDName", nameD);
            i.putExtra("termName",termNameById);
            startActivity(i);
            return true;
        }

         if (id==R.id.menuDelete)
        {
            AlertDialog dialog = deleteAlert();

                dialog.show();
        }
        if(id==R.id.menuAlarmSet){
            try {
                alarmSet();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        if(id==R.id.menuNoteCreate)
        {
            Bundle bundle = new Bundle();
            bundle.putString("IdNoteCreate", noteId);
            NoteCreateInput noteCreateInput = new NoteCreateInput();
            noteCreateInput.setArguments(bundle);
            noteCreateInput.show(getSupportFragmentManager(), "input_dialog");
        }
        if (item.getItemId() == android.R.id.home) {
            Intent i2 = new Intent(this, TermsDetailedView.class);
            String nameD = ((TextView) findViewById(R.id.tDetailName)).getText().toString();
            i2.putExtra("tDName", termName);
            i2.putExtra("tDBINAME",termNameById);
            startActivity(i2);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);}
    AlertDialog deleteAlert(){
        AlertDialog.Builder alertB = new AlertDialog.Builder(this);
        alertB.setMessage("Do you wish to proceed?");
        alertB.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                termsDatabase.deleteCourseCascadeAssessments(id);
                termsDatabase.deleteCourse(id);
                Toast.makeText(CourseDetailedView.this, "Courses and all assessments assigned have been deleted", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(CourseDetailedView.this, TermsDetailedView.class);
                i2.putExtra("tDName",termName);
                i2.putExtra("tDBINAME",termNameById);
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

    public void goToAssessmentsCreate(View view) {
        String s3 =((TextView) findViewById(R.id.tDetailName)).getText().toString();
        termIdGet = termsDatabase.getCourseId(s3);
        Intent i = new Intent(this, AssessmentCreate.class);
        i.putExtra("cID",termIdGet);
        i.putExtra("cName",name);
        i.putExtra("tDName",termName);
        startActivity(i);
    }
    @Override
    public void selectedItemClicked(int position) {

        Intent i = new Intent(CourseDetailedView.this, AssessmentDetailedView.class);
        String name2 = assessmentsArrayList.get(position).getAssessmentName();
        i.putExtra("tAName", name2);
        i.putExtra("cName",name);
        startActivity(i);

    }
    public void alarmSet() throws ParseException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(CourseDetailedView.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(CourseDetailedView.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Date parsedStartDate = dateFormat.parse(startDateToTime);
        Date parsedEndDate = dateFormat.parse(endDateToTime);

        assert parsedStartDate != null;
        long startTime = parsedStartDate.getTime();
        assert parsedEndDate != null;
        long endTime = parsedEndDate.getTime();

        // Create an Intent and PendingIntent
        Intent startIntent = new Intent(CourseDetailedView.this, StartEndAlert.class);
        startIntent.putExtra("alarmType","start");
        startIntent.putExtra("startTime",startTime);
        PendingIntent startPendingIntent = PendingIntent.getBroadcast(
                CourseDetailedView.this,
                ++MainActivity.numAlert,startIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );


        Intent endIntent = new Intent(CourseDetailedView.this, StartEndAlert.class);
        endIntent.putExtra("alarmType", "end");
        endIntent.putExtra("endTime", endTime);
        PendingIntent endPendingIntent = PendingIntent.getBroadcast(
                CourseDetailedView.this,
                ++MainActivity.numAlert,endIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Set the alarm
        AlarmManager alarmManager = (AlarmManager) CourseDetailedView.this.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (startTime < System.currentTimeMillis()){
                    Toast.makeText(this, "Course has already Started unable to set Alarm for Start Date", Toast.LENGTH_SHORT).show();
                }
                else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, startPendingIntent);
                    Toast.makeText(this, "Start Date Alarm Set", Toast.LENGTH_SHORT).show();
                }
                if(endTime < System.currentTimeMillis()){
                    Toast.makeText(this, "Course has already Ended unable to set Alarm for End Date", Toast.LENGTH_SHORT).show();

                }
                else {

                    alarmManager.set(AlarmManager.RTC_WAKEUP, endTime, endPendingIntent);
                    Toast.makeText(this, "End Date Alarm Set", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (startTime < System.currentTimeMillis()){
                    Toast.makeText(this, "Course has already Started unable to set Alarm for Start Date", Toast.LENGTH_SHORT).show();
                }
                else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTime, startPendingIntent);
                    Toast.makeText(this, "Start Date Alarm Set", Toast.LENGTH_SHORT).show();

                }
                if(endTime < System.currentTimeMillis()){
                    Toast.makeText(this, "Course has already Ended unable to set Alarm for End Date", Toast.LENGTH_SHORT).show();

                }else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, endTime, endPendingIntent);
                    Toast.makeText(this, "End Date Alarm Set", Toast.LENGTH_SHORT).show();

                }
            }
        }

    }
}