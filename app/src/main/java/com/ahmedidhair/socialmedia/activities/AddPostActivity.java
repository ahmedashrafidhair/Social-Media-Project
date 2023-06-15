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
import androidx.databinding.DataBindingUtil;

import com.ahmedidhair.socialmedia.R;
import com.ahmedidhair.socialmedia.databinding.ActivityAddPostBinding;
import com.ahmedidhair.socialmedia.model.Post;
import com.ahmedidhair.socialmedia.utils.GlobalData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.Objects;

public class AddPostActivity extends BActivity {

    ActivityAddPostBinding binding;
    Uri uri;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    String title;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_post);
        setTitle("Create Post");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        binding.createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = binding.titleEt.getText().toString();
                desc = binding.descriptionEt.getText().toString();

                if (title.isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.titleEt.startAnimation(shake);
                    GlobalData.Toast(getActivity(), "Enter Title");
                    return;
                }
                if (desc.isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.descriptionEt.startAnimation(shake);
                    GlobalData.Toast(getActivity(), "Enter Description");
                    return;
                }
                if (uri == null) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    binding.imgLy.startAnimation(shake);
                    GlobalData.Toast(getActivity(), "Add Image Post");
                    return;
                }
                final StorageReference reference = storage.getReference().child("posts")
                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                        .child(new Date().getTime() + "");
                GlobalData.progressDialog(getActivity(), "Loading...", true);
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Post post = new Post();
                                post.setPostImage(uri.toString());
                                post.setPostedBy(FirebaseAuth.getInstance().getUid());
                                post.setPostTitle(desc);
                                post.setPostDescription(title);
                                post.setPostedAt(new Date().getTime());

                                database.getReference().child("posts")
                                        .push()
                                        .setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                GlobalData.progressDialog(getActivity(), "Loading...", false);
                                                GlobalData.Toast(getActivity(), "Posted Successfully");
                                                finish();
                                            }
                                        });
                            }
                        });
                    }
                });
            }
        });
        binding.selectImgLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
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
                        uri = data.getData();
                        binding.img.setImageURI(uri);

                    }
                }
            });
}