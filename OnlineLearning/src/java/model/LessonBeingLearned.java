package model;

public class LessonBeingLearned {
    private Account accountID;
    private Lesson lessonID;
    private int timeContinue;

    public LessonBeingLearned() {
    }

    public LessonBeingLearned(Account accountID, Lesson lessonID, int timeContinue) {
        this.accountID = accountID;
        this.lessonID = lessonID;
        this.timeContinue = timeContinue;
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

    public int getTimeContinue() {
        return timeContinue;
    }

    public void setTimeContinue(int timeContinue) {
        this.timeContinue = timeContinue;
    }
    
}
