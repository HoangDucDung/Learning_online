package model;

import java.util.ArrayList;

public class SubLesson {
    private int subLessonID;
    private Course courseID;
    private String name;
    private ArrayList<Lesson> listLesson;

    public SubLesson() {
    }

    public SubLesson(int subLessonID, Course courseID, String name, ArrayList<Lesson> listLesson) {
        this.subLessonID = subLessonID;
        this.courseID = courseID;
        this.name = name;
        this.listLesson = listLesson;
    }

    public int getSubLessonID() {
        return subLessonID;
    }

    public void setSubLessonID(int subLessonID) {
        this.subLessonID = subLessonID;
    }

    public Course getCourseID() {
        return courseID;
    }

    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Lesson> getListLesson() {
        return listLesson;
    }

    public void setListLesson(ArrayList<Lesson> listLesson) {
        this.listLesson = listLesson;
    } 
    
}
