package com.example.ritu.a1_2016078;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity_A1_2016078 extends AppCompatActivity {

    private EditText name, roll, branch, course1, course2, course3, course4;
    private Button submit, clear;
    private String user_name, user_roll, user_branch, user_course1, user_course2, user_course3,
            user_course4;

    private static final String TAG = "State of activity "+ FirstActivity_A1_2016078.class.getSimpleName()
            + " changed from ";

    private String create, start, resume, pause, stop, destroy, restart, info, trace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first__a1_2016078);
        this.setTitle("Registration App");

        create = getString(R.string.create);
        start = getString(R.string.start);
        resume = getString(R.string.resume);
        pause = getString(R.string.pause);
        stop = getString(R.string.stop);
        destroy = getString(R.string.destroy);
        restart = getString(R.string.restart);

        info = getString(R.string.info);

        name = (EditText) findViewById(R.id.name_et);
        roll = (EditText) findViewById(R.id.roll_et);
        branch = (EditText) findViewById(R.id.branch_et);
        course1 = (EditText) findViewById(R.id.course1_et);
        course2 = (EditText) findViewById(R.id.course2_et);
        course3 = (EditText) findViewById(R.id.course3_et);
        course4 = (EditText) findViewById(R.id.course4_et);

        submit = (Button) findViewById(R.id.submit);
        clear = (Button) findViewById(R.id.clear);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name = name.getText().toString();
                user_roll = roll.getText().toString();
                user_branch = branch.getText().toString();
                user_course1 = course1.getText().toString();
                user_course2 = course2.getText().toString();
                user_course3 = course3.getText().toString();
                user_course4 = course4.getText().toString();

                if (user_name.matches("[a-zA-Z]+") && (user_roll.matches("[0-9]+") ||
                        (user_roll.startsWith("MT") && user_roll.substring(2).matches("[0-9]+"))
                        || (user_roll.startsWith("Phd") && user_roll.substring(3).matches("[0-9]+")))
                        && !user_branch.isEmpty() && !(user_course1.isEmpty() && user_course2.isEmpty() &&
                        user_course3.isEmpty() && user_course4.isEmpty())) {

                    Intent intent = new Intent(getApplicationContext(), SecondActivity_A1_2016078.class)
                            .putExtra("name", user_name)
                            .putExtra("roll", user_roll)
                            .putExtra("branch", user_branch)
                            .putExtra("course1", user_course1)
                            .putExtra("course2", user_course2)
                            .putExtra("course3", user_course3)
                            .putExtra("course4", user_course4);
                    startActivity(intent);
                }
                else {

                    if (user_name.isEmpty()) {

                        name.setHint(Html.fromHtml("<small>" + getResources().getString(R.string.name_et_hint_1) + "</small>"));
                        name.setHintTextColor(getResources().getColor(R.color.red));

                    } else if (!user_name.matches("[a-zA-Z]+")) {

                        name.getText().clear();
                        name.setHint(Html.fromHtml("<small>"+ getResources().getString(R.string.name_et_hint_2) + "</small>"));
                        name.setHintTextColor(getResources().getColor(R.color.red));
                    }
                    if (user_roll.isEmpty()) {

                        roll.setHint(Html.fromHtml("<small>" + getResources().getString(R.string.roll_et_hint_1) + "</small>"));
                        roll.setHintTextColor(getResources().getColor(R.color.red));

                    } else if (!user_roll.matches("[0-9]+")) {

                        roll.getText().clear();
                        roll.setHint(Html.fromHtml("<small>" + getResources().getString(R.string.roll_et_hint_2) + "</small>"));
                        roll.setHintTextColor(getResources().getColor(R.color.red));
                    }
                    if (user_branch.isEmpty()) {

                        branch.setHint(Html.fromHtml("<small>" + getResources().getString(R.string.branch_et_hint) + "</small>"));
                        branch.setHintTextColor(getResources().getColor(R.color.red));
                    }
                    if (user_course1.isEmpty() && user_course2.isEmpty() && user_course3.isEmpty()
                            && user_course4.isEmpty()) {

                        course1.setHint(Html.fromHtml("<small>" + getResources().getString(R.string.course_et_hint) + "</small>"));
                        course1.setHintTextColor(getResources().getColor(R.color.red));

                        course2.setHint(Html.fromHtml("<small>" + getResources().getString(R.string.course_et_hint) + "</small>"));
                        course2.setHintTextColor(getResources().getColor(R.color.red));

                        course3.setHint(Html.fromHtml("<small>" + getResources().getString(R.string.course_et_hint) + "</small>"));
                        course3.setHintTextColor(getResources().getColor(R.color.red));

                        course4.setHint(Html.fromHtml("<small>" + getResources().getString(R.string.course_et_hint) + "</small>"));
                        course4.setHintTextColor(getResources().getColor(R.color.red));
                    }
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.getText().clear();
                roll.getText().clear();
                branch.getText().clear();
                course1.getText().clear();
                course2.getText().clear();
                course3.getText().clear();
                course4.getText().clear();

                name.setHint(null);
                roll.setHint(null);
                branch.setHint(null);
                course1.setHint(null);
                course2.setHint(null);
                course3.setHint(null);
                course4.setHint(null);
            }
        });

        if (trace == null) {
            Log.i(info, "State of activity " + FirstActivity_A1_2016078.class.getSimpleName()
                    + " " + create);
            Toast.makeText(getApplicationContext(), "State of activity "
                    + FirstActivity_A1_2016078.class.getSimpleName() + " " + create, Toast.LENGTH_SHORT)
                    .show();
        }
        else if (trace.equals(stop)) {
            Log.i(info, TAG + stop + " to " + create);
            Toast.makeText(getApplicationContext(), TAG + stop + " to " + create,
                    Toast.LENGTH_SHORT)
                    .show();
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