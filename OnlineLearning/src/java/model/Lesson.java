package model;

import java.sql.Timestamp;

public class Lesson {
    private int lessonID;
    private Course courseID;
    private LessonType lessonTypeID;
    private String name;
    private Timestamp CreatedTime;
    private Timestamp UpdatedTime;
    private String wideImageUrl;
    private String tinyImageUrl;
    private int order;
    private boolean status;
    private SubLesson subLessonID;
    private String videoUrl;
    private Timestamp startLearningTime; 
    
    public Lesson() {
    }

    public Lesson(int lessonID, Course courseID, LessonType lessonTypeID, String name, Timestamp CreatedTime, Timestamp UpdatedTime, String wideImageUrl, String tinyImageUrl, int order, boolean status, SubLesson subLessonID, String videoUrl, Timestamp startLearningTime) {
        this.lessonID = lessonID;
        this.courseID = courseID;
        this.lessonTypeID = lessonTypeID;
        this.name = name;
        this.CreatedTime = CreatedTime;
        this.UpdatedTime = UpdatedTime;
        this.wideImageUrl = wideImageUrl;
        this.tinyImageUrl = tinyImageUrl;
        this.order = order;
        this.status = status;
        this.subLessonID = subLessonID;
        this.videoUrl = videoUrl;
        this.startLearningTime = startLearningTime;
    }

    

    public Timestamp getStartLearningTime() {
        return startLearningTime;
    }

    public void setStartLearningTime(Timestamp startLearningTime) {
        this.startLearningTime = startLearningTime;
    }
    
    
    @Deprecated
    public int getLessonID() {
        return lessonID;
    }

    @Deprecated
    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public Course getCourseID() {
        return courseID;
    }

    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }

    public LessonType getLessonTypeID() {
        return lessonTypeID;
    }

    public void setLessonTypeID(LessonType lessonTypeID) {
        this.lessonTypeID = lessonTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(Timestamp CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public Timestamp getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(Timestamp UpdatedTime) {
        this.UpdatedTime = UpdatedTime;
    }

    public String getWideImageUrl() {
        return wideImageUrl;
    }

    public void setWideImageUrl(String wideImageUrl) {
        this.wideImageUrl = wideImageUrl;
    }

    public String getTinyImageUrl() {
        return tinyImageUrl;
    }

    public void setTinyImageUrl(String tinyImageUrl) {
        this.tinyImageUrl = tinyImageUrl;
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

    public SubLesson getSubLessonID() {
        return subLessonID;
    }

    public void setSubLessonID(SubLesson subLessonID) {
        this.subLessonID = subLessonID;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }  

    public int getId() {
        return this.lessonID;
    } 
    
    public void setId(int id) {
        this.lessonID = id;
    }
}
