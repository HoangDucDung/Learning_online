package model;

public class QuestionLevel {

    private int id;
    private String levelName;

    public QuestionLevel() {

    }

    public QuestionLevel(int id, String levelName) {
        this.id = id;
        this.levelName = levelName;
    }

    public int getId() {
        return id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
