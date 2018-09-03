package com.example.ritu.a2_2016078;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FirstFragment_A2_2016078 extends Fragment {

    private ListView listView;
    private String songnames[] = {};
    private ArrayList<String> songList;
    public ArrayAdapter<String> adapter;
    private ImageButton button;
    private boolean isPlaying = false;

    private BroadcastReceiver myBroadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("DOWNLOAD");
            if (action.equals("Failure")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("Cannot Load URL");
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            else if (action.equals("Success")) {
                String filename = intent.getStringExtra("filename");
                songList.add(filename);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_first_a2_2016078, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview);
        button = (ImageButton) rootView.findViewById(R.id.btn_handle);

        songnames = getActivity().getResources().getStringArray(R.array.songlist);
        songList = new ArrayList<>(Arrays.asList(songnames));

        File[] files = getActivity().getFilesDir().listFiles();
        for (File file:files) {
            if (file.getName().contains(".mp3")) {
                Log.d("filelist", file.getName());
                songList.add(file.getName());
            }
        }

        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, songList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String song = adapter.getItem(i);
                Intent intent = new Intent(getActivity(), FirstService_A2_2016078.class);
                intent.putExtra("song", song);
                intent.setAction(getActivity().getResources().getString(R.string.ACTION_START_FOREGROUND_SERVICE));
                button.setBackgroundResource(android.R.drawable.ic_media_pause);
                isPlaying = true;
                getActivity().startService(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FirstService_A2_2016078.class);
                if (!isPlaying) {
                    Log.d("here", "ami");
                    button.setBackgroundResource(android.R.drawable.ic_media_pause);
                    intent.putExtra("song", songList.get(0));
                    intent.setAction(getActivity().getResources().getString(R.string.ACTION_START_FOREGROUND_SERVICE));
                    getActivity().startService(intent);
                    isPlaying = true;
                }
                else {
                    Log.d("here", "iam");
                    button.setBackgroundResource(android.R.drawable.ic_media_play);
                    intent.setAction(getActivity().getResources().getString(R.string.ACTION_STOP_FOREGROUND_SERVICE));
                    getActivity().startService(intent);
                    isPlaying = false;
                }
            }
        });

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.download) {
            Intent intent = new Intent(getActivity(), SecondService_A2_2016078.class);
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
