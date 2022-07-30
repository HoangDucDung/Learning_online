package model;

public class Question {

    private int id;
    private String text;
    private String imageUrl;
    private int lessonId;
    private QuestionLevel level;
    private int order;
    private boolean active;

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

    public String getQuestionText() {
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

}
