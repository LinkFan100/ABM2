package com.example.abm2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.abm2.DeleteFolder.AssessmentRVAdapter;
import com.example.abm2.Databases.TermsDatabase;

public class AssessmentDetailedView extends AppCompatActivity {
    private TermsDatabase termsDatabase;

     String assessmentId, name,courseName;
     String assessmentid,s1;

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

        //Setting DetailedView Fields
         s1 = ((TextView) findViewById(R.id.assessmentDetailedTitle)).getText().toString();
        String[] s4 = termsDatabase.setAssessmentDetails(s1);
        ((TextView)findViewById(R.id.assessmentDetailedStart)).setText(s4[0]);
        ((TextView)findViewById(R.id.assessmentDetailedEnd)).setText(s4[1]);
        ((TextView)findViewById(R.id.assessmentDetailedType)).setText(s4[2]);
        ((TextView)findViewById(R.id.assessmentInfoText)).setText(s4[3]);

    }

//code for making menu items and events attached to them
public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.menu,menu);
    menu.findItem(R.id.menuEdit).setTitle("Edit Assessment");
    menu.findItem(R.id.menuDelete).setTitle("Delete Assessment");
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
    alertB.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            termsDatabase.deleteAssessment(assessmentid);
            Intent i2 = new Intent(AssessmentDetailedView.this, CourseDetailedView.class);
            i2.putExtra("cName", courseName);
            Toast.makeText(AssessmentDetailedView.this, "Assessment Has been deleted", Toast.LENGTH_SHORT).show();
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
}
