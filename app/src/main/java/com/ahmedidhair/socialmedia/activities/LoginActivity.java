package com.ahmedidhair.socialmedia.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.ahmedidhair.socialmedia.R;
import com.ahmedidhair.socialmedia.databinding.ActivityLoginBinding;
import com.ahmedidhair.socialmedia.utils.GlobalData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends BActivity {
    ActivityLoginBinding binding;
    String email;
    String password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        Animation side = AnimationUtils.loadAnimation(getActivity(), R.anim.side_slide);
        binding.logoImg.startAnimation(side);
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = binding.emailEt.getText().toString();
                password = binding.passwordEt.getText().toString();

                if (email.isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.emailEt.startAnimation(shake);
                    GlobalData.Toast(getActivity(), "Enter Email");
                    return;
                }
                if (password.isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.passwordEt.startAnimation(shake);
                    GlobalData.Toast(getActivity(), "Enter Password");
                    return;
                }
                GlobalData.progressDialog(getActivity(),"Loading..",true);
                auth.signInWithEmailAndPassword(email, password.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        GlobalData.progressDialog(getActivity(),"Loading..",false);
                        if (task.isSuccessful()) {
                            startNewActivity(MainActivity.class, null, true);
                        } else {
                            GlobalData.Toast(getActivity(), Objects.requireNonNull(task.getException()).getMessage());
                        }
                    }
                });

            }
        });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(SignUpActivity.class, null, false);
            }
        });
    }
}