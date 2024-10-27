package com.example.abm2.Adapters;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abm2.Home;
import com.example.abm2.Model.Terms;
import com.example.abm2.R;

import java.util.ArrayList;

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.ViewHolder>{
    private final RecycleViewInterface recycleViewInterface;
   private ArrayList<Terms> termsArrayList;
   private Context context;
   int selected_item = -1;
   Home home;

public HomeRVAdapter(ArrayList<Terms> termsArrayList, Context context,RecycleViewInterface recycleViewInterface) {
        this.termsArrayList = termsArrayList;
        this.context = context;
        this.recycleViewInterface = recycleViewInterface;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycle_view, parent, false);
        return new ViewHolder(view, recycleViewInterface);    }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    Terms terms = termsArrayList.get(position);

    holder.homeTermName.setText(terms.getTermName());

    if(selected_item == position){
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, androidx.cardview.R.color.cardview_shadow_start_color));

    }
}



@Override
public int getItemCount() {
    // returning the size of our array list
    return termsArrayList.size();
}
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView homeTermName;
        private TextView homeTermId;

        public ViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            homeTermName = itemView.findViewById(R.id.homeTermNameRec);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                if (recycleViewInterface != null){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                    recycleViewInterface.selectedItemClicked(pos);
                    }
                }
                }
            });

        }

            }

        }




