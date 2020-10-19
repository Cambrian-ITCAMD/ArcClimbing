package com.example.arcclimbing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;

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
    }

    private void populateDetails() {

        binding.routeName.setText(route.getName());
        binding.barNumberVal.setText(route.getBarNumber());
        binding.gradeVal.setText(route.getGrade());
        binding.colourVal.setText(route.getColour());
        binding.routeSetterName.setText(route.getSetter());
        binding.routeStatus.setText(route.getStatus());
        binding.setDateVal.setText(route.getSetDate());
        binding.removedDateVal.setText(route.getRemovedDate());
    }
}