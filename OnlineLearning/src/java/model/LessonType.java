package model;

public class LessonType {

    private int lessonTypeID;
    private String name;
    private int order;
    private boolean status;
    private String type;

    public LessonType() {
    }

    public LessonType(int lessonTypeID, String name, int order, boolean status, String type) {
        this.lessonTypeID = lessonTypeID;
        this.name = name;
        this.order = order;
        this.status = status;
        this.type = type;
    }

    public int getLessonTypeID() {
        return lessonTypeID;
    }

    public void setLessonTypeID(int lessonTypeID) {
        this.lessonTypeID = lessonTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
