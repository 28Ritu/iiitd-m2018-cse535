package com.example.ritu.a2_2016078;

import java.util.ArrayList;
import java.util.HashMap;

public class SongMap_A2_2016078 {
    private HashMap<String, String> hashMap;

    public void initialiseSongList() {
        hashMap = new HashMap<String, String>();
        hashMap.put("Boyfriend-Justin Bieber", "android.resource://com.example.ritu.a2_2016078/" + R.raw.boyfriend);
        hashMap.put("Kabhi To Pass Mere Aao - Atif", "android.resource://com.example.ritu.a2_2016078/" + R.raw.kabhitohpaasmereaao);
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }
    public ArrayList<String> getKeys() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Boyfriend-Justin Bieber");
        arrayList.add("Kabhi To Pass Mere Aao - Atif");
        return arrayList;
    }
}
