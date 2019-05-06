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
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;


public class MainScreen extends AppCompatActivity {
    Button one;

    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        one = (Button) findViewById(R.id.button);

        int score = getIntent().getIntExtra("score", 0);
        try {
            socket = IO.socket("http://10.42.0.1:3000");
            socket.connect();
            one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String text = ((EditText)findViewById(R.id.editText)).getText().toString();
                socket.emit("join", text);
                Intent intent = new Intent(MainScreen.this, loading.class);
                startActivity(intent);
                }
            });
            if(score==0){
                Toast.makeText(MainScreen.this, "Let's Start!!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainScreen.this, "Congrats! You have scored: " + score, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(MainScreen.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}