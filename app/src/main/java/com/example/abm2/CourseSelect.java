package com.example.abm2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abm2.Databases.TermsDatabase;

import java.util.Calendar;

public class CourseSelect extends AppCompatActivity {
    private TermsDatabase termsDatabase;
     EditText courseName,courseInsName,courseInsPhone,courseInsEmail;
     Spinner courseStatus;
     Button courseStart,courseEnd,courseSave;
     String termCourseId, termName;

    //Date
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_select);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            termsDatabase = new TermsDatabase(CourseSelect.this);
            courseSave = findViewById(R.id.courseSave);
            courseName = findViewById(R.id.courseNameField);
            courseStatus = findViewById(R.id.courseStatusField);
            courseStart = findViewById(R.id.courseStartDateField);
            courseEnd = findViewById(R.id.courseEndDateField);
            courseInsName = findViewById(R.id.instructorNameField);
            courseInsPhone = findViewById(R.id.instructorPhoneField);
            courseInsEmail = findViewById(R.id.instructorEmailField);

//Adding Items to spinner
        String[] items = new String[]{"Status","In progress", "Completed", "Dropped", "Plan to take"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        courseStatus.setAdapter(adapter);

            Intent i1 = getIntent();
        termCourseId = i1.getStringExtra("tID");
        termName = i1.getStringExtra("cTermName");




            courseEnd.setEnabled(false);

            courseStart.setOnClickListener(View -> startDatePicker(courseStart,courseEnd));
            courseEnd.setOnClickListener(View -> endDatePicker(courseEnd));

            courseSave.setOnClickListener(view -> {

                Intent i2 = new Intent(CourseSelect.this, TermsDetailedView.class);

                String cName = courseName.getText().toString();
                String cStart = courseStart.getText().toString();
                String cEnd = courseEnd.getText().toString();
                String cStatus = courseStatus.getSelectedItem().toString();
                String cInsName = courseInsName.getText().toString();
                String cInsPhone = courseInsPhone.getText().toString();
                String cInsEmail = courseInsEmail.getText().toString();
                String cTermId = termCourseId;

                termsDatabase.addNewCourse(cName,cStart,cEnd,cStatus,cInsName,cInsPhone,cInsEmail,cTermId);
                courseName.setText("");
                courseStart.setText("");
                courseEnd.setText("");
                courseStatus.setSelection(0);
                courseInsName.setText("");
                courseInsPhone.setText("");
                courseInsEmail.setText("");
                i2.putExtra("tDName",termName);
                Toast.makeText(this, termName, Toast.LENGTH_SHORT).show();
                startActivity(i2);
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
        DatePickerDialog endPick = new DatePickerDialog(this, (datePicker, year, month, day) -> e.setText(month + 1 +"/" + day + "/" + year), year, month, dayOfMonth);
        endPick.getDatePicker().setMinDate(System.currentTimeMillis());
        endPick.show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(CourseSelect.this, TermsDetailedView.class);
            i.putExtra("tDName",termName);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}