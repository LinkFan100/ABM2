package com.example.abm2.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm2.Home;
import com.example.abm2.Model.Courses;
import com.example.abm2.R;

import java.util.ArrayList;

public class TermDetailedRVAdapter extends RecyclerView.Adapter<TermDetailedRVAdapter.ViewHolder>{
    private final RecycleViewInterface recycleViewInterface;
    private ArrayList<Courses> courseArrayList;
    private Context context;
    int selected_item = -1;
    Home home;

    public TermDetailedRVAdapter(ArrayList<Courses> courseArrayList, Context context,RecycleViewInterface recycleViewInterface) {
        this.courseArrayList = courseArrayList;
        this.context = context;
        this.recycleViewInterface = recycleViewInterface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycle_view, parent, false);
        return new ViewHolder(view, recycleViewInterface);    }

    @Override
    public void onBindViewHolder(@NonNull TermDetailedRVAdapter.ViewHolder holder, int position) {

        Courses courses = courseArrayList.get(position);
        holder.termCourseName.setText(courses.getCourseName());
        if(selected_item == position){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, androidx.cardview.R.color.cardview_shadow_start_color));

        }
    }



    @Override
    public int getItemCount() {
        // returning the size of our array list
        return courseArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView termCourseName;

        public ViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            termCourseName = itemView.findViewById(R.id.homeTermNameRec);
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

