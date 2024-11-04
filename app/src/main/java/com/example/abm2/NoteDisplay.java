package com.example.abm2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm2.Adapters.NotesRVAdapter;
import com.example.abm2.Adapters.RecycleViewInterface;
import com.example.abm2.Databases.TermsDatabase;
import com.example.abm2.Model.Notes;

import java.util.ArrayList;

public class NoteDisplay extends DialogFragment implements RecycleViewInterface{
    private TermsDatabase termsDatabase;
    private ArrayList<Notes> notesArrayList;
    private NotesRVAdapter notesRVAdapter;
    private RecyclerView noteRecycle;
//    public String value,value2;
    public static String value,value2,value3;

//    FloatingActionButton sendNote,deleteNote;




        @SuppressLint("MissingInflatedId")
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            notesArrayList = new ArrayList<>();
            termsDatabase = new TermsDatabase(getActivity());

            Bundle bundle = getArguments();
            if (bundle != null) {
                value = bundle.getString("bundleId");
                value2 = bundle.getString("nameBundle");
                value3 = bundle.getString("bundleAssessmentId");
            }
//
            if(value != null) {
                notesArrayList = termsDatabase.readNotesWithCourseId(Integer.parseInt(value));
            }
            else {
                assert value3 != null;
                notesArrayList = termsDatabase.readNotesWithAssessmentId(Integer.parseInt(value3));
            }

//            notesArrayList = termsDatabase.readNotes();

            notesRVAdapter = new NotesRVAdapter(notesArrayList, getActivity(), this);
//
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = requireActivity().getLayoutInflater();


            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            View dialogView = inflater.inflate(R.layout.note_display, null);
            Dialog dialog = getDialog();

//            sendNote = dialogView.findViewById(R.id.fltNoteSend);
//            deleteNote = dialogView.findViewById(R.id.fltNoteDelete);
            noteRecycle = dialogView.findViewById(R.id.noteRecycleDisplay);
            noteRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
            noteRecycle.setItemAnimator(new DefaultItemAnimator());
            noteRecycle.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
            noteRecycle.setAdapter(notesRVAdapter);
            builder.setView(dialogView)
                    .setTitle("Optional Notes")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            NoteDisplay.this.getDialog().cancel();
                        }
                    });



            return builder.create();
        }

//    }
@Override
public void selectedItemClicked(int position) {

//    Intent i = new Intent(CourseDetailedView.this, AssessmentDetailedView.class);
    String noteInformation = notesArrayList.get(position).getNoteInformation();
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT,noteInformation);
    sendIntent.putExtra(Intent.EXTRA_TITLE,value2+" Note");
    sendIntent.setType("text/plain");
    Intent shareIntent = Intent.createChooser(sendIntent,null);
    startActivity(shareIntent);
}
    }
