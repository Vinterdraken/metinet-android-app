package com.example.lp.coursandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lp.coursandroid.Handlers.ResponseJSONHandler;
import com.example.lp.coursandroid.Models.Post;
import com.example.lp.coursandroid.R;
import com.example.lp.coursandroid.Request.RequestMaker;

import org.json.JSONArray;

import java.util.ArrayList;

public class PostListActivity extends AppCompatActivity implements Response.ErrorListener {

    private RequestMaker requestMaker = new RequestMaker(this);
    private ResponseJSONHandler responseJSONHandler;

    private ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        getPosts();
        setCreatePostButtonListener();
    }

    private void getPosts() {
        requestMaker.makeGetRequest(
                "post",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        responseJSONHandler = new ResponseJSONHandler(response);

                        posts = responseJSONHandler.getPosts();

                        displayPostsTitleList();
                    }
                },
                this
        );
    }

    private void setCreatePostButtonListener(){
        Button createButton = findViewById(R.id.create_post_button);
        Button goBackButton = findViewById(R.id.go_back_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreatePostActivity();
            }
        });
        goBackButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToMainActivity();
                    }
                }
        );
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("API Err: Post_list", error.toString());
    }

    private void displayPostsTitleList() {
        ListView listView = findViewById(R.id.post_list);

        ArrayList<String> titles = new ArrayList<>();

        for(Post post : posts) {
            titles.add(post.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.post_list_item, R.id.item_post_title, titles);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToSinglePostActivity(posts.get(position).getId());
            }
        });
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void goToSinglePostActivity(String id){
        Intent intent = new Intent(this, SinglePostActivity.class);
        intent.putExtra("postId", id);

        startActivity(intent);
    }
    private void goToCreatePostActivity(){
        Intent intent = new Intent(this, PostFormActivity.class);
        startActivity(intent);
    }
}
