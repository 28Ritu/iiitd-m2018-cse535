package com.example.ritu.a2_2016078;

import java.util.ArrayList;
import java.util.HashMap;

public class SongMap_A2_2016078 {
    private HashMap<String, String> hashMap;

    public void initialiseSongList() {
        hashMap = new HashMap<String, String>();
        hashMap.put("Boyfriend-Justin Bieber", "android.resource://com.example.ritu.a2_2016078/" + R.raw.boyfriend);
        hashMap.put("Kabhi To Pass Mere Aao - Atif", "android.resource://com.example.ritu.a2_2016078/" + R.raw.kabhitohpaasmereaao);
        hashMap.put("Nit Khair Manga", "android.resource://com.example.ritu.a2_2016078/" + R.raw.nitkhairmangaraid);
        hashMap.put("Sanu Ek Pal Chain", "android.resource://com.example.ritu.a2_2016078/" + R.raw.sanuekpalchain);
        hashMap.put("Tere Liye", "android.resource://com.example.ritu.a2_2016078/" + R.raw.tereliye);
    }

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }
    public ArrayList<String> getKeys() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Boyfriend-Justin Bieber");
        arrayList.add("Kabhi To Pass Mere Aao - Atif");
        arrayList.add("Nit Khair Manga");
        arrayList.add("Sanu Ek Pal Chain");
        arrayList.add("Tere Liye");
        return arrayList;
    }
}
