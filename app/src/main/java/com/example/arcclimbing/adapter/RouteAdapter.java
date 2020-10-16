package com.example.arcclimbing.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arcclimbing.R;
import com.example.arcclimbing.databinding.ViewRouteListItemBinding;
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

        private ViewRouteListItemBinding binding;

        private RouteViewHolder(@NonNull ViewRouteListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public static RouteViewHolder from(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ViewRouteListItemBinding binding = ViewRouteListItemBinding.inflate(inflater, parent, false);
            return new RouteViewHolder(binding);
        }

        public void bind(Route route, OnRouteClickListener listener) {
            binding.routeName.setText(route.getName());
            binding.barNumberVal.setText(route.getBarNumber());
            binding.gradeVal.setText(route.getGrade());

            binding.getRoot().setOnClickListener(view -> listener.onRouteClick(route));
        }
    }
}
