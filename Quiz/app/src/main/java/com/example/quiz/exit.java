package com.example.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class exit extends AppCompatActivity {

    public static int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        TextView final_score = findViewById(R.id.my_score);
        final_score.setText("Your score is: " + String.valueOf(result));
    }
}
