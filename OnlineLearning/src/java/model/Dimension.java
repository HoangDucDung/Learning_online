package model;

public class Dimension {

    private int dimensionID;
    private int QuestionID;
    private String name;
    private String description;
    private DimensionType typeID;
    private Course courseID;

    public Dimension() {
    }

    public Dimension(int dimensionID, int QuestionID, String name, String description, DimensionType typeID, Course courseID) {
        this.dimensionID = dimensionID;
        this.QuestionID = QuestionID;
        this.name = name;
        this.description = description;
        this.typeID = typeID;
        this.courseID = courseID;
    }

    

    public int getDimensionID() {
        return dimensionID;
    }

    public void setDimensionID(int dimensionID) {
        this.dimensionID = dimensionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DimensionType getTypeID() {
        return typeID;
    }

    public void setTypeID(DimensionType typeID) {
        this.typeID = typeID;
    }

    public Course getCourseID() {
        return courseID;
    }

    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }
    
    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int QuestionID) {
        this.QuestionID = QuestionID;
    }

    
}
