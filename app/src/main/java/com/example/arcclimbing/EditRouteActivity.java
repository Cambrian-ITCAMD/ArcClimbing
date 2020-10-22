package com.example.arcclimbing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.arcclimbing.databinding.ActivityEditRouteBinding;
import com.example.arcclimbing.model.Route;
import com.example.arcclimbing.utils.ArcClimbingConst;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditRouteActivity extends AppCompatActivity {

    private ActivityEditRouteBinding binding;
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditRouteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String msg = getIntent().getStringExtra(ArcClimbingConst.ACTIVITY);

        if (msg.equals(ArcClimbingConst.DETAIL)) {
            route = (Route) getIntent().getSerializableExtra(ArcClimbingConst.SELECTED_ROUTE);

            if (route == null) {
                finish();
                return;
            }

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(route.getName());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            popDetails();
        }

        binding.editSetDateVal.setOnClickListener(view -> showDatePickerDialog(ArcClimbingConst.SET_DATE));
        binding.editRemovedDateVal.setOnClickListener(view -> showDatePickerDialog(ArcClimbingConst.REMOVED_DATE));

        binding.saveRoute.setOnClickListener(view -> {
            String routeName = binding.editRouteName.getText().toString();
            String barNumberVal = binding.editBarNumberVal.getText().toString();
            String gradeVal = binding.editGradeVal.getText().toString();
            String colourVal = binding.editColourVal.getText().toString();
            String setter = binding.editRouteSetterName.getText().toString();
            String status = binding.editRouteStatusVal.getText().toString();
            String setDate = binding.editSetDateVal.getText().toString();
            String removedDate = binding.editRemovedDateVal.getText().toString();

            if (routeName.trim().isEmpty() || barNumberVal.trim().isEmpty() || gradeVal.trim().isEmpty() || colourVal.trim().isEmpty() || setter.trim().isEmpty() || setDate.trim().isEmpty() || status.trim().isEmpty()) {
                Toast.makeText(this, "Please insert data into all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            CollectionReference routeRef = FirebaseFirestore.getInstance()
                    .collection("routes");

            if (msg.equals(ArcClimbingConst.MAIN) && removedDate.isEmpty()) {
                routeRef.add(new Route(routeName, gradeVal, barNumberVal, colourVal, setter, setDate, removedDate, status));
            } else if (msg.equals(ArcClimbingConst.MAIN)) {
                routeRef.add(new Route(routeName, gradeVal, barNumberVal, colourVal, setter, setDate, removedDate, status));
            } else if (msg.equals(ArcClimbingConst.DETAIL) && removedDate.isEmpty()) {
                routeRef.document(route.getDocumentId()).set(new Route(routeName, gradeVal, barNumberVal, colourVal, setter, setDate, status), SetOptions.merge());
            } else if (msg.equals(ArcClimbingConst.DETAIL)) {
                routeRef.document(route.getDocumentId()).set(new Route(routeName, gradeVal, barNumberVal, colourVal, setter, setDate, removedDate, status), SetOptions.merge());
            }
            finish();
        });
    }

    private void showDatePickerDialog(String string) {
        Calendar calendar = Calendar.getInstance(Locale.CANADA);
        DatePickerDialog setDate = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
                    String date = dateFormat.format(selectedDate.getTime());
                    if (string.equals(ArcClimbingConst.SET_DATE)) {
                        binding.editSetDateVal.setText(date);
                    } else {
                        binding.editRemovedDateVal.setText(date);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        setDate.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void popDetails() {
        binding.editRouteName.setText(route.getName());
        binding.editBarNumberVal.setText(route.getBarNumber());
        binding.editGradeVal.setText(route.getGrade());
        binding.editColourVal.setText(route.getColour());
        binding.editRouteSetterName.setText(route.getSetter());
        binding.editRouteStatusVal.setText(route.getStatus());
        binding.editSetDateVal.setText(route.getSetDate());
        if (route.getRemovedDate() != null) {
            binding.editRemovedDateVal.setText(route.getRemovedDate());
        }
    }
}


