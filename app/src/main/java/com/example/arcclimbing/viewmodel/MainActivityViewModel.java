package com.example.arcclimbing.viewmodel;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModel;

import com.example.arcclimbing.R;

public class MainActivityViewModel extends ViewModel {

    private boolean isSigningIn;

    public MainActivityViewModel() {
        this.isSigningIn = false;
    }

    public boolean getIsSigningIn() {
        return isSigningIn;
    }

    public void setIsSigningIn(boolean isSigningIn) {
        this.isSigningIn = isSigningIn;
    }

    public void toggle(Boolean isNightMode) {
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            item.setIcon(R.drawable.ic_dark);
//            item.setTitle(R.string.dark_mode);
        } else {
//            item.setIcon(R.drawable.ic_day);
//            item.setTitle(R.string.day);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
