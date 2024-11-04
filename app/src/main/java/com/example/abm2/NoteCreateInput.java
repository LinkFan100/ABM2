package com.example.abm2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.abm2.Databases.TermsDatabase;

public class NoteCreateInput extends DialogFragment {
    private TermsDatabase termsDatabase;
    String value,value3;
    private int value2;

    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder;
            try (TermsDatabase termsDatabase = new TermsDatabase(getActivity())) {
                builder = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater inflater = requireActivity().getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                View dialogView = inflater.inflate(R.layout.fragment_note_create, null);
                EditText inputEditText = dialogView.findViewById(R.id.noteCreateField);

                Bundle bundle = getArguments();
                if (bundle != null) {
                    value = bundle.getString("IdNoteCreate");
                    value2 = 0;
                    value3 = bundle.getString("IdAssessmentNoteCreate");
                }


                builder.setView(dialogView)
                        .setTitle("Optional Note")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String inputData = inputEditText.getText().toString();
                                if(value != null) {
                                    termsDatabase.addNewNote(inputData, value, String.valueOf(value2));
                                }
                                else
                                    termsDatabase.addNewNote(inputData,String.valueOf(value2),value3);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                NoteCreateInput.this.getDialog().cancel();
                            }
                        });
            }
            return builder.create();
        }
    }
