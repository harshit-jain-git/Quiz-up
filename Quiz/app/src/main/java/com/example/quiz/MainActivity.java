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

import org.w3c.dom.Text;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseCreate db = new DatabaseCreate(this);
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

        setQuestionView();
        String name = "Level 1";
        level.setText(name);
        score_1.setText(String.valueOf(score));

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
                    if (counter == 0) setQuestionView();
                    System.out.println(counter + "is the time left");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread_1.start();

//        final Thread thread_2=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true)
//                    if(counter == 5){
//                        stop=true;
//                        break;
//                    }
//            }
//        });
//        thread_2.start();

        thread_3.start();

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
            System.out.println("Inside update method");
            if (!clicked)
            {
                TextView selected_option = (TextView) v;
                if (currentQ.getANSWER().equals(selected_option.getText())) {
                    selected_option.setBackgroundResource(R.drawable.greentextview);
                    score_1.setText(String.valueOf(Integer.valueOf(score_1.getText().toString()) + 1));
                } else selected_option.setBackgroundResource(R.drawable.redtextview);
                nextQuestion=true;
                clicked=true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
