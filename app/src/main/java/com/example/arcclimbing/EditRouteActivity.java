package com.example.arcclimbing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.arcclimbing.databinding.ActivityEditRouteBinding;
import com.example.arcclimbing.model.Route;
import com.example.arcclimbing.utils.ArcClimbingConst;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class EditRouteActivity extends AppCompatActivity {

    private ActivityEditRouteBinding binding;
    Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditRouteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        route = (Route) getIntent().getSerializableExtra(ArcClimbingConst.SELECTED_ROUTE);

        if (route != null) {
            popDetails();
        }

        binding.saveRoute.setOnClickListener(view -> updateDetails());
    }

    private void updateDetails() {
        String routeName = binding.editRouteName.getText().toString();
        String barNumberVal = binding.editBarNumberVal.getText().toString();
        String gradeVal = binding.editGradeVal.getText().toString();
        String colourVal = binding.editColourVal.getText().toString();
        String setter = binding.editRouteSetterName.getText().toString();
        String status = binding.editRouteStatusVal.getText().toString();
        String setDate = binding.editSetDateVal.getText().toString();
        String removedDate = binding.editRemovedDateVal.getText().toString();

        if (route == null && (routeName.trim().isEmpty() || barNumberVal.trim().isEmpty() || gradeVal.trim().isEmpty() || colourVal.trim().isEmpty() || setter.trim().isEmpty() || status.trim().isEmpty())) {
            Toast.makeText(this, "Please insert data into all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference routeRef = FirebaseFirestore.getInstance()
                .collection("routes");

        if (route != null) {
            routeRef.document(route.getDocumentId()).set(new Route(routeName,gradeVal,barNumberVal,colourVal,setter,setDate,removedDate,status), SetOptions.merge());
        } else if (route == null && removedDate.trim().isEmpty()) {
            routeRef.add(new Route(routeName,gradeVal,barNumberVal,colourVal,setter,setDate,status));
        } else {
            routeRef.add(new Route(routeName,gradeVal,barNumberVal,colourVal,setter,setDate,removedDate,status));
        }
        finish();

    }

    private void popDetails() {
        binding.editRouteName.setText(route.getName());
        binding.editBarNumberVal.setText(route.getBarNumber());
        binding.editGradeVal.setText(route.getGrade());
        binding.editColourVal.setText(route.getColour());
        binding.editRouteSetterName.setText(route.getSetter());
        binding.editRouteStatusVal.setText(route.getStatus());
        binding.editSetDateVal.setText(route.getSetDate());
        binding.editRemovedDateVal.setText(route.getRemovedDate());
    }
}