package com.myselamat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myselamat.model.TravelHistory;

import java.util.ArrayList;

public class Recycle_View_Travel_History extends RecyclerView.Adapter<Recycle_View_Travel_History.ViewHolder> {

    Context context;
    ArrayList<TravelHistory> listhistory;


    public Recycle_View_Travel_History(Context context, ArrayList<TravelHistory> listhistory) {
        this.context = context;
        this.listhistory = listhistory;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_history, viewGroup, false);

        return new ViewHolder(view);
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        TravelHistory th = listhistory.get(position);


        viewHolder.locationame.setText(th.getLocation());
        viewHolder.checkin.setText(th.getCheckIn());
        viewHolder.checkout.setText(th.getCheckOut());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listhistory.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView locationame,checkin,checkout;


        public ViewHolder(View view) {


            super(view);


            locationame= view.findViewById(R.id.loc_name);
            checkin = view.findViewById(R.id.ci_time);
            checkout = view.findViewById(R.id.co_time);


        }


    }

}


