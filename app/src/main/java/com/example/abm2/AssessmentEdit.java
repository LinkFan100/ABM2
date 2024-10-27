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
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abm2.Databases.TermsDatabase;

import java.util.Calendar;
import java.util.Objects;

public class AssessmentEdit extends AppCompatActivity {
    private TermsDatabase termsDatabase;
    private EditText assessmentName,assessmentInfo;
    private Button assessmentStart,assessmentEnd, assessmentUpdate;
    private String nameAssessment,courseName;
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
        termsDatabase = new TermsDatabase(AssessmentEdit.this);
        Intent I1 = getIntent();
         nameAssessment = I1.getStringExtra("tDName");
         courseName = I1.getStringExtra("cName");
        assessmentName = findViewById(R.id.assessmentNameField);
        assessmentType = findViewById(R.id.assessmentTypeField);
        assessmentInfo = findViewById(R.id.assessmentInfoField);
        assessmentStart = findViewById(R.id.assessmentStartDateBtn);
        assessmentEnd = findViewById(R.id.assessmentEndDateBtn);
        assessmentUpdate = findViewById(R.id.assessmentSaveBtn);
        assessmentEnd.setEnabled(true);
        assessmentUpdate.setText("Update Assessment");
        assessmentStart.setOnClickListener(view -> startDatePicker(assessmentStart));
        assessmentEnd.setOnClickListener(view -> endDatePicker(assessmentEnd));
        //Adding Items to spinner
        String[] items = new String[]{"Type","Performance","Objective"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        assessmentType.setAdapter(adapter);

        //Populating Fields from Database
        String[] s4 = termsDatabase.setAssessmentDetails(nameAssessment);
        assessmentName.setText(nameAssessment);
        assessmentStart.setText(s4[0]);
        assessmentEnd.setText(s4[1]);

        if (Objects.equals(s4[2], items[1])){
            assessmentType.setSelection(1);
        } else if (Objects.equals(s4[2], items[2])) {
            assessmentType.setSelection(2);
        } else if (!s4[2].equals(items[1]) && !s4[2].equals(items[2])) {
            assessmentType.setSelection(0);

        }
        assessmentInfo.setText(s4[3]);


        assessmentUpdate.setOnClickListener(view -> {
            Intent i = new Intent(AssessmentEdit.this,AssessmentDetailedView.class);
            termsDatabase.updateAssessment(nameAssessment,assessmentName.getText().toString(),assessmentStart.getText().toString(),
                    assessmentEnd.getText().toString(),assessmentType.getSelectedItem().toString(),assessmentInfo.getText().toString());
            i.putExtra("tAName",assessmentName.getText().toString());
            i.putExtra("cName",courseName);
            startActivity(i);
        });

    }
    private void startDatePicker(Button s){
        DatePickerDialog startPick = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            s.setText(month + 1 + "/" + day + "/" + year);
        }, year, month, dayOfMonth);
        startPick.getDatePicker().setMinDate(System.currentTimeMillis());
        startPick.show();
    }
    private void endDatePicker(Button e){
        DatePickerDialog endPick = new DatePickerDialog(this, (datePicker, year, month, day) -> e.setText(month + 1 +"/" + day + "/" + year), year, month, dayOfMonth);
        endPick.getDatePicker().setMinDate(System.currentTimeMillis());
        endPick.show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this, AssessmentDetailedView.class);
            i.putExtra("tAName",assessmentName.getText().toString());
            i.putExtra("cName",courseName);
            startActivity(i);
            this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);}

}