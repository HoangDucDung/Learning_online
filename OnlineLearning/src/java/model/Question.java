package model;

import com.google.gson.annotations.Expose;
import java.util.List;

public class Question {

    @Expose
    private int id;
    
    @Expose
    private String text;
    
    private String imageUrl;
    private int lessonId;
    private int questionLevelId;
    
    @Expose
    private int order;
    private boolean active;

    @Expose
    private QuestionLevel level;
    
    @Expose
    private List<Answer> answers;

    public Question() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }
    
    public void setLevel(QuestionLevel level) {
        this.level = level;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    @Deprecated
    public String getQuestionText() {
        return text;
    }
    
    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLessonId() {
        return lessonId;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public int getOrder() {
        return order;
    }

    public boolean isActive() {
        return active;
    }

    public List<Answer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Question other = (Question) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int getQuestionLevelId() {
        return questionLevelId;
    }

    public void setQuestionLevelId(int questionLevelId) {
        this.questionLevelId = questionLevelId;
    }

}
