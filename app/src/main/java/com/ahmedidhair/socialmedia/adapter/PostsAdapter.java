package com.ahmedidhair.socialmedia.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedidhair.socialmedia.R;
import com.ahmedidhair.socialmedia.activities.CommentsPostActivity;
import com.ahmedidhair.socialmedia.databinding.ItemPostBinding;
import com.ahmedidhair.socialmedia.model.Post;
import com.ahmedidhair.socialmedia.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {

    private List<Post> postList;
    private Activity context;
//    private OfferItemListener itemListener;

    public PostsAdapter(Activity context, List<Post> postList) {
        this.postList = postList;
        this.context = context;
//        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public PostsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostsAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.MyViewHolder holder, int position) {
        holder.setData(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Post model;
        public ItemPostBinding binding;

        public MyViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.commentLy.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void setData(Post model) {
            this.model = model;
            binding.titleTv.setText(model.getPostTitle());
            binding.like.setText(String.format("%d", model.getPostLike()));
            Picasso.get().load(model.getPostImage())
                    .placeholder(android.R.color.darker_gray)
                    .into(binding.img);
            FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(model.getPostedBy()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            binding.nameTv.setText(user.fullName);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            FirebaseDatabase.getInstance().getReference()
                    .child("posts")
                    .child(model.getPostId())
                    .child("likes")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                boolean isLike = (boolean) snapshot.getValue();
                                if (isLike) {
                                    binding.likeImg.setImageResource(R.drawable.ic_red_hear);
                                } else {
                                    binding.likeImg.setImageResource(R.drawable.ic_like);
                                }
                            } else {
                                binding.likeLy.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("posts")
                                                .child(model.getPostId())
                                                .child("likes")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        FirebaseDatabase.getInstance().getReference()
                                                                .child("posts")
                                                                .child(model.getPostId())
                                                                .child("postLike")
                                                                .setValue(model.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        binding.likeImg.setImageResource(R.drawable.ic_red_hear);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.commentLy:
                    System.out.println("Log getPostId"+model.getPostId());
                    Intent intent = new Intent(context, CommentsPostActivity.class);
                    intent.putExtra("postId", model.getPostId());
                    intent.putExtra("postedBy", model.getPostedBy());
                    context.startActivity(intent);
                    break;

            }
        }

    }
}