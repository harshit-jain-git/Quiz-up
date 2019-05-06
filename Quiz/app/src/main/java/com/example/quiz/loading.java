package com.example.quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

public class loading extends AppCompatActivity {

    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            System.out.println("Life is good");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_loading);

            socket = IO.socket("http://10.42.0.1:3000");
            socket.connect();
            socket.on("confirmation", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Intent i1 = new Intent(loading.this, MainActivity.class);
                    startActivity(i1);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
