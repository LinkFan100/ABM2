package com.example.abm2.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm2.Databases.TermsDatabase;
import com.example.abm2.Home;
import com.example.abm2.Model.Notes;
import com.example.abm2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NotesRVAdapter extends RecyclerView.Adapter<NotesRVAdapter.ViewHolder>{
    private final RecycleViewInterface recycleViewInterface;
    private final ArrayList<Notes> noteArrayList;
    private final Context context;
    int selected_item = -1;
    TermsDatabase termsDatabase;
    Home home;


    public NotesRVAdapter(ArrayList<Notes> noteArrayList, Context context, RecycleViewInterface recycleViewInterface) {
        this.noteArrayList = noteArrayList;
        this.context = context;
        this.recycleViewInterface = recycleViewInterface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_recycle_view, parent, false);
        return new NotesRVAdapter.ViewHolder(view, recycleViewInterface);    }

    @Override
    public void onBindViewHolder(@NonNull NotesRVAdapter.ViewHolder holder, int position) {

        Notes notes = noteArrayList.get(position);
        holder.noteInfo.setText(notes.getNoteInformation());
        if(selected_item == position){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, androidx.cardview.R.color.cardview_shadow_start_color));

        }


    }



    @Override
    public int getItemCount() {
        // returning the size of our array list
        return noteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteInfo;
        FloatingActionButton sendNote;


        public ViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            noteInfo = itemView.findViewById(R.id.noteInfoText);

            itemView.setOnClickListener(view -> {
                if (recycleViewInterface != null){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        recycleViewInterface.selectedItemClicked(pos);
                    }
                }
            });

        }

    }
}

