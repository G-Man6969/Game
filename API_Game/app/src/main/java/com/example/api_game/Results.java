package com.example.api_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        int score = GameState.INSTANCE.getScore();

        TextView textView2 = findViewById(R.id.textView2);
        if (score > 2)
        {
            textView2.setText("Well Done!");
            ImageView imageView = findViewById(R.id.imageView2);
            imageView.setImageResource(R.drawable.happy);
        }
        else
        {
            textView2.setText("Terrible job..");
            ImageView imageView = findViewById(R.id.imageView2);
            imageView.setImageResource(R.drawable.gary);
        }

        TextView textView = findViewById(R.id.textView);
        textView.setText(getString(R.string.scorePlaceholder, score));
    }
}