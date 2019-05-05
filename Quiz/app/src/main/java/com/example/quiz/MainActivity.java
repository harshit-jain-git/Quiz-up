package com.example.quiz;

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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    List<Question> quesList;
    static int score=0;
    int qid=0;
    Question currentQ;
    TextView txtQuestion;
    TextView res;
    static TextView level,user_1,user_2,score_1,score_2,time;
    TextView rda, rdb, rdc, rdd;
    private void setQuestionView()
    {
        txtQuestion.setText(currentQ.getQUESTION());
        rda.setText(currentQ.getOPTA());
        rdb.setText(currentQ.getOPTB());
        rdc.setText(currentQ.getOPTC());
        rdd.setText(currentQ.getOPTD());
        qid++;
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

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                int counter=10;
                while(!Thread.currentThread().isInterrupted())
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            time.setText(String.valueOf(10));
                        }
                    });
                    counter--;
                    System.out.println(counter + "is the time left");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();


//        final Handler mHandler = new Handler(Looper.getMainLooper()) {
//            @Override
//            public void handleMessage(Message message) {
//                Bundle bundle = message.getData();
//                String string = bundle.getString("1");
//                String string2 = bundle.getString("2");
//                textView5.setText(string2);
//                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
//                System.out.println("hi   "+string2);
//            }
//        };
//        Thread thread = new Thread() {
//            long startTime = System.nanoTime();
//            long temp = System.nanoTime();
//            int counter=10;
//            @Override
//            public void run() {
//                while(true)
//                {
//                    if(System.nanoTime()-temp>(int)Math.pow(10,9))
//                    {
////                        System.out.println(counter);
//                        temp=System.nanoTime();
//                        counter--;
//                        Message msg = mHandler.obtainMessage();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("1","Your score : " + score);
//                        bundle.putString("2",String.valueOf(counter));
//                        msg.setData(bundle);
//                        mHandler.sendMessage(msg);
//                    }
//                    if(rda.isChecked()||rdb.isChecked()||rdc.isChecked()||rdd.isChecked()||System.nanoTime()-startTime>5
//                            *(int)Math.pow(10,9))
//                    {
////                        Toast.makeText(MainActivity.this, "Your Score : "+score, Toast.LENGTH_SHORT).show();
//                        try {
//                            if (currentQ.getANSWER().equals(answer.getText())) {
//                                score++;
////                                res.setText("Your score : " + score);
//                            }
//                            if ((qid == 5) || (qid == 10)||(qid==15)||(qid==20)||(qid==25)||(qid==30)||(qid==35)) {
////                                Toast.makeText(MainActivity.this, "Final Score For Last Level Was: " + score, Toast.LENGTH_SHORT).show();
//                                if(qid==5){
//                                    textView.setText("LEVEL 2");
//                                }else
//                                if(qid==10){
//                                    textView.setText("LEVEL 3");
//                                }else
//                                if(qid==15){
//                                    textView.setText("LEVEL 4");
//                                }else
//                                if(qid==20){
//                                    textView.setText("LEVEL 5");
//                                }else
//                                if(qid==25){
//                                    textView.setText("LEVEL 6");
//                                }else
//                                if(qid==30){
//                                    textView.setText("LEVEL 7");
//                                }else
//                                if(qid==35){
//                                    textView.setText("LEVEL 8");
//                                }
//                                grp.clearCheck();
//                            }
//
//                            if (qid < 40) {
//                                currentQ = quesList.get(qid);
//                                setQuestionView();
//                            }
//                            else{
//                                Bundle b=new Bundle();
//                                b.putInt("score", score);
//                                Intent i1=new Intent(MainActivity.this, MainScreen.class);
//                                i1.putExtras(b);
//                                //stopService(intent1);
//                                finish();
//                                score=0;
//                                startActivity(i1);
//                            }
//                        } catch (Exception e) {
////                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                        if(qid==40){
//                            qid=0;
//                            break;
//                        }
//                        temp=System.nanoTime();
//                        startTime=System.nanoTime();
//                        counter=10;
//                        Message msg = mHandler.obtainMessage();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("1","Your score : " + score);
//                        bundle.putString("2",String.valueOf(counter));
//                        msg.setData(bundle);
//                        mHandler.sendMessage(msg);
//                    }
//                }
//            }
//        };
//        thread.start();
    }


}
