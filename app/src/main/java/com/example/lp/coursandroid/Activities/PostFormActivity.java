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
import com.example.lp.coursandroid.R;
import com.example.lp.coursandroid.Request.RequestMaker;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostFormActivity extends AppCompatActivity implements Response.ErrorListener {

    private EditText title;
    private EditText content;

    private RequestMaker requestMaker = new RequestMaker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_form);

        getEditTexts();
        setButtonsListener();
    }

    private void getEditTexts(){
        title = findViewById(R.id.EditTextTitle);
        content = findViewById(R.id.EditTextContent);
    }

    private void setButtonsListener(){
        Button cancelButton = findViewById(R.id.cancel_post_creation_button);
        Button createButton = findViewById(R.id.validate_post_creation_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToListActivity();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("title", title.getText().toString());
                map.put("content", content.getText().toString());
                map.put("associe", "5ccafe08542445165c2e1ee6");

                createPost(map);
            }
        });
    }

    private void createPost(Map<String, String> map){
        requestMaker.makePostRequest(
                this,
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

    private void goBackToListActivity(){
        Intent intent = new Intent(this, PostListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("API Err: Create_post", error.toString());
    }
}
