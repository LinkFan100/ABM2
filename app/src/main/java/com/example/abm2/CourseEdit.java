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
import java.util.Objects;

public class CourseEdit extends AppCompatActivity {
    private TermsDatabase termsDatabase;
     EditText courseName,courseInsName,courseInsPhone,courseInsEmail;
     Spinner courseStatus;
     Button courseStart,courseEnd, courseUpdate;
     String termCourseId, termEName;

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
        Intent iE = getIntent();
        String cDName = iE.getStringExtra("tDName");

            termsDatabase = new TermsDatabase(CourseEdit.this);
            courseUpdate = findViewById(R.id.courseSave);
            courseName = findViewById(R.id.courseNameField);
            courseStatus = findViewById(R.id.courseStatusField);
            courseStart = findViewById(R.id.courseStartDateField);
            courseEnd = findViewById(R.id.courseEndDateField);
            courseInsName = findViewById(R.id.instructorNameField);
            courseInsPhone = findViewById(R.id.instructorPhoneField);
            courseInsEmail = findViewById(R.id.instructorEmailField);
            courseUpdate.setText("Update Course");

//Adding Items to spinner
        String[] items = new String[]{"Status","In progress", "Completed", "Dropped", "Plan to take"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        courseStatus.setAdapter(adapter);

            Intent i1 = getIntent();
        termCourseId = i1.getStringExtra("tID");
         termEName = i1.getStringExtra("termName");

            courseEnd.setEnabled(false);

            courseStart.setOnClickListener(View -> startDatePicker(courseStart,courseEnd));
            courseEnd.setOnClickListener(View -> endDatePicker(courseEnd));

            //Setting Edited Fields
        String[] s4 = termsDatabase.setCourseDetails2(cDName);
        courseName.setText(cDName);
        courseStart.setText(s4[0]);
        courseEnd.setText(s4[1]);
        if (Objects.equals(s4[2], items[1])){
            courseStatus.setSelection(1);
        } else if (Objects.equals(s4[2], items[2])) {
            courseStatus.setSelection(2);

        } else if (Objects.equals(s4[2], items[3])) {
            courseStatus.setSelection(3);

        } else if (Objects.equals(s4[2], items[4])) {
            courseStatus.setSelection(4);
        }
        else if(!s4[2].equals(items[1]) && !s4[2].equals(items[2])&& !s4[2].equals(items[3])&& !s4[2].equals(items[4])){}
        courseStatus.setSelection(0);


        courseInsName.setText(s4[3]);
        courseInsEmail.setText(s4[4]);
        courseInsPhone.setText(s4[5]);

            courseUpdate.setOnClickListener(view -> {

                Intent i = new Intent(CourseEdit.this, TermsDetailedView.class);

                String cName = courseName.getText().toString();
                String cStart = courseStart.getText().toString();
                String cEnd = courseEnd.getText().toString();
                String cStatus = courseStatus.getSelectedItem().toString();
                String cInsName = courseInsName.getText().toString();
                String cInsPhone = courseInsPhone.getText().toString();
                String cInsEmail = courseInsEmail.getText().toString();
                String cTermId = termCourseId;

                termsDatabase.updateCourse(cDName,cName,cStart,cEnd,cStatus,cInsName,cInsPhone,cInsEmail);
                courseName.setText("");
                courseStart.setText("");
                courseEnd.setText("");
                courseStatus.setSelection(0);
                courseInsName.setText("");
                courseInsPhone.setText("");
                courseInsEmail.setText("");

                i.putExtra("cEName", cName);
                i.putExtra("tDName",termEName);
                Toast.makeText(this, "Term Name = " + termEName, Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(this, CourseDetailedView.class);
            i.putExtra("cEName", courseName.getText().toString());
            this.finish();
            return true;
        }


        return super.onOptionsItemSelected(item);}
}