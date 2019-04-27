package com.example.quiz;

import android.os.CountDownTimer;
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

public class MainActivity extends AppCompatActivity {
    List<Question> quesList;
    static int score=0;
    int qid=0;
    Question currentQ;
    TextView txtQuestion;
    TextView res;
    static TextView textView,textView4,textView5,textView6,textView7;
    RadioButton rda, rdb, rdc, rdd;
    Button butNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseCreate db = new DatabaseCreate(this);
        quesList = db.getAllQuestions();
        currentQ = quesList.get(qid);
        final Intent intent1=new Intent(MainActivity.this,SongService.class);
        txtQuestion = (TextView) findViewById(R.id.textView2);
        textView = (TextView) findViewById(R.id.textView);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        rda = (RadioButton) findViewById(R.id.radioButton);
        rdb = (RadioButton) findViewById(R.id.radioButton2);
        rdc = (RadioButton) findViewById(R.id.radioButton3);
        rdd = (RadioButton) findViewById(R.id.radioButton4);
//        butNext = (Button) findViewById(R.id.next);
        res = (TextView) findViewById(R.id.textView3);
        setQuestionView();
        String name = "Level 1";
        textView.setText(name);
        startService(intent1);
        res.setText("Your Score : "+score);
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                new CountDownTimer(30000, 1000){
//                    public int counter=10;
//                    public void onTick(long millisUntilFinished){
//                        textView5.setText(String.valueOf(counter));
//                        counter--;
//                    }
//                    public  void onFinish(){
//                        textView5.setText(String.valueOf(0));
//                    }
//                }.start();
                RadioGroup grp = (RadioGroup) findViewById(R.id.radioGroup);
                RadioButton answer = (RadioButton) findViewById(grp.getCheckedRadioButtonId());
                try {
                    if (currentQ.getANSWER().equals(answer.getText())) {
                        score++;
                        res.setText("Your score : " + score);
                    }
                    if ((qid == 5) || (qid == 10)||(qid==15)||(qid==20)||(qid==25)||(qid==30)||(qid==35)) {
                        Toast.makeText(MainActivity.this, "Final Score For Last Level Was: " + score, Toast.LENGTH_SHORT).show();
                        if(qid==5){
                            textView.setText("LEVEL 2");
                        }else
                        if(qid==10){
                            textView.setText("LEVEL 3");
                        }else
                        if(qid==15){
                            textView.setText("LEVEL 4");
                        }else
                        if(qid==20){
                            textView.setText("LEVEL 5");
                        }else
                        if(qid==25){
                            textView.setText("LEVEL 6");
                        }else
                        if(qid==30){
                            textView.setText("LEVEL 7");
                        }else
                        if(qid==35){
                            textView.setText("LEVEL 8");
                        }
                        grp.clearCheck();
                    }

                    if (qid < 40) {
                        currentQ = quesList.get(qid);
                        setQuestionView();
                    }
                    else{
                        Bundle b=new Bundle();
                        b.putInt("score", score);
                        Intent i1=new Intent(MainActivity.this, MainScreen.class);
                        i1.putExtras(b);
                        stopService(intent1);
                        finish();
                        score=0;
                        startActivity(i1);
                    }
                } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        },0,1000);
        if(qid==40){
            qid=0;
        }
    }
    private void setQuestionView()
    {
        txtQuestion.setText(currentQ.getQUESTION());
        rda.setText(currentQ.getOPTA());
        rdb.setText(currentQ.getOPTB());
        rdc.setText(currentQ.getOPTC());
        rdd.setText(currentQ.getOPTD());
        qid++;
    }
}
