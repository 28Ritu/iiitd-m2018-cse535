package com.example.ritu.a1_2016078;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity_A1_2016078 extends AppCompatActivity {

    private TextView name, roll, branch, course1, course2, course3, course4;

    private static final String TAG = "State of activity "
            + SecondActivity_A1_2016078.class.getSimpleName() + " changed from ";

    private String create, start, resume, pause, stop, destroy, restart, info, trace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second__a1_2016078);
        this.setTitle("User Info");

        create = getString(R.string.create);
        start = getString(R.string.start);
        resume = getString(R.string.resume);
        pause = getString(R.string.pause);
        stop = getString(R.string.stop);
        destroy = getString(R.string.destroy);
        restart = getString(R.string.restart);

        info = getString(R.string.info);

        name = (TextView) findViewById(R.id.name_res);
        roll = (TextView) findViewById(R.id.roll_res);
        branch = (TextView) findViewById(R.id.branch_res);
        course1 = (TextView) findViewById(R.id.course1_res);
        course2 = (TextView) findViewById(R.id.course2_res);
        course3 = (TextView) findViewById(R.id.course3_res);
        course4 = (TextView) findViewById(R.id.course4_res);

        Intent intent = getIntent();
        String user_name = intent.getStringExtra("name");
        name.setText(user_name);

        String user_roll = intent.getStringExtra("roll");
        roll.setText(user_roll);

        String user_branch = intent.getStringExtra("branch");
        branch.setText(user_branch);

        String user_course1 = intent.getStringExtra("course1");
        course1.setText(user_course1);

        String user_course2 = intent.getStringExtra("course2");
        course2.setText(user_course2);

        String user_course3 = intent.getStringExtra("course3");
        course3.setText(user_course3);

        String user_course4 = intent.getStringExtra("course4");
        course4.setText(user_course4);

        if (trace == null) {
            Log.i(info, "State of activity " + SecondActivity_A1_2016078.class.getSimpleName()
                    + " " + create);
            Toast.makeText(getApplicationContext(), "State of activity "
                    + SecondActivity_A1_2016078.class.getSimpleName() + "  " + create, Toast.LENGTH_SHORT)
                    .show();
        }
        else if (trace.equals(stop)) {
            Log.i(info, TAG + stop + " to " + create);
            Toast.makeText(getApplicationContext(), TAG + stop + " to " + create,
                    Toast.LENGTH_SHORT).show();
        }
        trace = create;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (trace.equals(create)) {
            Log.i(info, TAG + create + " to " + start);
            Toast.makeText(getApplicationContext(), TAG + create + " to " + start,
                    Toast.LENGTH_SHORT).show();
        }
        else if (trace.equals(restart)) {
            Log.i(info, TAG + restart + " to " + start);
            Toast.makeText(getApplicationContext(), TAG + restart + " to " + start,
                    Toast.LENGTH_SHORT).show();
        }
        trace = start;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (trace.equals(start)) {
            Log.i(info, TAG + start + " to " + resume);
            Toast.makeText(getApplicationContext(), TAG + start + " to " + resume,
                    Toast.LENGTH_SHORT).show();
        }
        else if (trace.equals(pause)) {
            Log.i(info, TAG + pause + " to " + resume);
            Toast.makeText(getApplicationContext(), TAG + pause + " to " + resume,
                    Toast.LENGTH_SHORT).show();
        }
        trace = resume;
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(info, TAG + resume + " to " + pause);
        Toast.makeText(getApplicationContext(), TAG + resume + " to " + pause,
                Toast.LENGTH_SHORT).show();

        trace = pause;
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(info, TAG + pause + " to " + stop);
        Toast.makeText(getApplicationContext(), TAG + pause + " to " + stop,
                Toast.LENGTH_SHORT).show();

        trace = stop;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(info, TAG + stop + " to " + destroy);
        Toast.makeText(getApplicationContext(), TAG + stop + " to " + destroy,
                Toast.LENGTH_SHORT).show();

        trace = destroy;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(info, TAG + stop + " to " + restart);
        Toast.makeText(getApplicationContext(), TAG + stop + " to " + restart,
                Toast.LENGTH_SHORT).show();

        trace = restart;
    }
}