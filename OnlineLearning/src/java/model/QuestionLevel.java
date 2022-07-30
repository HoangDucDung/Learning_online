package model;

public class QuestionLevel {

    private int id;
    private String levelName;
    private int order;
    private boolean status;
    private String type;

    public QuestionLevel() {
    }

    public QuestionLevel(int id, String levelName, int order, boolean status, String type) {
        this.id = id;
        this.levelName = levelName;
        this.order = order;
        this.status = status;
        this.type = type;
    }

    public QuestionLevel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
