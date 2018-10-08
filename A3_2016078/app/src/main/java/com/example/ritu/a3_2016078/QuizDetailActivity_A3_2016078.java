package com.example.ritu.a3_2016078;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuizDetailActivity_A3_2016078 extends AddFragmentActivity_A3_2016078 {

    private static final String EXTRA_QUESTION_ID = "com.example.ritu.a3_2016078.question_id";

    public static Intent newIntent(Context packageContext, int question_id) {
        Intent intent = new Intent(packageContext, QuizDetailActivity_A3_2016078.class);
        intent.putExtra(EXTRA_QUESTION_ID, question_id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int question_id = (int) getIntent().getSerializableExtra(EXTRA_QUESTION_ID);
        return QuizDetailFragment_A3_2016078.newInstance(question_id);
    }
}
