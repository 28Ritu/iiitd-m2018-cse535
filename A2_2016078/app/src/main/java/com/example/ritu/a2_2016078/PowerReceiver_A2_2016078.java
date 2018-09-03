package com.example.ritu.a2_2016078;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PowerReceiver_A2_2016078 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Device connected to power", Toast.LENGTH_SHORT).show();
    }
}
