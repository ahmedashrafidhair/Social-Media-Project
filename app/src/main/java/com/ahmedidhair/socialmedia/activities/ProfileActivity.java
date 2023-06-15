package com.ahmedidhair.socialmedia.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.ahmedidhair.socialmedia.R;
import com.ahmedidhair.socialmedia.databinding.ActivityProfileBinding;
import com.ahmedidhair.socialmedia.model.User;
import com.ahmedidhair.socialmedia.utils.GlobalData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileActivity extends BActivity {

    ActivityProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    User user;
    String fullName;
    String mobileNumber;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setTitle("Profile");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        database.getReference().child("Users").child(Objects.requireNonNull(auth.getUid()))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            System.out.println(snapshot.toString());
                            user = snapshot.getValue(User.class);
                            binding.fullNameEt.setText(user.fullName);
                            binding.mobileEt.setText(user.mobile);
                            binding.emailEt.setText(user.email);

                            Picasso.get()
                                    .load(user.avatar != null && !user.avatar.isEmpty() ? user.avatar : "none")
                                    .placeholder(android.R.color.darker_gray)
                                    .into(binding.img);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.insertImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        binding.savaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullName = binding.fullNameEt.getText().toString();
                mobileNumber = binding.mobileEt.getText().toString();
                if (fullName.isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.fullNameEt.startAnimation(shake);
                    GlobalData.Toast(getActivity(), "Enter Full Name");
                    return;
                }
                if (mobileNumber.isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.fullNameEt.startAnimation(shake);
                    GlobalData.Toast(getActivity(), "Enter Mobile Number");
                    return;
                }
                database.getReference().child("Users").child(auth.getUid()).child("fullName").setValue(fullName);
                database.getReference().child("Users").child(auth.getUid()).child("mobile").setValue(mobileNumber);
                GlobalData.Toast(getActivity(), "Saved Successfully");

            }
        });
    }


    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        binding.img.setImageURI(selectedImageUri);
                        StorageReference reference = storage.getReference()
                                .child("profileImage").child(Objects.requireNonNull(auth.getUid()));
                        GlobalData.progressDialog(getActivity(), "Loading...", true);
                        reference.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                GlobalData.progressDialog(getActivity(), "Loading...", false);
                                GlobalData.Toast(getActivity(), "Profile Photo Saved");
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        database.getReference().child("Users").child(auth.getUid()).child("avatar").setValue(uri.toString());
                                    }
                                });
                            }
                        });

//
//                        Bitmap selectedImageBitmap;
//                        try {
//                            selectedImageBitmap
//                                    = MediaStore.Images.Media.getBitmap(
//                                    this.getContentResolver(),
//                                    selectedImageUri);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            });


}