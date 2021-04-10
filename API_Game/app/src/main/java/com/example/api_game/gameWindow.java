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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_window);

        spinner = findViewById(R.id.spinner);
        nameBox = findViewById(R.id.nameBox);
        quoteBox = findViewById(R.id.quoteBox);
        personFace = (ImageView)findViewById(R.id.imageView);


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
        // Increase iterations
        GameState.INSTANCE.setIterations(GameState.INSTANCE.getIterations() + 1);

        nameBox.setText(name);
        personFace.setVisibility(View.VISIBLE);
        if((spinner.getSelectedItem().toString()).equals(name)){
            Toast.makeText(gameWindow.this, "CORRECT", Toast.LENGTH_LONG).show();
            GameState.INSTANCE.setScore(GameState.INSTANCE.getScore() + 1);
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){

            @Override
            public void run() {
                if(GameState.INSTANCE.getIterations() >= 5){
                    Intent intent = new Intent(gameWindow.this, Results.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(gameWindow.this, gameWindow.class);
                    startActivity(intent);
                }
            }
        };

        timer.schedule(task, 1000);
    }
}

