package com.ahmedidhair.socialmedia.activities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.ahmedidhair.socialmedia.R;
import com.ahmedidhair.socialmedia.databinding.ActivitySignUpBinding;
import com.ahmedidhair.socialmedia.model.User;
import com.ahmedidhair.socialmedia.utils.GlobalData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends BActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String fullName;
    String mobileNumber;
    String email;
    String password;
    String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        setTitle("Sign Up");
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Animation side = AnimationUtils.loadAnimation(getActivity(), R.anim.side_slide);
        binding.logoImg.startAnimation(side);

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = binding.fullNameEt.getText().toString();
                mobileNumber = binding.mobileEt.getText().toString();
                email = binding.emailEt.getText().toString();
                password = binding.passwordEt.getText().toString();
                confirmPassword = binding.confirmPasswordEt.getText().toString();

                if(fullName.isEmpty()){
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.fullNameEt.startAnimation(shake);
                    Toast.makeText(SignUpActivity.this, "Enter Full Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mobileNumber.isEmpty()){
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.mobileEt.startAnimation(shake);
                    Toast.makeText(SignUpActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.isEmpty()){
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.emailEt.startAnimation(shake);
                    Toast.makeText(SignUpActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.passwordEt.startAnimation(shake);
                    Toast.makeText(SignUpActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.confirmPasswordEt.startAnimation(shake);
                    Toast.makeText(SignUpActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.trim().equals(confirmPassword.trim())) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.passwordEt.startAnimation(shake);
                    binding.confirmPasswordEt.startAnimation(shake);
                    Toast.makeText(SignUpActivity.this, "Password Does Not Match", Toast.LENGTH_SHORT).show();
                    return;
                }
                GlobalData.progressDialog(getActivity(),"Loading...",true);
                auth.createUserWithEmailAndPassword(email, password.trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        GlobalData.progressDialog(getActivity(),"Loading...",false);
                        if (task.isSuccessful()) {
                            User user = new User(fullName,mobileNumber,email,password,"");
                            String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            startNewActivity(MainActivity.class,null,true);

                        } else {
                            Toast.makeText(SignUpActivity.this,  Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}