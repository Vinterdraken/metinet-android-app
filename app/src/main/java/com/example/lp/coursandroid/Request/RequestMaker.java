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

    public RequestMaker() {
    }

    public void makeGetRequest(Context context, String objectName,
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

    public void makeSingleObjectGetRequest(Context context, String objectName, String objectId,
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

    public void makeDeleteRequest(Context context, String objectName, String objectId,
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

    public void makePostRequest(Context context, final String objectName, final Map<String, String> objectToCreate,
                                Response.Listener<JSONObject> jsonObjectListener,
                                Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://10.0.2.2:3000/api/" + objectName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
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

            @Override
            protected Map<String, String> getParams()
            {
                Log.e("MAP", objectToCreate.get("title"));

                return objectToCreate;
            }
        };

        queue.add(jsonObjectRequest);
    }
}
