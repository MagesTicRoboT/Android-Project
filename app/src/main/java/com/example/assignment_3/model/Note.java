package com.example.assignment_3.model;

import android.graphics.Bitmap;

public class Note {
    private int NoteID;
    private String LocationName;
    private String Description;
    private byte[] MyImage;

    public Note(int noteID, String locationName, String description, byte[] myImage) {
        NoteID = noteID;
        LocationName = locationName;
        Description = description;
        MyImage = myImage;
    }
    public Note(int noteID){
        NoteID = noteID;
    }

    public Note(String locationName, String description, byte[] myImage) {
        LocationName = locationName;
        Description = description;
        MyImage = myImage;
    }

    public int getNoteID() {
        return NoteID;
    }

    public void setNoteID(int noteID) {
        NoteID = noteID;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public byte[] getMyImage() {
        return MyImage;
    }

    public void setMyImage(byte[] myImage) {
        MyImage = myImage;
    }
}
