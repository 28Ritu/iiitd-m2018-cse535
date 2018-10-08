package com.example.ritu.a3_2016078;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class QuizListFragment_A3_2016078 extends Fragment {

    private RecyclerView mQuizRecyclerView;
    private QuestionAdapter mAdapter;
    private QuestionDBHelper_A3_2016078 questionDBHelper;

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
        questionDBHelper = QuestionDBHelper_A3_2016078.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_list, container, false);
        mQuizRecyclerView = (RecyclerView) view.findViewById(R.id.quiz_recycler_view);
        mQuizRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mQuizRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        updateUI();
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
        updateUI();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myBroadReceiver, new IntentFilter("message"));

    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myBroadReceiver);
    }

    private void updateUI() {
        List<Question_A3_2016078> questionList = questionDBHelper.getQuestionList();

        if (mAdapter == null) {
            mAdapter = new QuestionAdapter(questionList);
            mQuizRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Question_A3_2016078 mQuestion;
        private TextView questionTextView;

        public QuizHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            questionTextView = (TextView) itemView.findViewById(R.id.tv_question);
        }

        public void bind(Question_A3_2016078 question) {
            mQuestion = question;
            questionTextView.setText(mQuestion.getTitle());
        }


        @Override
        public void onClick(View view) {
            int clickedItemPos = getAdapterPosition();
            Intent intent = QuizDetailActivity_A3_2016078.newIntent(getActivity(), clickedItemPos + 1);
            startActivity(intent);
        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuizHolder> {
        private List<Question_A3_2016078> questionList;

        public QuestionAdapter(List<Question_A3_2016078> mQuestionList) {
            questionList = mQuestionList;
        }

        @Override
        public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View view = inflater.inflate(R.layout.list_item_question, parent, false);
            QuizHolder quizHolder = new QuizHolder(view);

            return quizHolder;
        }

        @Override
        public void onBindViewHolder(QuizHolder holder, int position) {
            Question_A3_2016078 question = questionList.get(position);
            holder.bind(question);
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }
    }
}
