package com.example.lp.coursandroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lp.coursandroid.Handlers.ResponseJSONHandler;
import com.example.lp.coursandroid.Models.Category;
import com.example.lp.coursandroid.R;
import com.example.lp.coursandroid.Request.RequestMaker;

import org.json.JSONArray;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity implements Response.ErrorListener {

    private RequestMaker requestMaker = new RequestMaker();
    private ResponseJSONHandler responseJSONHandler;

    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        getCategories();
    }

    private void getCategories(){
        requestMaker.makeGetRequest(
                this,
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

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                goToSinglePostActivity(posts.get(position).getId());
//            }
//        });
    }
}
