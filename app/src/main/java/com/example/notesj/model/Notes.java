package com.example.notesj.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Notes extends RealmObject {
    @PrimaryKey
        private String note;
        private String date;
        String id;

        public Notes(){

        }

        public Notes(String id,String note,String date) {
            this.id = id;
            this.note = note;
            this.date = date;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
            return date;
        }

    public void setDate(String date) {
        this.date = date;
    }

    // creating getter and setter methods.
        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

}
