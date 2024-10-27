package com.example.abm2.DeleteFolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm2.Adapters.RecycleViewInterface;
import com.example.abm2.Home;
import com.example.abm2.Model.Assessments;
import com.example.abm2.R;

import java.util.ArrayList;

public class AssessmentRVAdapter extends RecyclerView.Adapter<AssessmentRVAdapter.ViewHolder>{
    private final RecycleViewInterface recycleViewInterface;
    private ArrayList<Assessments> AssessmentArrayList;
    private Context context;
    int selected_item = -1;
    Home home;

    public AssessmentRVAdapter(ArrayList<Assessments> AssessmentArrayList, Context context,RecycleViewInterface recycleViewInterface) {
        this.AssessmentArrayList = AssessmentArrayList;
        this.context = context;
        this.recycleViewInterface = recycleViewInterface;
    }
    @NonNull
    @Override
    public AssessmentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycle_view, parent, false);
        return new AssessmentRVAdapter.ViewHolder(view, recycleViewInterface);    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentRVAdapter.ViewHolder holder, int position) {

        Assessments assessments = AssessmentArrayList.get(position);
        holder.courseAssessmentName.setText(assessments.getAssessmentName());
        if(selected_item == position){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, androidx.cardview.R.color.cardview_shadow_start_color));

        }
    }



    @Override
    public int getItemCount() {
        // returning the size of our array list
        return AssessmentArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseAssessmentName;

        public ViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            courseAssessmentName = itemView.findViewById(R.id.homeTermNameRec);
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

