package model;

import java.sql.Timestamp;

public class Note {
    private int noteID;
    private Lesson lessonID;
    private String noteDescription;
    private Timestamp createdTime;
    private String noteTimeInVideo;
    private Account accountID;

    public Note() {
    }

    public Note(int noteID, Lesson lessonID, String noteDescription, Timestamp createdTime, String noteTimeInVideo, Account accountID) {
        this.noteID = noteID;
        this.lessonID = lessonID;
        this.noteDescription = noteDescription;
        this.createdTime = createdTime;
        this.noteTimeInVideo = noteTimeInVideo;
        this.accountID = accountID;
    }
    
    public Account getAccountID() {
        return accountID;
    }

    public void setAccountID(Account accountID) {
        this.accountID = accountID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public Lesson getLessonID() {
        return lessonID;
    }

    public void setLessonID(Lesson lessonID) {
        this.lessonID = lessonID;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getNoteTimeInVideo() {
        return noteTimeInVideo;
    }

    public void setNoteTimeInVideo(String noteTimeInVideo) {
        this.noteTimeInVideo = noteTimeInVideo;
    }
    
    
}
