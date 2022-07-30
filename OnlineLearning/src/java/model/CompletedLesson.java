package model;

import java.sql.Timestamp;

public class CompletedLesson {

    private Account accountID;
    private Lesson lessonID;
    private int score;
    private boolean status;
    private Timestamp startTime;
    private Timestamp endTime;

    public CompletedLesson() {
    }

    public CompletedLesson(Account accountID, Lesson lessonID, int score, boolean status, Timestamp startTime, Timestamp endTime) {
        this.accountID = accountID;
        this.lessonID = lessonID;
        this.score = score;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Account getAccountID() {
        return accountID;
    }

    public void setAccountID(Account accountID) {
        this.accountID = accountID;
    }

    public Lesson getLessonID() {
        return lessonID;
    }

    public void setLessonID(Lesson lessonID) {
        this.lessonID = lessonID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

}
