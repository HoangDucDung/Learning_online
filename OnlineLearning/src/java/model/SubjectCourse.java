package model;

public class SubjectCourse {
    private Subject subjectId;
    private Course courseId;

    public SubjectCourse() {
    }

    public SubjectCourse(Subject subjectId, Course courseId) {
        this.subjectId = subjectId;
        this.courseId = courseId;
    }

    public Subject getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Subject subjectId) {
        this.subjectId = subjectId;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }
    
}