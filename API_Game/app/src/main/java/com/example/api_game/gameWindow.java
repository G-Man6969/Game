package com.example.api_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class gameWindow extends AppCompatActivity {
    Spinner spinner;
    TextView nameBox;
    TextView quoteBox;
    ImageView personFace;
    JSONArray json;
    JSONObject object;
    Random random;
    String name;
    String picPath;
    int score;
    int iterations;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_window);

        preferences = getSharedPreferences("space", Context.MODE_PRIVATE);
        editor = preferences.edit();

        spinner = findViewById(R.id.spinner);
        nameBox = findViewById(R.id.nameBox);
        quoteBox = findViewById(R.id.quoteBox);
        personFace = (ImageView)findViewById(R.id.imageView);
        score = preferences.getInt("score", 0);
        iterations = preferences.getInt("iterations", 0);

        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);
        String firsturl = "https://finalspaceapi.com/api/v0/quote/";
        JsonArrayRequest ExampleRequest = new JsonArrayRequest(firsturl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    json = response;
                    for(int i = 0; i < 51; i++){
                        Log.d("myTag", response.getJSONObject(i).get("by").toString());
                    }
                    nextQuestion();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        ExampleRequestQueue.add(ExampleRequest);

        String url = "https://finalspaceapi.com/api/character/avatar/chuck.jpg";

    }

    public void nextQuestion(){
        personFace.setVisibility(View.INVISIBLE);
        nameBox.setText("Guess who said that");

        random = new Random();
        try {
            object = json.getJSONObject(random.nextInt(51));
            quoteBox.setText(object.get("quote").toString());
            name = object.get("by").toString();
            Picasso.get().load(object.get("image").toString()).into(personFace);
        }
        catch(Exception e){

        }


    }

    public void onClick(View view){
        nameBox.setText(name);
        personFace.setVisibility(View.VISIBLE);
        ++iterations;
        if(spinner.getSelectedItem().toString() == name){
            preferences.edit().remove("score").commit();
            editor.putInt("score", score).commit();
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){

            @Override
            public void run() {
                if(iterations >= 5){
                    //TODO Display Results Screen
                }
                else{
                    preferences.edit().remove("iterations").commit();
                    editor.putInt("iterations", iterations).commit();
                    Intent intent = new Intent(gameWindow.this, gameWindow.class);
                    startActivity(intent);
                }
            }
        };

        timer.schedule(task, 5000);
    }
}

