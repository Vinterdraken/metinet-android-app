package com.example.lp.coursandroid.Activities.Post;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lp.coursandroid.Handlers.ResponseJSONHandler;
import com.example.lp.coursandroid.Models.Category;
import com.example.lp.coursandroid.Models.Post;
import com.example.lp.coursandroid.R;
import com.example.lp.coursandroid.Request.RequestMaker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostFormActivity extends AppCompatActivity implements Response.ErrorListener {

    private EditText title;
    private EditText content;
    private Spinner spinner;

    private ResponseJSONHandler responseJSONHandler;
    private String postId;
    private Post post;

    private ArrayList<Category> categories;

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
        getCategories();
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
                map.put("title", title.getText().toString());
                map.put("content", content.getText().toString());
                map.put("associe", getSelectedCategoryId());

                if(postId != null) {
                    updatePost(map);
                }
                else {
                    //map.put("associe", "5ccafe08542445165c2e1ee6");
                    createPost(map);
                }
            }
        });
    }
    private void setSpinner(){
        spinner = findViewById(R.id.categories_spinner);

        ArrayList<String> categoriesName = new ArrayList<>();

        for (Category category : categories){
            categoriesName.add(category.getName());
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categoriesName
        );

        spinner.setAdapter(adp);

        if(postId != null) {
            setSelectedCategory(adp);
        }


        spinner.setVisibility(View.VISIBLE);
    }


    private String getSelectedCategoryId(){
        String selectedCategoryName = spinner.getSelectedItem().toString();

        for (Category category : categories){
            if(selectedCategoryName.equals(category.getName())) {
                return category.getId();
            }
        }

        return "5ccafe08542445165c2e1ee6";
    }
    private void setSelectedCategory(ArrayAdapter<String> adp){
        Category selectedCategory = new Category("","");
        for(Category category : categories){
            if(post.getCategory().getId().equals(category.getId())){
                selectedCategory = category;
            }
        }

        spinner.setSelection(
                adp.getPosition(
                        selectedCategory.getName()
                )
        );
    }

    private void getCategories(){
        requestMaker.makeGetRequest(
                "category",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        responseJSONHandler = new ResponseJSONHandler(response);
                        categories = responseJSONHandler.getCategories();
                        setSpinner();
                    }
                },
                this
        );
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
