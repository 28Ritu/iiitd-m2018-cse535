package com.example.ritu.a3_2016078;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuestionDBHelper_A3_2016078 extends SQLiteOpenHelper {

    private static final String DB_NAME = "QuestionDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Questions";
    private static final String QUESTION_COLUMN_ID = "_id";
    private static final String QUESTION_COLUMN_TITLE = "Question";
    private static final String QUESTION_COLUMN_ANSWER = "Answer";
    private static List<Question_A3_2016078> questionList;
    private static QuestionDBHelper_A3_2016078 questionDBHelper;

    private  QuestionDBHelper_A3_2016078(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static QuestionDBHelper_A3_2016078 get(Context context) {
        if (questionDBHelper == null) {
            questionDBHelper = new QuestionDBHelper_A3_2016078(context);
        }
        return questionDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateQuestionDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        updateQuestionDatabase(sqLiteDatabase, oldVersion, newVersion);
    }

    private void updateQuestionDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + QUESTION_COLUMN_ID
                    + " INTEGER PRIMARY KEY , " + QUESTION_COLUMN_TITLE + " TEXT, "
                    + QUESTION_COLUMN_ANSWER + " TEXT)");
            insertQuestions(db, 1, "Freeware is a software that is available for use at no monetary cost.");
            insertQuestions(db,2, "IPv6 Internet Protocol address is represented as eight groups of four Octal digits.");
            insertQuestions(db, 3, "The hexadecimal number system contains digits from 1 - 15.");
            insertQuestions(db, 4, "CPU stands for Central Processing Unit.");
            insertQuestions(db, 5, "The Language that the computer can understand is called Machine Language.");
            insertQuestions(db, 6, "Twitter is an online social networking and blogging service.");
            insertQuestions(db, 7, "Dot-matrix Deskjet Inkjet and Laser are all types of Scanners.");
            insertQuestions(db, 8, "A firewall is a type of hardware.");
            insertQuestions(db, 9, "The operating system serves as an intermediary between the user and the computer hardware.");
            insertQuestions(db, 10, "A process is a running program");
            insertQuestions(db, 11, "The operating system serves as an intermediary between a process and the computer hardware.");
            insertQuestions(db, 12, "Linux was developed by Microsoft.");
            insertQuestions(db, 13, "Multitasking requires multiple processors.");
            insertQuestions(db, 14, "Inside a computer data is stored in binary form.");
            insertQuestions(db, 15, "When a single user runs two copies of the same program concurrently (e.g. two instances of NotePad) two processes are created.");
            insertQuestions(db, 16, "There are only 5 layers in OSI model.");
            insertQuestions(db, 17, "HTTP stands for HyperText Transfer Protocol");
            insertQuestions(db, 18, "WiFi is a wireless technology.");
            insertQuestions(db, 19, "One byte is equal to 8 bits.");
            insertQuestions(db, 20, "Bit stands for Binary Digit.");
            insertQuestions(db, 21, "Cache memory helps to prevent bottlenecks between a CPU and random access memory.");
            insertQuestions(db, 22, "A disk drive is an example of a primary storage device.");
            insertQuestions(db, 23, "Session layer is a part of IP stack.");
            insertQuestions(db, 24, "Go-Back-N is a sliding window protocol.");
            insertQuestions(db, 25, "Broadcasting is of 2 types: Limited and Directed.");
            insertQuestions(db, 26, "In Go-Back-N N is the window size of receiver.");
            insertQuestions(db, 27, "Stop-and-wait protocol is the most efficient protocol.");
            insertQuestions(db, 28, "TCP uses 3-way Handshake.");
            insertQuestions(db, 29, "A router is an end-system.");
            insertQuestions(db, 30, "Internet Protocol is a protocol of Link layer.");
        } else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }

    public void insertQuestions(SQLiteDatabase sqLiteDatabase, int id, String question) {
        ContentValues questionValues = new ContentValues();
        questionValues.put(QUESTION_COLUMN_ID, id);
        questionValues.put(QUESTION_COLUMN_TITLE, question);
        sqLiteDatabase.insert(TABLE_NAME, null, questionValues);
    }

    public Cursor getCursor(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{QUESTION_COLUMN_ID,
                QUESTION_COLUMN_TITLE, QUESTION_COLUMN_ANSWER}, QUESTION_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public Question_A3_2016078 getQuestion(int id) {
        Cursor cursor = getCursor(id);
        Question_A3_2016078 question = new Question_A3_2016078();
        question.setID(cursor.getInt(0));
        question.setTitle("Question " + question.getID());
        question.setQuestion(cursor.getString(1));
        return question;
    }

    public List<Question_A3_2016078> getQuestionList() {
        questionList = new ArrayList<>();
        questionList.clear();

        for (int i = 1; i <= 30; i++) {
            Cursor cursor = getCursor(i);
            Question_A3_2016078 question = new Question_A3_2016078();
            question.setID(cursor.getInt(0));
            question.setTitle("Question " + question.getID());
            question.setQuestion(cursor.getString(1));

            questionList.add(question);
        }

        return questionList;
    }

    public boolean saveAnswer(int id, String answer) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION_COLUMN_ANSWER, answer);
        sqLiteDatabase.update(TABLE_NAME, contentValues, QUESTION_COLUMN_ID + "=?",
                new String[]{Integer.toString(id)});
        return true;
    }
}
