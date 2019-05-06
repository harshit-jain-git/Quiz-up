package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quiz.MainActivity;
import com.example.quiz.R;

public class MainScreen extends AppCompatActivity {
    Button one;

    public static String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        one = (Button) findViewById(R.id.button);

        int score = getIntent().getIntExtra("score", 0);
        try {
            one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                text = ((EditText)findViewById(R.id.editText)).getText().toString();
                Intent intent = new Intent(MainScreen.this, mode.class);
                startActivity(intent);
                }
            });

            Toast.makeText(MainScreen.this, "Let's Start!!", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(MainScreen.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}