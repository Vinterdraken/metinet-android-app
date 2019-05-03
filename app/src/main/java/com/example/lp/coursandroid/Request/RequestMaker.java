package com.example.lp.coursandroid.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestMaker {

    private final String token = "TmlxdWUgdGEgbWFyZQ==";
    private Context context;

    public RequestMaker(Context context) {
        this.context = context;
    }

    public void makeGetRequest(final String objectName,
                               Response.Listener<JSONArray> jsonArrayListener,
                               Response.ErrorListener errorListener){
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://10.0.2.2:3000/api/" + objectName;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                jsonArrayListener,
                errorListener
        );
        queue.add(jsonArrayRequest);
    }

    public void makeSingleObjectGetRequest(final String objectName, final String objectId,
                                           Response.Listener<JSONObject> jsonObjectListener,
                                           Response.ErrorListener errorListener){
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://10.0.2.2:3000/api/" + objectName + "/" + objectId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                url,
                null,
                jsonObjectListener,
                errorListener
        );
        queue.add(jsonObjectRequest);
    }

    public void makeDeleteRequest(final String objectName, final String objectId,
                                  Response.Listener<JSONObject> jsonObjectListener,
                                  Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://10.0.2.2:3000/api/" + objectName + "/" + objectId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                jsonObjectListener,
                errorListener
        ) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String>  headers = new HashMap<> ();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);

                return headers;
            }
        };

        queue.add(jsonObjectRequest);

    }

    public void makePostRequest(final String objectName, final Map<String, String> objectToCreate,
                                Response.Listener<JSONObject> jsonObjectListener,
                                Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://10.0.2.2:3000/api/" + objectName;

        JSONObject jsonObject = new JSONObject();

        try {
            if(objectName.equals("post")){
                jsonObject.put("title", objectToCreate.get("title"));
                jsonObject.put("content", objectToCreate.get("content"));
                jsonObject.put("associe", objectToCreate.get("associe"));
            }
            else if(objectName.equals("category")){
                jsonObject.put("name", objectToCreate.get("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                jsonObjectListener,
                errorListener
        ) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String>  headers = new HashMap<> ();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);

                return headers;
            }

        };

        queue.add(jsonObjectRequest);
    }

    public void makePutRequest(final String objectName, final String objectId,
                               final Map<String, String> objectToCreate,
                               Response.Listener<JSONObject> jsonObjectListener,
                               Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://10.0.2.2:3000/api/" + objectName + "/" + objectId;

        JSONObject jsonObject = new JSONObject();

        try {
            if(objectName.equals("post")){
                jsonObject.put("title", objectToCreate.get("title"));
                jsonObject.put("content", objectToCreate.get("content"));
                jsonObject.put("associe", objectToCreate.get("associe"));
            }
            else if(objectName.equals("category")){
                jsonObject.put("name", objectToCreate.get("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonObject,
                jsonObjectListener,
                errorListener
        ) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String>  headers = new HashMap<> ();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);

                return headers;
            }

        };

        queue.add(jsonObjectRequest);
    }
}
