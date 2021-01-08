package com.example.arcclimbing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.arcclimbing.adapter.RouteAdapter;
import com.example.arcclimbing.databinding.ActivityMainBinding;
import com.example.arcclimbing.model.Route;
import com.example.arcclimbing.utils.ArcClimbingConst;
import com.example.arcclimbing.viewmodel.MainActivityViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    private FirebaseFirestore firestore;
    private Query query;
    private RouteAdapter adapter;

    private Boolean isNightMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar.getRoot());

        binding.navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar.getRoot(),
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // View model
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        //Initialize Firestore
        initFirestore();

        //Setup Recyclerview
        setUpRecyclerView();

        //Add new route
        binding.addRoute.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditRouteActivity.class);
            intent.putExtra(ArcClimbingConst.ACTIVITY, ArcClimbingConst.MAIN);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setUpRecyclerView() {
        FirestoreRecyclerOptions<Route> options = new FirestoreRecyclerOptions.Builder<Route>()
                .setQuery(query, Route.class)
                .build();

        adapter = new RouteAdapter(options, route -> {
            Intent intent = new Intent(this, RouteDetailsActivity.class);
            intent.putExtra(ArcClimbingConst.SELECTED_ROUTE, route);
            startActivity(intent);
        });
        binding.routeRecyclerView.setAdapter(adapter);
    }

    private void initFirestore() {
        firestore = FirebaseFirestore.getInstance();

        query = firestore.collection("routes")
                .orderBy("barNumber",Query.Direction.DESCENDING)
//                .whereEqualTo("status","active")
                .limit(ArcClimbingConst.LIMIT);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Start sign in if necessary
        if (shouldStartSignIn()) {
            startSignIn();
            return;
        }

        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ArcClimbingConst.RC_SIGN_IN) {
            viewModel.setIsSigningIn(false);

            if (requestCode != RESULT_OK && shouldStartSignIn()) {
                startSignIn();
            }
        }
    }

    private boolean shouldStartSignIn() {
        return (!viewModel.getIsSigningIn() && FirebaseAuth.getInstance().getCurrentUser() == null);
    }

    private void startSignIn() {
        // Sign in with FirebaseUI
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.EmailBuilder().build()))
                .setIsSmartLockEnabled(false)
                .build();

        startActivityForResult(intent,ArcClimbingConst.RC_SIGN_IN);
        viewModel.setIsSigningIn(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.themes_menu, menu);
            if (isNightMode && menu.findItem(R.id.toggleTheme) != null) {
                MenuItem toggle = menu.findItem(R.id.toggleTheme);
                toggle.setIcon(R.drawable.ic_day);
                toggle.setTitle(R.string.day);
            }
//            menu.getItem(R.id.toggleTheme).setIcon(R.drawable.ic_day);
//            menu.getItem(R.id.toggleTheme).setTitle(R.string.day);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.toggleTheme) {
            viewModel.toggle(isNightMode);
            isNightMode = !isNightMode;
            invalidateOptionsMenu();
        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_user_profile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_dark_mode:
//                viewModel.toggle(item, isNightMode);
//                isNightMode = !isNightMode;
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_sign_out:
                AuthUI.getInstance().signOut(this);
                startSignIn();
                break;
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}