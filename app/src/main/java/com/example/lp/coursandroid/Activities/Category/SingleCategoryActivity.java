package com.example.lp.coursandroid.Activities.Category;

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
import com.example.lp.coursandroid.Models.Category;
import com.example.lp.coursandroid.R;
import com.example.lp.coursandroid.Request.RequestMaker;

import org.json.JSONObject;

public class SingleCategoryActivity extends AppCompatActivity implements Response.ErrorListener {

    private RequestMaker requestMaker = new RequestMaker(this);
    private ResponseJSONHandler responseJSONHandler;

    private String categoryId;
    private Category category;

    private Button deleteButton;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category);

        getExtras();
        getCategory();

        deleteButton = findViewById(R.id.delete_category_button);
        updateButton = findViewById(R.id.update_category_button);

        setButtonsListeners();
    }

    private void getExtras(){
        Bundle extra = getIntent().getExtras();

        if(extra != null) {
            if (extra.getString("categoryId") != null)
                categoryId = extra.getString("categoryId");
            else
                Log.e("DEBUG", "extra.getString(\"categoryId\") is null" );
        }
    }
    private void setButtonsListeners() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOnCategoryFormActivity();
            }
        });

        Button goBackButton = findViewById(R.id.single_category_go_back_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToListActivity();
            }
        });
    }

    private void getCategory(){
        requestMaker.makeSingleObjectGetRequest(
                "category" ,
                categoryId,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseJSONHandler = new ResponseJSONHandler(response);

                        category = responseJSONHandler.getCategory();

                        setTextViews();
                    }
                },
                this
        );
    }
    private void deleteCategory() {
        requestMaker.makeDeleteRequest(
                "category",
                categoryId,
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
        TextView name = findViewById(R.id.category_name);

        name.setText(category.getName());
    }

    private void goBackToListActivity(){
        Intent intent = new Intent(this, CategoryListActivity.class);
        startActivity(intent);
    }
    private void goOnCategoryFormActivity(){
        Intent intent = new Intent(this, CategoryFormActivity.class);
        intent.putExtra("categoryId", categoryId);
        startActivity(intent);
    }
}
