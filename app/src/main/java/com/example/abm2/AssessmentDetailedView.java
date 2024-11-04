package com.example.abm2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abm2.Alert.StartEndAlert;
import com.example.abm2.Databases.TermsDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetailedView extends AppCompatActivity {
    private TermsDatabase termsDatabase;
    private String assessmentid,s1,name,courseName,startDateToTime,endDateToTime;
    FloatingActionButton assessmentViewNotes;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assessment_detailed_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        termsDatabase = new TermsDatabase(AssessmentDetailedView.this);
        Intent i = getIntent();
        name = i.getStringExtra("tAName");
        courseName = i.getStringExtra("cName");
        assessmentid = termsDatabase.getAssessmentId(name);
        ((TextView) findViewById(R.id.assessmentDetailedTitle)).setText(name);
        assessmentViewNotes = findViewById(R.id.fltViewNotesBtn2);
        assessmentViewNotes.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("bundleAssessmentId", assessmentid);
            bundle.putString("nameBundle",name);
            NoteDisplay noteDisplay = new NoteDisplay();
            noteDisplay.setArguments(bundle);
            noteDisplay.show(getSupportFragmentManager(), "display_dialog");
        });

        //Setting DetailedView Fields
         s1 = ((TextView) findViewById(R.id.assessmentDetailedTitle)).getText().toString();
        String[] s4 = termsDatabase.setAssessmentDetails(s1);
        ((TextView)findViewById(R.id.assessmentDetailedStart)).setText(s4[0]);
        ((TextView)findViewById(R.id.assessmentDetailedEnd)).setText(s4[1]);
        ((TextView)findViewById(R.id.assessmentDetailedType)).setText(s4[2]);
        ((TextView)findViewById(R.id.assessmentInfoText)).setText(s4[3]);
        startDateToTime = ((TextView)findViewById(R.id.assessmentDetailedStart)).getText().toString();
        endDateToTime = ((TextView)findViewById(R.id.assessmentDetailedEnd)).getText().toString();

    }

//code for making menu items and events attached to them
public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.menu,menu);
    menu.findItem(R.id.menuEdit).setTitle("Edit Assessment");
    menu.findItem(R.id.menuDelete).setTitle("Delete Assessment");
    menu.findItem(R.id.menuAlarmSet).setTitle("Set Assessment Alarms");
    menu.findItem(R.id.menuNoteCreate).setTitle("Create Assessment Notes");
    return true;
}
@Override
public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();

    if (id == R.id.menuEdit) {

        Intent i = new Intent(this, AssessmentEdit.class);
        String nameD = ((TextView) findViewById(R.id.assessmentDetailedTitle)).getText().toString();
        i.putExtra("tDName", nameD);
        i.putExtra("cName", courseName);
        startActivity(i);
        return true;
    }

    else if (id==R.id.menuDelete)
    {
        AlertDialog dialog = deleteAlert();
            dialog.show();
    }
    else if (id==R.id.menuAlarmSet)
    {
        try {
            alarmSet();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    if(id==R.id.menuNoteCreate)
    {
        Bundle bundle = new Bundle();
        bundle.putString("IdAssessmentNoteCreate", assessmentid);
        NoteCreateInput noteCreateInput = new NoteCreateInput();
        noteCreateInput.setArguments(bundle);
        noteCreateInput.show(getSupportFragmentManager(), "input_dialog");
    }
    if (item.getItemId() == android.R.id.home) {
        Intent i = new Intent(this, CourseDetailedView.class);
        i.putExtra("cName", courseName);
        startActivity(i);
        this.finish();
        return true;
    }


    return super.onOptionsItemSelected(item);}
AlertDialog deleteAlert(){
    AlertDialog.Builder alertB = new AlertDialog.Builder(this);
    alertB.setMessage("Do you wish to proceed?");
    alertB.setPositiveButton("Yes", (dialogInterface, i) -> {
        termsDatabase.deleteAssessment(assessmentid);
        Intent i2 = new Intent(AssessmentDetailedView.this, CourseDetailedView.class);
        i2.putExtra("cName", courseName);
        Toast.makeText(AssessmentDetailedView.this, "Assessment Has been deleted", Toast.LENGTH_SHORT).show();
        startActivity(i2);

    });
    alertB.setNegativeButton("No", (dialogInterface, i) -> {
        AlertDialog dialog = deleteAlert();
        dialog.dismiss();
    });
    return alertB.create();
}
    public void alarmSet() throws ParseException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(AssessmentDetailedView.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(AssessmentDetailedView.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
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
        Intent startAssessmentIntent = new Intent(AssessmentDetailedView.this, StartEndAlert.class);
        startAssessmentIntent.putExtra("alarmType","startAssess");
        PendingIntent startPendingIntent = PendingIntent.getBroadcast(
                AssessmentDetailedView.this,
                ++MainActivity.numAlert,startAssessmentIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );


        Intent endAssessmentIntent = new Intent(AssessmentDetailedView.this, StartEndAlert.class);
        endAssessmentIntent.putExtra("alarmType", "endAssess");
        PendingIntent endPendingIntent = PendingIntent.getBroadcast(
                AssessmentDetailedView.this,
                ++MainActivity.numAlert,endAssessmentIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Set the alarm
        AlarmManager alarmManager = (AlarmManager) AssessmentDetailedView.this.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (startTime < System.currentTimeMillis()){
                    Toast.makeText(this, "Assessment has already started unable to set Alarm for Start Date", Toast.LENGTH_SHORT).show();
                }
                else if(startTime > System.currentTimeMillis()) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, startPendingIntent);
                    Toast.makeText(this, "Start Date Alarm Set", Toast.LENGTH_SHORT).show();
                }
                if(endTime < System.currentTimeMillis()){
                    Toast.makeText(this, "Assessment has already ended unable to set Alarm for End Date", Toast.LENGTH_SHORT).show();

                }
                else if(endTime > System.currentTimeMillis()){

                    alarmManager.set(AlarmManager.RTC_WAKEUP, endTime, endPendingIntent);
                    Toast.makeText(this, "End Date Alarm Set", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                if (startTime < System.currentTimeMillis()){
                    Toast.makeText(this, "Assessment has already started unable to set Alarm for Start Date", Toast.LENGTH_SHORT).show();
                }
                else if(startTime > System.currentTimeMillis()){
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTime, startPendingIntent);
                    Toast.makeText(this, "Start Date Alarm Set", Toast.LENGTH_SHORT).show();

                }
                if(endTime < System.currentTimeMillis()){
                    Toast.makeText(this, "Assessment has already ended unable to set Alarm for End Date", Toast.LENGTH_SHORT).show();

                }else if(endTime > System.currentTimeMillis()){
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, endTime, endPendingIntent);
                    Toast.makeText(this, "End Date Alarm Set", Toast.LENGTH_SHORT).show();

                }
            }
        }

    }
}
