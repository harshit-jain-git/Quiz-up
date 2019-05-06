package com.example.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

public class mode extends AppCompatActivity {

    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Button singleplayer = findViewById(R.id.singleplayer);
            Button multiplayer = findViewById(R.id.multiplayer);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mode);
            socket = IO.socket("http://10.42.0.1:3000");
            socket.connect();

            singleplayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(mode.this, MainActivity_2.class);
                startActivity(intent);
                }
            });

            multiplayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String text = MainScreen.text;
                socket.emit("join", text);
                Intent intent = new Intent(mode.this, loading.class);
                startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
