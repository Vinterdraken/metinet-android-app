package com.example.lp.coursandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lp.coursandroid.Handlers.ResponseJSONHandler;
import com.example.lp.coursandroid.Models.Post;
import com.example.lp.coursandroid.R;
import com.example.lp.coursandroid.Request.RequestMaker;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostFormActivity extends AppCompatActivity implements Response.ErrorListener {

    private EditText title;
    private EditText content;

    private ResponseJSONHandler responseJSONHandler;
    private String postId;
    private Post post;

    private final RequestMaker requestMaker = new RequestMaker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_form);
        getEditTexts();
        getExtras();
        if(postId != null){
            getPost();
        }
        setButtonsListener();
    }

    private void getExtras(){
        Bundle extra = getIntent().getExtras();

        if(extra != null) {
            if (extra.getString("postId") != null)
                postId = extra.getString("postId");
            else
                postId = null;
                Log.e("DEBUG", "extra.getString(\"postId\") is null" );
        }
    }
    private void getPost(){
        requestMaker.makeSingleObjectGetRequest(
                "post" ,
                postId,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseJSONHandler = new ResponseJSONHandler(response);

                        post = responseJSONHandler.getPost();

                        setEditText();
                    }
                },
                this
        );
    }
    private void setEditText(){
        title.setText(post.getTitle());
        content.setText(post.getContent());
    }

    private void getEditTexts(){
        title = findViewById(R.id.EditTextTitle);
        content = findViewById(R.id.EditTextContent);
    }
    private void setButtonsListener(){
        Button cancelButton = findViewById(R.id.cancel_post_creation_button);
        Button createOrUpdateButton = findViewById(R.id.validate_post_creation_button);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToListActivity();
            }
        });

        if(postId != null) {
            createOrUpdateButton.setText(R.string.update_post_button_text);
            createOrUpdateButton.setBackgroundColor(getResources().getColor(R.color.orange));
        } else {
            createOrUpdateButton.setText(R.string.create_post_button_text);
            createOrUpdateButton.setBackgroundColor(getResources().getColor(R.color.green));
        }


        createOrUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("title", title.getText().toString());
                map.put("content", content.getText().toString());

                if(postId != null) {
                    updatePost(map);
                }
                else {
                    map.put("associe", "5ccafe08542445165c2e1ee6");
                    createPost(map);
                }
            }
        });
    }
    private void createPost(Map<String, String> map){
        requestMaker.makePostRequest(
                "post",
                map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        goBackToListActivity();
                    }
                },
                this
        );
    }
    private void updatePost(Map<String, String> map){
        requestMaker.makePutRequest(
                "post",
                postId,
                map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        goBackOnSinglePostActivity();
                    }
                },
                this
        );
    }

    private void goBackToListActivity(){
        Intent intent = new Intent(this, PostListActivity.class);
        startActivity(intent);
    }
    private void goBackOnSinglePostActivity(){
        Intent intent = new Intent(this, SinglePostActivity.class);
        intent.putExtra("postId", postId);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("API Err: Create_post", error.toString());
    }
}
