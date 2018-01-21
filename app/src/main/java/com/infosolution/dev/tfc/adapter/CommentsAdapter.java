package com.infosolution.dev.tfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.model.Comments;
import com.infosolution.dev.tfc.model.Home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by amit on 1/18/2018.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {



    public CommentsAdapter(ArrayList<Comments> commentsArrayList, Context context, Activity activity) {
        this.commentsArrayList = commentsArrayList;
        this.context = context;
        this.activity = activity;
    }

    private ArrayList<Comments> commentsArrayList;
    Context context;
    private Activity activity;

    @Override
    public CommentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
       return new MyViewHolder(view,context,commentsArrayList);
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.MyViewHolder holder, int position) {

        final Comments comments= commentsArrayList.get(position);


        holder.tvusernamecomm.setText(commentsArrayList.get(position).getUsername());
        holder.tvcomments.setText(commentsArrayList.get(position).getComments());
        holder.rbcomments.setRating(commentsArrayList.get(position).getRating());




        Glide.with(activity).load(comments.getProfileimage()).into(holder.ivprofileimage);





    }



    @Override
    public int getItemCount() {

        return commentsArrayList.size();
    }

    public  class  MyViewHolder extends  RecyclerView.ViewHolder{

        ImageView ivprofileimage;
        TextView tvusernamecomm,tvcomments;
        RatingBar rbcomments;
        Context ctx;
        ArrayList<Comments> comments= new ArrayList<Comments>();

        public MyViewHolder(View itemView, Context ctx, ArrayList<Comments> comments) {
            super(itemView);
            this.ctx = ctx;
            this.comments = comments;


            ivprofileimage=(ImageView) itemView.findViewById(R.id.profile_image);
            tvusernamecomm=(TextView) itemView.findViewById(R.id.tv_namecom);
            tvcomments=(TextView) itemView.findViewById(R.id.tv_comments);
            rbcomments=(RatingBar) itemView.findViewById(R.id.rb_comm);
        }
    }

}
