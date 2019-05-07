package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCreate extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Quizzz.db";
    private static final String TABLE_QUEST="Quest";
    private static final String QUESTION = "Question" ;
    private static final String OPTIONA = "optiona" ;
    private static final String OPTIONB = "optionb" ;
    private static final String OPTIONC = "optionc" ;
    private static final String OPTIOND = "optiond" ;
    private static final String ANSWER = "answer" ;
    SQLiteDatabase dbase;
    public DatabaseCreate(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        db.execSQL("create table if not exists Quest(QID int primary key, Question varchar(500) not null, optiona varchar(50) not null, optionb varchar(50) not null, optionc varchar(50) not null, optiond varchar(50) not null, answer varchar(50) not null)");
        addQuestions();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Quest");
        onCreate(db);
    }
    private void addQuestions()
    {
        Question q1=new Question(1,"Which of these behaviours is a symptom of depression?","Cynicism", "Unexplained aggression","Loss of interest in all things", "Rapid mood swings", "Loss of interest in all things");
        this.addQuestion(q1);
        Question q2=new Question(2,"Which of these should you avoid if you're depressed?","A supportive relationship","Exercise","Major decisions","All of the above","All of the above");
        this.addQuestion(q2);
        Question q3=new Question(3,"How should you respond to a depressed person?","Be upbeat","Listen","Encourage the person to spend time alone","Keep the person company but don't talk about the depression","Keep the person company but don't talk about the depression" );
        this.addQuestion(q3);
        Question q4=new Question(4,"Proper nutrition may improve your state of mind. Which of these vitamins may help ease depressed moods","Vitamin C","Vitamin B-complex","Vitamin A","Vitamin E","Vitamin E");
        this.addQuestion(q4);
        Question q5=new Question(5,"A major cause of depression in women is","Increased stress","Sadness","Jealousy","Competitiveness","Competitiveness");
        this.addQuestion(q5);
        Question q6=new Question(6,"Most accidents can be prevented if road users:","Only drive during the day","Have good knowledge of traffic rules and regulations","Are able to anticipate and react accordingly to traffic hazards","Exercise patience on the road","Exercise patience on the road");
        this.addQuestion(q6);
        Question q7=new Question(7,"What is the name of the disease caused due to the deficiency of Vitamin B1:","Scurvy","Beriberi", "Pellagra", "Gingivitis","Pellagra");
        this.addQuestion(q7);
        Question q8=new Question(8,"How many blood types are there?","3 - A, B and O", "4 - A, B, AB and O", "2 - A and O","1 - AB","4 - A, B, AB and O");
        this.addQuestion(q8);
        Question q9=new Question(9,"A 32 year old woman has had three episodes of slurred speech and two episodes of transient weakness of both legs in the past five years. Each episode has resolved within three months. What is the SINGLE most likely diagnosis?", "Meningioma", "Migraine", "Multiple sclerosis", "Stroke", "Migraine" );
        this.addQuestion(q9);
        Question q10=new Question(10,"Night blindness drying of the conjunctiva, dry and scaly skin and loss of hair are some of the symptoms of :", "Vitamin K deficiency", "Vitamin A deficiency", "Iron deficiency", "Folic acid deficiency", "Vitamin A deficiency");
        this.addQuestion(q10);
        Question q11=new Question(11,"What are the effects of Vitamin B6 deficiency?", "Scurvy", "Beriberi", "Dermatomyoma", "Certain types of Eczema", "Dermatomyoma");
        this.addQuestion(q11);
        Question q12=new Question(12,"What is the condition known as, in which the body does not get its fair share of nutrients, either from starvation, or as a result of poor absorption :", "Marasmus", "Malnutrition", "Kwashiorkor", "Malnutrition and Marasmus", "Malnutrition");
        this.addQuestion(q12);
        Question q13=new Question(13,"An eight year old girl has had left earache for two days. The earache subsided about two hours ago with the onset of a purulent discharge which relieved the pain. Her temperature is 39.2°C. What is the SINGLE most appropriate antibiotic?", "Amoxicillin", "Ciprofloxacin", "Erythromycin", "Flucloxacillin", "Erythromycin");
        this.addQuestion(q13);
        Question q14=new Question(14,"Driving faster than traffic and continually passing other cars:", "Increases fuel efficiency", "Helps prevent traffic congestion", "Increases your chance of an accident", "Is ok if you are a parent and late picking up your child", "Increases fuel efficiency");
        this.addQuestion(q14);
        Question q15=new Question(15,"A 65 year old man had closure of colostomy performed five days ago. He is not systemically unwell. There is a tender, localised fluctuant swelling 4 cm in diameter in the wound. What is the single most appropriate management?", "Abdominal support", "Antibiotics", "Laparotomy and re-suture", "Local exploration of wound", "Antibiotics");
        this.addQuestion(q15);
        Question q16=new Question(16,"At roundbouts in India:", "Traffic flows from right to left", "Traffic Moves in Clockwise-direction", "Traffic from right has priority", "All of the above", "All of the above");
        this.addQuestion(q16);
        Question q17=new Question(17,"On expressway while driving in the car, you will normally use", "The left lane", "The middle lane", "The right lane", "The hard shoulder", "The middle lane");
        this.addQuestion(q17);
        Question q18=new Question(18,"While you intend to take a right or left turn first you have to do in following sequence:", "Gear - mirror - signal", "Mirror - gear - signal", "Signal - gear - mirror", "None of the above", "Signal - gear - mirror");
        this.addQuestion(q18);
        Question q19=new Question(19,"The safest way to stop the vehicle is:", "Press clutch and then brake", "Press brake and then clutch", "Press brake and clutch at a time", "None of the above", "Press brake and then clutch");
        this.addQuestion(q19);
        Question q20=new Question(20,"What is the normal BMI index range?", "< 18", "1-6", "18-24", ">24", "18-24");
        this.addQuestion(q20);
    }
    public void addQuestion(Question quest) {
        ContentValues values = new ContentValues();
        values.put(QUESTION, quest.getQUESTION());
        values.put(ANSWER, quest.getANSWER());
        values.put(OPTIONA, quest.getOPTA());
        values.put(OPTIONB, quest.getOPTB());
        values.put(OPTIONC, quest.getOPTC());
        values.put(OPTIOND, quest.getOPTD());
        dbase.insert("Quest", null, values);
    }
    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<Question>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setQUESTION(cursor.getString(1));
                quest.setOPTA(cursor.getString(2));
                quest.setOPTB(cursor.getString(3));
                quest.setOPTC(cursor.getString(4));
                quest.setOPTD(cursor.getString(5));
                quest.setANSWER(cursor.getString(6));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        return quesList;
    }
}