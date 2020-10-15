package com.example.arcclimbing.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arcclimbing.R;
import com.example.arcclimbing.model.Route;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RouteAdapter extends FirestoreRecyclerAdapter<Route, RouteAdapter.RouteViewHolder> {

    public RouteAdapter(@NonNull FirestoreRecyclerOptions<Route> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RouteViewHolder holder, int position, @NonNull Route model) {
        holder.routeName.setText(model.getName());
        holder.barNumberVal.setText(model.getBarNumber());
        holder.gradeVal.setText(model.getGrade());
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_route_list_item,
                parent, false);
        return new RouteViewHolder(view);
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder{

        TextView routeName;
        TextView barNumberVal;
        TextView gradeVal;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeName = itemView.findViewById(R.id.routeName);
            barNumberVal = itemView.findViewById(R.id.barNumberVal);
            gradeVal = itemView.findViewById(R.id.gradeVal);
        }
    }
}
