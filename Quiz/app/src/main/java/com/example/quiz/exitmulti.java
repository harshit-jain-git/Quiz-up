package com.example.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class exitmulti extends AppCompatActivity {
    public static int result;
    public static int score_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exitmulti);
        ImageView mview=findViewById(R.id.imageView2);
        if(score_2>result)
        {
            mview.setImageResource(R.drawable.lost);
        }
        TextView score_p1 = findViewById(R.id.score_p);
        TextView score_p2 = findViewById(R.id.score_p1);
        score_p1.setText("Your score is: " + String.valueOf(result));
        score_p2.setText("Player2's score is: " + String.valueOf(score_2));
    }
}
