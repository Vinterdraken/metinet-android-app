package com.example.lp.coursandroid.Handlers;

import com.example.lp.coursandroid.Models.Category;
import com.example.lp.coursandroid.Models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseJSONHandler {

    private JSONArray jsonArray;
    private JSONObject jsonObject;

    // CONSTRUCTORS
    public ResponseJSONHandler(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
    public ResponseJSONHandler(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    // GETTERS
    public JSONObject getJsonObject() {
        return jsonObject;
    }
    public JSONArray getJsonArray() {
        return jsonArray;
    }

    //HANDLE
    public ArrayList<Post> getPosts(){
        try {
            ArrayList<Post> posts = new ArrayList<>();

            for(int i = 0; i < jsonArray.length(); ++i){
                jsonObject = jsonArray.getJSONObject(i);

                JSONObject category = jsonObject.getJSONObject("associe");

                Post newPost = new Post(
                    jsonObject.getString("_id"),
                    jsonObject.getString("title"),
                    jsonObject.getString("creationDate"),
                    jsonObject.getString("content"),
                    category.getString("name")
                );

                posts.add(newPost);
            }

            return posts;

        } catch(JSONException je) {
            je.printStackTrace();
            return null;
        }
    }
    public Post getPost(){
        try {
            JSONObject category = jsonObject.getJSONObject("associe");

            Post newPost = new Post(
                    jsonObject.getString("_id"),
                    jsonObject.getString("title"),
                    jsonObject.getString("creationDate"),
                    jsonObject.getString("content"),
                    category.getString("name")
            );

            return newPost;

        } catch(JSONException je) {
            je.printStackTrace();
            return null;
        }
    }

    public ArrayList<Category> getCategories(){
        try {
            ArrayList<Category> categories = new ArrayList<>();

            for(int i = 0; i < jsonArray.length(); ++i){
                jsonObject = jsonArray.getJSONObject(i);

                Category newCategory = new Category(
                        jsonObject.getString("_id"),
                        jsonObject.getString("name")
                );

                categories.add(newCategory);
            }

            return categories;

        } catch(JSONException je) {
            je.printStackTrace();
            return null;
        }
    }
}
