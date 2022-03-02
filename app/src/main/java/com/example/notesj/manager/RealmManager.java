package com.example.notesj.manager;

import com.example.notesj.model.Notes;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmManager {
    public static final String TAG = RealmManager.class.getSimpleName();
    private static RealmManager realmManager;
    private static Realm realm;

    public static RealmManager getInstance() {
        if (realmManager == null)
            realmManager = new RealmManager();
        return realmManager;
    }

    public RealmManager() {
        // Get Default Realm
        realm = Realm.getDefaultInstance();
    }

    public void saveNote(Notes note) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(note);
        realm.commitTransaction();
    }

    public ArrayList<Notes> loadNotes() {
        RealmResults<Notes> results = realm.where(Notes.class).findAll();
        return new ArrayList<Notes>(results);
    }

}
