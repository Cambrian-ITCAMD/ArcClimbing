package com.example.arcclimbing.viewmodel;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    private boolean isSigningIn;

    public MainActivityViewModel() {
        this.isSigningIn = false;
    }

    public boolean getIsSigningIn() {
        return isSigningIn;
    }

    public void setIsSigningIn(boolean isSigningIn) {
        isSigningIn = isSigningIn;
    }

}
