package com.ahmedidhair.socialmedia.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.databinding.DataBindingUtil;

import com.ahmedidhair.socialmedia.R;
import com.ahmedidhair.socialmedia.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends BActivity {

    ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        Animation side = AnimationUtils.loadAnimation(getActivity(), R.anim.side_slide);
        binding.logoImg.startAnimation(side);
        handleSplashScreen();
    }

    private void handleSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser() != null){
                    startNewActivity(MainActivity.class, null, true);
                }else {
                    startNewActivity(LoginActivity.class, null, true);
                }

            }
        }, 3000);


    }


}