package com.example.ritu.a3_2016078;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizDetailFragment_A3_2016078 extends Fragment {

    private static final String ARG_QUESTION_ID = "question_id";
    private String answer;
    private QuestionDBHelper_A3_2016078 questionDBHelper;
    private Question_A3_2016078 mQuestion;
    private TextView mQuestionNo;
    private TextView mquestion;
    private RadioGroup radioGroup;
    private RadioButton true_button;
    private RadioButton false_button;
    private Button save_button;
    private Button next_button;

    public static QuizDetailFragment_A3_2016078 newInstance(int questionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION_ID, questionId);

        QuizDetailFragment_A3_2016078 fragment = new QuizDetailFragment_A3_2016078();
        fragment.setArguments(args);
        return fragment;
    }

    private BroadcastReceiver myBroadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("UPLOAD");
            if (action.equals("Failure")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("Cannot Upload");
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else if (action.equals("Success")) {
                Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int questionId = (int) getArguments().getSerializable(ARG_QUESTION_ID);
        questionDBHelper = QuestionDBHelper_A3_2016078.get(getActivity());
        mQuestion = questionDBHelper.getQuestion(questionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_detail, container, false);
        mQuestionNo = (TextView) view.findViewById(R.id.questionNo);
        mquestion = (TextView) view.findViewById(R.id.question);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio);
        true_button = (RadioButton) view.findViewById(R.id.rb_true);
        false_button = (RadioButton) view.findViewById(R.id.rb_false);
        save_button = (Button) view.findViewById(R.id.save_button);
        next_button = (Button) view.findViewById(R.id.next_button);

        mQuestionNo.setText(Integer.toString(mQuestion.getID()) + ".");
        mquestion.setText(mQuestion.getQuestion());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_true)
                    answer = String.valueOf(true);
                else if (i == R.id.rb_false)
                    answer = String.valueOf(false);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionDBHelper.saveAnswer(mQuestion.getID(), answer);
                String saved = "Saved Answer: " + answer;
                Toast.makeText(getContext(), saved, Toast.LENGTH_SHORT).show();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int questionId = mQuestion.getID();
                if (questionId < 30)
                    mQuestion = questionDBHelper.getQuestion(questionId + 1);
                else if (questionId == 30)
                    mQuestion = questionDBHelper.getQuestion(1);
                mQuestionNo.setText(Integer.toString(mQuestion.getID()) + ".");
                mquestion.setText(mQuestion.getQuestion());
                radioGroup.clearCheck();
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.submit) {
            Intent intent = new Intent(getActivity(), FirstService_A3_2016078.class);
            getActivity().startService(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myBroadReceiver, new IntentFilter("message"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myBroadReceiver);
    }
}
