package com.example.dnoteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Update {
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    private static final String[] colums= {
            NoteDatabase.ID,
            NoteDatabase.CONTENT,
            NoteDatabase.TIME,
            NoteDatabase.MODE,
    };

    public Update(Context context){
        dbhandler = new NoteDatabase(context);
    }

    public void open(){
        db = dbhandler.getWritableDatabase();
    }

    public void close(){
        dbhandler.close();
    }
    //将note添加入database
    public Note addNote(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDatabase.CONTENT,note.getContent());
        contentValues.put(NoteDatabase.TIME,note.getTime());
        contentValues.put(NoteDatabase.MODE,note.getTag());
        Long insertID = db.insert(NoteDatabase.TABLE_NAME,null,contentValues);
        note.setId(insertID);
        return note;
    }

    public Note getNote(long id){
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME,colums,NoteDatabase.ID+"=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor != null)cursor.moveToFirst();
        Note e = new Note(cursor.getString(1),cursor.getString(2),cursor.getInt(3));
        return e;
    }

    public List<Note> getAllNotes(){
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME,colums,null,null,null,null,null);

        List<Note> notes = new ArrayList<>();
        if(cursor.getCount()>0){
            while (cursor.moveToFirst()){
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteDatabase.ID)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDatabase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDatabase.TIME)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(NoteDatabase.MODE)));
                notes.add(note);
            }
        }
        return notes;
    }

    public int updateNote (Note note){

        ContentValues values = new ContentValues();
        values.put(NoteDatabase.CONTENT,note.getContent());
        values.put(NoteDatabase.ID,note.getId());
        values.put(NoteDatabase.TIME,note.getTime());
        values.put(NoteDatabase.MODE,note.getTag());

        return db.update(NoteDatabase.TABLE_NAME,values,NoteDatabase.ID+"=?"+note.getId(),null);
    }

}
