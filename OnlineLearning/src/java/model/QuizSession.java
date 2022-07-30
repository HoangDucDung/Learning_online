package model;

import java.time.LocalDateTime;

public class QuizSession {

    private int id;
    private int accountId;
    private int quizLessonId;
    private LocalDateTime startTime;
    private LocalDateTime expiredTime;

    public QuizSession(int id, int accountId, int quizLessonId, LocalDateTime startTime, LocalDateTime expiredTime) {
        this.id = id;
        this.accountId = accountId;
        this.quizLessonId = quizLessonId;
        this.startTime = startTime;
        this.expiredTime = expiredTime;
    }

    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getQuizLessonId() {
        return quizLessonId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setQuizLessonId(int quizLessonId) {
        this.quizLessonId = quizLessonId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }
}
