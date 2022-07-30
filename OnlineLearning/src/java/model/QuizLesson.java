package model;

import java.util.ArrayList;

public class QuizLesson {
    
    private String name;
    private int lessonID;
    private String note;
    private int passScore;
    private int examTimeInMinute;
    private int quizID;
    private int subjectID;
    private String level;
    private int totalQues;
    private String type;
    private TestType testype;
    private String subjectName;
    private ArrayList<Question> questions = new ArrayList<>();
    
    public QuizLesson() {
        
    }
    
    public QuizLesson(String name, int lessonID, String note, int passScore, int examTimeInMinute, int quizID, int subjectID, String level, int totalQues, String type, String subjectName) {
        this.name = name;
        this.lessonID = lessonID;
        this.note = note;
        this.passScore = passScore;
        this.examTimeInMinute = examTimeInMinute;
        this.quizID = quizID;
        this.subjectID = subjectID;
        this.level = level;
        this.totalQues = totalQues;
        this.type = type;
        this.subjectName = subjectName;
    }

    public QuizLesson(String name, int lessonID, String note, int passScore, int examTimeInMinute, int quizID, int subjectID, String level, int totalQues, String type, TestType testype, String subjectName) {
        this.name = name;
        this.lessonID = lessonID;
        this.note = note;
        this.passScore = passScore;
        this.examTimeInMinute = examTimeInMinute;
        this.quizID = quizID;
        this.subjectID = subjectID;
        this.level = level;
        this.totalQues = totalQues;
        this.type = type;
        this.testype = testype;
        this.subjectName = subjectName;
    }

    
    
    

    public QuizLesson(int LessonID, String Note, int PassScore) {
        this.lessonID = LessonID;
        this.note = Note;
        this.passScore = PassScore;
    }
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(int totalQues) {
        this.totalQues = totalQues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    /**
     * Use getId()
     * @return
     * @deprecated
     */
    @Deprecated
    public int getLessonID() {
        return lessonID;
    }

    /**
     * Use setId()
     * @param LessonID
     * @deprecated
     */
    @Deprecated
    public void setLessonID(int LessonID) {
        this.lessonID = LessonID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String Note) {
        this.note = Note;
    }

    public int getPassScore() {
        return passScore;
    }

    public void setPassScore(int PassScore) {
        this.passScore = PassScore;
    }

    public int getExamTimeInMinute() {
        return examTimeInMinute;
    }

    public void setExamTimeInMinute(int examTimeInMinute) {
        this.examTimeInMinute = examTimeInMinute;
    }

    
    
    public void setId(int id) {
        this.lessonID = id;
    }
    
    public int getId() {
        return this.lessonID;
    }

    public TestType getTestype() {
        return testype;
    }

    public void setTestype(TestType testype) {
        this.testype = testype;
    }

    

    @Override
    public String toString() {
        return "QuizLesson{" + "name=" + name + ", lessonID=" + lessonID + ", note=" + note + ", passScore=" + passScore + ", examTimeInMinute=" + examTimeInMinute + ", quizID=" + quizID + ", subjectID=" + subjectID + ", level=" + level + ", totalQues=" + totalQues + ", type=" + type + ", subjectName=" + subjectName + ", questions=" + questions + '}';
    }
    
    
}
