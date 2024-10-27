package com.example.abm2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class TermEdit extends AppCompatActivity {
    private TermsDatabase termsDatabase;
    private TermCreate termCreate;
     EditText termField;
    private Button startField,endField,updateTerm;
    boolean updatePass = false;

    //Date
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_term_create);

        //Setting Up Variables
        termsDatabase = new TermsDatabase(TermEdit.this);
        termCreate = new TermCreate();
         termField = findViewById(R.id.termName);
         startField = findViewById(R.id.startDate);
         endField = findViewById(R.id.endDate);
         updateTerm = findViewById(R.id.saveTerms);
         startField.setOnClickListener(view -> startDatePicker(startField));
        endField.setOnClickListener(view -> endDatePicker(endField));
        endField.setEnabled(true);
        updateTerm.setText(R.string.update);


        //Populating Fields from Database
        Intent i = getIntent();
        String nameTE = i.getStringExtra("tDName");
        String termID = i.getStringExtra("termID");
        String s2 = termsDatabase.setTermStartEnd(nameTE);
        String sub1 = s2.substring(0, s2.indexOf(" "));
        String sub2 = s2.substring(s2.indexOf(" ")+1);
        ((EditText)findViewById(R.id.termName)).setText(nameTE);
        ((Button)findViewById(R.id.startDate)).setText(sub1);
        ((Button)findViewById(R.id.endDate)).setText(sub2);

        updateTerm.setOnClickListener(v -> {
            if(startField.getText().toString().equals(endField.getText().toString())){
                Toast.makeText(TermEdit.this, "Please Choose End Date greater then Start Date..", Toast.LENGTH_LONG).show();
            }
            else {
                // validating if the text fields are empty or not.
                if (termField.getText().toString().isEmpty() && startField.getText().toString().isEmpty() && endField.getText().toString().isEmpty()) {
                    Toast.makeText(TermEdit.this, "One or more fields are empty..", Toast.LENGTH_SHORT).show();
                } else {
                    boolean recordNameExists = termsDatabase.checkIfExists("Terms", "term", termField.getText().toString());
                    boolean recordIDExists = termsDatabase.checkIfExists("Terms", "id", termID);
                    boolean updateCheck = termsDatabase.checkIfExistsUpdate("Terms", "term", "id",termField.getText().toString(),termID);
                    if (recordNameExists) {
                    if(updateCheck){
                         updatePass = true;

                    }
                    else
                        Toast.makeText(this, termField.getText().toString() + " already exists, please rename term.", Toast.LENGTH_LONG).show();

                    }
                    if(!recordNameExists){
                        updatePass = true;
                    }

                     if (updatePass){
                        // inside this method we are calling an update course
                        // method and passing all our edit text values.
                        termsDatabase.updateTerm(nameTE, termField.getText().toString(), startField.getText().toString(), endField.getText().toString());

                        // displaying a toast message that our course has been updated.
                        Toast.makeText(TermEdit.this, "Term Updated..", Toast.LENGTH_SHORT).show();

                        // launching our main activity.
                        Intent i1 = new Intent(TermEdit.this, Home.class);
                        i1.putExtra("editedTermName", termField.getText().toString());
                        startActivity(i1);
                    }
                }
            }

        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void startDatePicker(Button s){
        DatePickerDialog startPick = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            s.setText(month+1 + "/" + day + "/" + year);

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
            Intent i = new Intent(this, TermsDetailedView.class);
            i.putExtra("editedTermName",termField.getText().toString());
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);}



}