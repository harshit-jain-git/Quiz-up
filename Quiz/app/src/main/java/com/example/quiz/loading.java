package com.example.quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class loading extends AppCompatActivity {

    private Socket socket;
    public static String p1, p2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            p1 = MainScreen.text;
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_loading);

            socket = IO.socket("http://10.42.0.1:3000");
            socket.connect();

            socket.on("confirmation", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    Iterator<String> itr=data.keys();
                    while(itr.hasNext())
                    {
                        String s1=itr.next();
                        if(!s1.equals(p1))
                        {
                            System.out.println("Other player is: " + s1);
                            p2=s1;
                        }
                    }
                    Intent i1 = new Intent(loading.this, MainActivity.class);
                    startActivity(i1);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
