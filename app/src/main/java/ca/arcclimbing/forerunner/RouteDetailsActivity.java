package ca.arcclimbing.forerunner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import ca.arcclimbing.forerunner.model.Route;
import ca.arcclimbing.forerunner.utils.ArcClimbingConst;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import ca.arcclimbing.forerunner.databinding.ActivityRouteDetailsBinding;

public class RouteDetailsActivity extends AppCompatActivity {

    private ActivityRouteDetailsBinding binding;
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRouteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        route = (Route) getIntent().getSerializableExtra(ArcClimbingConst.SELECTED_ROUTE);

        if (route == null) {
            finish();
            return;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(route.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        populateDetails();
        binding.editRoute.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditRouteActivity.class);
            intent.putExtra(ArcClimbingConst.SELECTED_ROUTE, route);
            intent.putExtra(ArcClimbingConst.ACTIVITY, ArcClimbingConst.DETAIL);
            startActivity(intent);
            finish();
        });
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

    private void populateDetails() {

        binding.routeName.setText(route.getName());
        binding.barNumberVal.setText(route.getBarNumber());
        binding.gradeVal.setText(route.getGrade());
        binding.colourVal.setText(route.getColour());
        binding.routeSetterName.setText(route.getSetter());
        binding.routeStatusVal.setText(route.getStatus());
        binding.setDateVal.setText(route.getSetDate());
        binding.removedDateVal.setText(route.getRemovedDate());
    }
}