package com.example.abm2.Model;

public class Notes {
    private String noteInformation;
    private int noteId;
    private int noteCourseId;

    public Notes(String noteInformation,int noteCourseId) {
        this.noteInformation = noteInformation;
        this.noteCourseId = noteCourseId;
    }

    public String getNoteInformation() {
        return noteInformation;
    }

    public void setNoteInformation(String noteInformation) {
        this.noteInformation = noteInformation;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getNoteCourseId() {
        return noteCourseId;
    }

    public void setNoteCourseId(int noteCourseId) {
        this.noteCourseId = noteCourseId;
    }
}
