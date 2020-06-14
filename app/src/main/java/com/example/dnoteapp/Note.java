package com.example.dnoteapp;

public class Note {
    private long id;
    private String content;
    private String time;
    private int tag;

    public Note(){

    }
    public Note(String content,String time,int tag){
        this.content = content;
        this.time = time;
        this.tag = tag;
    }
}
