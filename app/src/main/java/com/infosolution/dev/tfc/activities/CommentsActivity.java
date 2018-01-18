package com.infosolution.dev.tfc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.infosolution.dev.tfc.R;
import com.infosolution.dev.tfc.adapter.CommentsAdapter;
import com.infosolution.dev.tfc.model.Comments;
import com.infosolution.dev.tfc.model.Home;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView rvcomments;
    CommentsAdapter commentsAdapter;
    View view;
    private List<Comments> commentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        rvcomments = findViewById(R.id.rv_comments);

        commentsList = comments_data();


        commentsAdapter = new CommentsAdapter(commentsList, CommentsActivity.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CommentsActivity.this, LinearLayoutManager.VERTICAL, false);
        rvcomments.setLayoutManager(layoutManager);
        rvcomments.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvcomments.setAdapter(commentsAdapter);

    }

    private List<Comments> comments_data() {

        List<Comments> comments= new ArrayList<>();

        comments.add(new Comments( R.drawable.default_user, "Shreyansh","Hello",3));
        comments.add(new Comments( R.drawable.default_user, "Lucky","Hi",3));
        comments.add(new Comments( R.drawable.default_user, "Shreyansh","How are you?",5));


        return comments;
    }
}

