package com.example.arcclimbing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arcclimbing.databinding.ActivityRouteDetailsBinding;
import com.example.arcclimbing.model.Route;
import com.example.arcclimbing.utils.ArcClimbingConst;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RouteDetailsActivity extends AppCompatActivity {

    private ActivityRouteDetailsBinding binding;
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRouteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        route = (Route) getIntent().getSerializableExtra(ArcClimbingConst.SELECTED_ROUTE);
        populateDetails();
        binding.editRoute.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditRouteActivity.class);
            intent.putExtra(ArcClimbingConst.SELECTED_ROUTE, route);
            startActivity(intent);
        });
    }

    private void populateDetails() {

        binding.routeName.setText(route.getName());
        binding.barNumberVal.setText(route.getBarNumber());
        binding.gradeVal.setText(route.getGrade());
        binding.colourVal.setText(route.getColour());
        binding.routeSetterName.setText(route.getSetter());
        binding.routeStatusVal.setText(route.getStatus());
        binding.setDateVal.setText(route.getSetDate());

        if (route.getRemovedDate() == null) {
            binding.removedDateVal.setVisibility(View.GONE);
        } else {
            binding.removedDateVal.setText(route.getRemovedDate());
        }
    }
}