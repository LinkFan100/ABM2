package com.example.abm2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abm2.Databases.TermsDatabase;

import java.util.Calendar;

public class AssessmentCreate extends AppCompatActivity {
    private TermsDatabase termsDatabase;
    private EditText assessmentName,assessmentInfo;
    private Button assessmentStart,assessmentEnd,assessmentSave;
    private String assessmentCourseId,courseNameD,termName;
    private Spinner assessmentType;

    //Date
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assessment_create);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        termsDatabase = new TermsDatabase(AssessmentCreate.this);
        Intent I1 = getIntent();
         assessmentCourseId = I1.getStringExtra("cID");
        courseNameD = I1.getStringExtra("cName");
        termName = I1.getStringExtra("tDName");

        assessmentName = findViewById(R.id.assessmentNameField);
        assessmentType = findViewById(R.id.assessmentTypeField);
        assessmentInfo = findViewById(R.id.assessmentInfoField);
        assessmentStart = findViewById(R.id.assessmentStartDateBtn);
        assessmentEnd = findViewById(R.id.assessmentEndDateBtn);
        assessmentSave = findViewById(R.id.assessmentSaveBtn);
        assessmentEnd.setEnabled(false);
        assessmentStart.setOnClickListener(view -> startDatePicker(assessmentStart,assessmentEnd));
        assessmentEnd.setOnClickListener(view -> endDatePicker(assessmentEnd));
        //Adding Items to spinner
        String[] items = new String[]{"Type","Performance","Objective"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        assessmentType.setAdapter(adapter);


        assessmentSave.setOnClickListener(view -> {
            termsDatabase.addNewAssessment(assessmentName.getText().toString(),assessmentStart.getText().toString(),
                    assessmentEnd.getText().toString(),assessmentType.getSelectedItem().toString(),assessmentInfo.getText().toString(),assessmentCourseId);
            Intent i = new Intent(this,CourseDetailedView.class);
            i.putExtra("tmName",courseNameD);
            i.putExtra("tName",termName);
            startActivity(i);
        });

    }
    private void startDatePicker(Button s, Button e){
        DatePickerDialog startPick = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            s.setText(month + 1 + "/" + day + "/" + year);
            e.setEnabled(true);
        }, year, month, dayOfMonth);
        startPick.getDatePicker().setMinDate(System.currentTimeMillis());
        startPick.show();
    }
    private void endDatePicker(Button e){
        DatePickerDialog endPick = new DatePickerDialog(this, (datePicker, year, month, day) -> e.setText(month+1 +"/" + day + "/" + year), year, month, dayOfMonth);
        endPick.getDatePicker().setMinDate(System.currentTimeMillis());
        endPick.show();

    }
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this, CourseDetailedView.class);
            i.putExtra("tName",courseNameD);

            this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);}

}
