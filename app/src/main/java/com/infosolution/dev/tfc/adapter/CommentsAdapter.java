package com.infosolution.dev.tfc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.model.Comments;
import com.infosolution.dev.tfc.model.Home;

import java.util.Collections;
import java.util.List;

/**
 * Created by amit on 1/18/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    List<Comments> commentsList= Collections.emptyList();
    Context context;

    public CommentsAdapter(List<Comments> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivprofileimage;
        TextView tvusernamecomm,tvcomments;
        RatingBar rbcomments;
        public MyViewHolder(View view) {
            super(view);
            ivprofileimage=(ImageView) view.findViewById(R.id.profile_image);

            tvusernamecomm=(TextView) view.findViewById(R.id.tv_namecomm);
            tvcomments=(TextView) view.findViewById(R.id.tv_comments);
            rbcomments=(RatingBar) view.findViewById(R.id.rb_comm);

        }
}
   @Override
    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

       View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);

       return new MyViewHolder(itemView);
   }

    @Override
    public void onBindViewHolder(final CommentsAdapter.MyViewHolder holder, final int position) {

        holder.ivprofileimage.setImageResource(commentsList.get(position).getProfileimage());
        holder.tvusernamecomm.setText(commentsList.get(position).getUsername());
        holder.tvcomments.setText(commentsList.get(position).getComments());
        holder.rbcomments.setRating(commentsList.get(position).getRating());



    }
    @Override
    public int getItemCount()
    {
        return commentsList.size();
    }
}
