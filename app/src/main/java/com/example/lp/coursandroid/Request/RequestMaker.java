package com.example.lp.coursandroid.Request;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class RequestMaker {

    public RequestMaker() {
    }

    public void makeGetRequest(Context context, String objectName, Response.Listener<JSONArray> jsonArrayListener, Response.ErrorListener errorListener){
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                "http://10.0.2.2:3000/api/" + objectName,
                jsonArrayListener,
                errorListener
        );
        queue.add(jsonArrayRequest);
    }

    public void makeSingleObjectGetRequest(Context context, String objectName, Response.Listener<JSONObject> jsonObjectListener, Response.ErrorListener errorListener){
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "http://10.0.2.2:3000/api/" + objectName,
                null,
                jsonObjectListener,
                errorListener
        );
        queue.add(jsonObjectRequest);
    }
}
