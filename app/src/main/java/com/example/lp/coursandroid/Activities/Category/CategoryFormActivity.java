package com.example.lp.coursandroid.Activities.Category;

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
import com.example.lp.coursandroid.Models.Category;
import com.example.lp.coursandroid.R;
import com.example.lp.coursandroid.Request.RequestMaker;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CategoryFormActivity extends AppCompatActivity implements Response.ErrorListener {

    private EditText name;

    private ResponseJSONHandler responseJSONHandler;
    private String categoryId;
    private Category category;

    private final RequestMaker requestMaker = new RequestMaker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_form);
        getEditTexts();
        getExtras();
        if(categoryId != null){
            getCategory();
        }
        setButtonsListener();
    }

    private void getExtras(){
        Bundle extra = getIntent().getExtras();

        if(extra != null) {
            if (extra.getString("categoryId") != null)
                categoryId = extra.getString("categoryId");
            else
                categoryId = null;
            Log.e("DEBUG", "extra.getString(\"categoryId\") is null" );
        }
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

                        setEditText();
                    }
                },
                this
        );
    }

    private void getEditTexts(){
        name = findViewById(R.id.EditTextName);
    }
    private void setEditText(){
        name.setText(category.getName());
    }

    private void setButtonsListener(){
        Button cancelButton = findViewById(R.id.cancel_category_creation_button);
        Button createOrUpdateButton = findViewById(R.id.validate_category_creation_button);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToListActivity();
            }
        });

        if(categoryId != null) {
            createOrUpdateButton.setText(R.string.update_button_text);
            createOrUpdateButton.setBackgroundColor(getResources().getColor(R.color.orange));
        } else {
            createOrUpdateButton.setText(R.string.create_button_text);
            createOrUpdateButton.setBackgroundColor(getResources().getColor(R.color.green));
        }


        createOrUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("name", name.getText().toString());

                if(categoryId != null) {
                    updateCategory(map);
                }
                else {
                    createCategory(map);
                }
            }
        });
    }
    private void createCategory(Map<String, String> map){
        requestMaker.makePostRequest(
                "category",
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
    private void updateCategory(Map<String, String> map){
        requestMaker.makePutRequest(
                "category",
                categoryId,
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
        Intent intent = new Intent(this, CategoryListActivity.class);
        startActivity(intent);
    }
    private void goBackOnSinglePostActivity(){
        Intent intent = new Intent(this, SingleCategoryActivity.class);
        intent.putExtra("categoryId", categoryId);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("API Err: Create_categ", error.toString());
    }
}
