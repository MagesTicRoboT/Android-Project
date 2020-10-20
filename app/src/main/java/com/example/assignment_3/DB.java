package com.example.assignment_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_3.model.Note;
import com.example.assignment_3.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    public DB(@Nullable Context context) {
        super(context, "DB", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String UserTable = "Create table user(" +
                "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FName TEXT," +
                "LName TEXT," +
                "UserName TEXT," +
                "Email TEXT," +
                "Address TEXT," +
                "PhNo TEXT," +
                "Password)";
        String NoteTable = "Create table note(" +
                "NoteID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "LocationName TEXT," +
                "Description TEXT," +
                "MyImage BLOB)";
        db.execSQL(NoteTable);
        db.execSQL(UserTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean Insert_into_User(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("FName",user.getFName());
        cv.put("LName",user.getLName());
        cv.put("UserName",user.getUserName());
        cv.put("Email",user.getEmail());
        cv.put("Address",user.getAddress());
        cv.put("Password",user.getPassword());

        long success = db.insert("user",null,cv);

        if (success == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean Insert_Into_Note(Note note){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("LocationName", note.getLocationName());
        cv.put("Description",note.getDescription());
        cv.put("MyImage",note.getMyImage());

        long success = db.insert("note", null, cv);
        if (success == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public boolean Check_User(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user",
                new String[]{"UserName", "Password"},
                "UserName=?",
                new String[]{user.getUserName()},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            User check = new User(cursor.getString(0), cursor.getString(1));

            if (user.getPassword().equalsIgnoreCase(check.getPassword())) {
                return true;
            }
        }
        db.close();
        cursor.close();
        return false;
    }
    public boolean isUserNameExists(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("user",
                new String[]{"UserName", "Email"},
                "UserName=?",
                new String[]{userName},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {

            db.close();
            cursor.close();
            return true;
        }
        else{
            db.close();
            cursor.close();
            return false;
        }



    }
    public List<Note> getAllNotes() {
        List<Note> NoteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM note ORDER BY LocationName";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getBlob(3));

                // Adding contact to list
                NoteList.add(note);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        cursor.close();
        // return contact list
        return NoteList;
    }

    public boolean updateData(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("LocationName",note.getLocationName());
        contentValues.put("Description",note.getDescription());
        contentValues.put("MyImage",note.getMyImage());
        db.update("note", contentValues, "NoteID = ?",new String[]{String.valueOf(note.getNoteID())});
        return true;
    }

    public Integer deleteData (Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("note", "NoteID = ?",new String[] {String.valueOf(note.getNoteID())});
    }
}
