package com.example.lp.coursandroid.Activities.Category;

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
import com.example.lp.coursandroid.Activities.MainActivity;
import com.example.lp.coursandroid.Handlers.ResponseJSONHandler;
import com.example.lp.coursandroid.Models.Category;
import com.example.lp.coursandroid.R;
import com.example.lp.coursandroid.Request.RequestMaker;

import org.json.JSONArray;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity implements Response.ErrorListener {

    private RequestMaker requestMaker = new RequestMaker(this);
    private ResponseJSONHandler responseJSONHandler;

    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        getCategories();
        setButtons();
    }

    private void getCategories(){
        requestMaker.makeGetRequest(
                "category",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        responseJSONHandler = new ResponseJSONHandler(response);

                        categories = responseJSONHandler.getCategories();

                        displayPostsTitleList();
                    }
                },
                this
        );
    }
    private void setButtons(){
        Button createButton = findViewById(R.id.create_category_button);
        Button goBackButton = findViewById(R.id.category_list_go_back_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateCategoryActivity();
            }
        });
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("API Err: Category_list", error.toString());
    }

    private void displayPostsTitleList() {
        ListView listView = findViewById(R.id.category_list);

        ArrayList<String> titles = new ArrayList<>();

        for(Category category : categories) {
            titles.add(category.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.category_list_item, R.id.item_category_name, titles);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToSingleCategoryActivity(categories.get(position).getId());
            }
        });
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void goToSingleCategoryActivity(String id){
        Intent intent = new Intent(this, SingleCategoryActivity.class);
        intent.putExtra("categoryId", id);

        startActivity(intent);
    }
    private void goToCreateCategoryActivity(){
        Intent intent = new Intent(this, CategoryFormActivity.class);
        startActivity(intent);
    }
}
