package com.example.abm2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abm2.Databases.TermsDatabase;

import java.util.Calendar;
import java.util.Date;

public class TermCreate extends AppCompatActivity {
    private TermsDatabase termsDatabase;
    EditText termField;
    Button startField,endField;

    //Date Time
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    int startDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_term_create);

        termsDatabase = new TermsDatabase(TermCreate.this);
         termField = findViewById(R.id.termName);
         startField = findViewById(R.id.startDate);
         endField = findViewById(R.id.endDate);

        startField.setOnClickListener(view -> startDatePicker(startField,endField));
        endField.setOnClickListener(view -> endDatePicker(endField));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Saves to Database and moves to Course Select
    public void saveTermInfo(View view) {

        Intent i = new Intent(this, Home.class);

        //Start of code block for saving data to Terms Database
        String nameTerm = ((EditText)findViewById(R.id.termName)).getText().toString();
        String dateStart = ((Button)findViewById(R.id.startDate)).getText().toString();
        String dateEnd = ((Button)findViewById(R.id.endDate)).getText().toString();



            // validating if the text fields are empty or not.
            if (nameTerm.isEmpty() || dateStart.isEmpty() || dateEnd.isEmpty()) {
                Toast.makeText(TermCreate.this, "One or more fields are empty..", Toast.LENGTH_SHORT).show();
            } else {
        if(dateStart.equals(dateEnd)){
            Toast.makeText(TermCreate.this, "Please Choose End Date greater then Start Date..", Toast.LENGTH_LONG).show();
        }

        else {
                boolean recordExists = termsDatabase.checkIfExists("Terms", "term", nameTerm);
                if (recordExists) {
                    Toast.makeText(this, nameTerm +" already exists, please rename term.", Toast.LENGTH_LONG).show();
                }
                else {
                    termsDatabase.addNewTerm(nameTerm, dateStart, dateEnd);
                    Toast.makeText(TermCreate.this, "Term has been added.", Toast.LENGTH_SHORT).show();
                    termField.setText("");
                    startField.setText("");
                    endField.setText("");
                    startActivity(i);
                }
            }


        }
    }
    private void startDatePicker(Button s,Button e){
        DatePickerDialog startPick = new DatePickerDialog(this, (datePicker, year, month, day) -> {

            s.setText(month + 1 + "/" + day + "/" + year);
            startDay = day;
            e.setEnabled(true);
        }, year, month, dayOfMonth);
        startPick.getDatePicker().setMinDate(System.currentTimeMillis());
        startPick.show();
    }
    private void endDatePicker(Button e){

        DatePickerDialog endPick = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            e.setText(month + 1 + "/" + day + "/" + year);
        }, year, month, startDay);
        endPick.getDatePicker().setMinDate(System.currentTimeMillis());
        endPick.show();

    }



}