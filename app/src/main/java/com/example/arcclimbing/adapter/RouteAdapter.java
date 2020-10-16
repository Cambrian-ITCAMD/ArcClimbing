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

    private OnRouteClickListener listener;

    public RouteAdapter(@NonNull FirestoreRecyclerOptions<Route> options, OnRouteClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull RouteViewHolder holder, int position, @NonNull Route model) {
        holder.bind(model, listener);
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RouteViewHolder.from(parent);
    }

    public interface OnRouteClickListener {
        void onRouteClick(Route route);
    }

    public static class RouteViewHolder extends RecyclerView.ViewHolder{

        TextView routeName;
        TextView barNumberVal;
        TextView gradeVal;

        private RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeName = itemView.findViewById(R.id.routeName);
            barNumberVal = itemView.findViewById(R.id.barNumberVal);
            gradeVal = itemView.findViewById(R.id.gradeVal);
        }

        public static RouteViewHolder from(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_route_list_item, parent, false);
            return new RouteViewHolder(view);
        }

        public void bind(Route route, OnRouteClickListener listener) {
            routeName.setText(route.getName());
            barNumberVal.setText(route.getBarNumber());
            gradeVal.setText(route.getGrade());

            routeName.setOnClickListener(view -> listener.onRouteClick(route));
        }
    }
}
