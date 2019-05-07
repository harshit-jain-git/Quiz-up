package com.example.quiz;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    List<Question> quesList;
    static int score=0;
    int counter=10;
    boolean stop=false;
    boolean nextQuestion=false;
    boolean clicked=false;
    int qid=0;
    Question currentQ;
    TextView txtQuestion;
    static TextView level,user_1,user_2,score_1,score_2,time;
    TextView rda, rdb, rdc, rdd;
    String p1, p2;
    Intent intent1;
    JSONObject data;
    int index;

    private Socket socket;

    private void setQuestionView()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (qid % 5 == 0)
                {
                    int l = qid/5 + 1;
                    level.setText("Level " + l);
                }
                currentQ = quesList.get(qid);
                rda.setBackgroundResource(R.drawable.textview_border);
                rdb.setBackgroundResource(R.drawable.textview_border);
                rdc.setBackgroundResource(R.drawable.textview_border);
                rdd.setBackgroundResource(R.drawable.textview_border);
                txtQuestion.setText(currentQ.getQUESTION());
                rda.setText(currentQ.getOPTA());
                rdb.setText(currentQ.getOPTB());
                rdc.setText(currentQ.getOPTC());
                rdd.setText(currentQ.getOPTD());
                qid++;
                clicked=false;
                counter=10;
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        p1 = MainScreen.text;
        p2 = loading.p2;
        index = loading.serverindex;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseCreate db = new DatabaseCreate(this);
        intent1=new Intent(MainActivity.this,SongService.class);
        quesList = db.getAllQuestions();
        currentQ = quesList.get(qid);
        txtQuestion = (TextView) findViewById(R.id.question);
        level = (TextView) findViewById(R.id.level);
        user_1 = (TextView) findViewById(R.id.user_1);
        time = (TextView) findViewById(R.id.time);
        score_2 = (TextView) findViewById(R.id.score_2);
        user_2 = (TextView) findViewById(R.id.user_2);
        score_1 = (TextView) findViewById(R.id.score_1);
        rda = (TextView) findViewById(R.id.option_1);
        rdb = (TextView) findViewById(R.id.option_2);
        rdc = (TextView) findViewById(R.id.option_3);
        rdd = (TextView) findViewById(R.id.option_4);
        try {
            socket = IO.socket("http://10.42.0.1:3000");
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        user_1.setText(p1);
        user_2.setText(p2);
        setQuestionView();
        String name = "Level 1";
        level.setText(name);
        score_1.setText(String.valueOf(score));
        startService(intent1);
        final Thread thread_1=new Thread(new Runnable() {
            @Override
            public void run() {
                counter=10;
                while(!Thread.currentThread().isInterrupted()&&!stop)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            time.setText(String.valueOf(counter));
                        }
                    });
                    if (!clicked) counter--;
                    if (counter == 1) socket.emit("updateResult", score, p1, index);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread_1.start();

        thread_3.start();

        socket.on("update", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                nextQuestion=true;
                data = (JSONObject)args[0];

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            score_2.setText(String.valueOf(data.get(p2)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "The other user has left the game", Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(MainActivity.this, MainScreen.class);
                        startActivity(i1);
                    }
                });
            }
        });
    }

    Thread thread_3 = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                while(true) {
                    while (nextQuestion) {
                        sleep(1000);
                        System.out.println("Inside next question, qid: " + qid);

                        if (qid < 40) {
                            setQuestionView();
                        } else {
                            exit.result = score;
                            stopService(intent1);
                            Intent i1 = new Intent(MainActivity.this, exit.class);
                            startActivity(i1);
                        }
                        nextQuestion = false;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    });

    public void click_handler(View v) {
        try {
            if (!clicked)
            {
                TextView selected_option = (TextView) v;
                score = Integer.valueOf(score_1.getText().toString());
                if (currentQ.getANSWER().equals(selected_option.getText())) {
                    selected_option.setBackgroundResource(R.drawable.greentextview);
                    score = score + 1;
                    score_1.setText(String.valueOf(score));
                } else selected_option.setBackgroundResource(R.drawable.redtextview);
                socket.emit("updateResult", score, p1, index);
                clicked=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        exit.result = score;
        super.onDestroy();
        stopService(intent1);
        socket.emit("beforedisconnect", index);
        socket.disconnect();
    }
}