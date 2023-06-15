package com.ahmedidhair.socialmedia.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedidhair.socialmedia.R;
import com.ahmedidhair.socialmedia.databinding.ItemCommentBinding;
import com.ahmedidhair.socialmedia.databinding.ItemPostBinding;
import com.ahmedidhair.socialmedia.model.Comment;
import com.ahmedidhair.socialmedia.model.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private List<Comment> commentList;
    private Activity context;

    public CommentsAdapter(Activity context, List<Comment> commentList) {
        this.commentList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentsAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.MyViewHolder holder, int position) {
        holder.setData(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Comment model;
        public ItemCommentBinding binding;

        public MyViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void setData(Comment model) {
            this.model = model;

            String time = TimeAgo.using(model.getCommentedAt());
            binding.time.setText(time);
            binding.comment.setText(model.getCommentBody());

            FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(model.getCommentedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            Picasso.get()
                                    .load(user.avatar != null && !user.avatar.isEmpty() ? user.avatar : "none")
                                    .placeholder(android.R.color.darker_gray)
                                    .into(binding.profileImg);
                            binding.nameTv.setText(user.fullName);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


        }

        @Override
        public void onClick(View view) {

        }
    }
}