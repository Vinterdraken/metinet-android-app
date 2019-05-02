package com.example.lp.coursandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lp.coursandroid.Handlers.ResponseJSONHandler;
import com.example.lp.coursandroid.Models.Post;
import com.example.lp.coursandroid.R;
import com.example.lp.coursandroid.Request.RequestMaker;

import org.json.JSONException;
import org.json.JSONObject;

public class SinglePostActivity extends AppCompatActivity implements Response.ErrorListener {


    private RequestMaker requestMaker = new RequestMaker();
    private ResponseJSONHandler responseJSONHandler;

    private String postId;
    private Post post;

    private Button deleteButton;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        getExtras();
        getPost();

        deleteButton = findViewById(R.id.delete_post_button);
        updateButton = findViewById(R.id.update_post_button);

        setButtonsListeners();

    }

    private void getExtras(){
        Bundle extra = getIntent().getExtras();

        if(extra != null) {
            if (extra.getString("id") != null)
                postId = extra.getString("id");
            else
                Log.e("DEBUG", "extra.getString(\"id\") is null" );
        }
    }
    private void setButtonsListeners() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePost();
            }
        });
    }

    private void getPost(){
        requestMaker.makeSingleObjectGetRequest(
                this,
                "post" ,
                postId,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseJSONHandler = new ResponseJSONHandler(response);

                        post = responseJSONHandler.getPost();

                        setTextViews();
                    }
                },
                this
        );
    }
    private void deletePost() {
        requestMaker.makeDeleteRequest(
                this,
                "post",
                postId,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        goBackToListActivity();
                    }
                },
                this
        );
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void setTextViews(){
        TextView title = findViewById(R.id.post_title);
        TextView creationDate = findViewById(R.id.post_creation_date);
        TextView content = findViewById(R.id.post_content);
        TextView category = findViewById(R.id.post_category);

        title.setText(post.getTitle());
        creationDate.setText(post.getCreationDate());
        content.setText(post.getContent());
        category.setText(post.getIdCategory());
    }

    private void goBackToListActivity(){
        Intent intent = new Intent(this, PostListActivity.class);
        startActivity(intent);
    }
}
