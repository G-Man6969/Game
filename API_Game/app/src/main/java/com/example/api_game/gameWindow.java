package com.example.api_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class gameWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_window);

        TextView textView = findViewById(R.id.textView);

        final String[] holder = new String[1];

        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);
        String firsturl = "https://finalspaceapi.com/api/v0/episode/";
        JsonArrayRequest ExampleRequest = new JsonArrayRequest(firsturl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                textView.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.toString());
            }
        });


        ExampleRequestQueue.add(ExampleRequest);
    }
}