package com.example.arcclimbing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.arcclimbing.adapter.RouteAdapter;
import com.example.arcclimbing.databinding.ActivityMainBinding;
import com.example.arcclimbing.model.Route;
import com.example.arcclimbing.utils.ArcClimbingConst;
import com.example.arcclimbing.viewmodel.MainActivityViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private static final int LIMIT = 25;

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    private FirebaseFirestore firestore;
    private Query query;
    private RouteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            intent.putExtra(ArcClimbingConst.SELECTED_ROUTE, new Route());
            startActivity(intent);
        });
    }

    private void setUpRecyclerView() {
        FirestoreRecyclerOptions<Route> options = new FirestoreRecyclerOptions.Builder<Route>()
                .setQuery(query, Route.class)
                .build();

        adapter = new RouteAdapter(options, route -> {
//            Toast.makeText(this, "I want to climb " + route.getDocumentId(),
//                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, RouteDetailsActivity.class);
            intent.putExtra(ArcClimbingConst.SELECTED_ROUTE, route);
            startActivity(intent);
        });
        binding.routeRecyclerView.setAdapter(adapter);
    }

    private void initFirestore() {
        firestore = FirebaseFirestore.getInstance();

        query = firestore.collection("routes")
                .orderBy("grade",Query.Direction.DESCENDING)
                .limit(LIMIT);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user_profile:
//                onAddItemsClicked();
                break;
            case R.id.menu_sign_out:
                AuthUI.getInstance().signOut(this);
                startSignIn();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
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

        startActivityForResult(intent,RC_SIGN_IN);
        viewModel.setIsSigningIn(true);
    }
}