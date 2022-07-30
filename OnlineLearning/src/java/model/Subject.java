package model;

import java.util.ArrayList;

public class Subject {

    private int subjectId;
    private String name;
    private SubjectCategory categoryID;
    private int order;
    private String type;
    private boolean status;
    private ArrayList<SubjectCategory> listCategory;

    public ArrayList<SubjectCategory> getListCategory() {
        return listCategory;
    }

    public Subject(int subjectId, String name, SubjectCategory categoryID, int order, String type, boolean status) {
        this.subjectId = subjectId;
        this.name = name;
        this.categoryID = categoryID;
        this.order = order;
        this.type = type;
        this.status = status;
    }

    
    public void setListCategory(ArrayList<SubjectCategory> listCategory) {
        this.listCategory = listCategory;
    }

    public Subject() {
    }

    public Subject(int subjectId, String name, SubjectCategory categoryID, int order, String type) {
        this.subjectId = subjectId;
        this.name = name;
        this.categoryID = categoryID;
        this.order = order;
        this.type = type;
    }
    
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectCategory getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(SubjectCategory categoryID) {
        this.categoryID = categoryID;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Subject{" + "subjectId=" + subjectId + ", name=" + name + ", categoryID=" + categoryID + ", order=" + order + ", type=" + type + ", status=" + status + ", listCategory=" + listCategory + '}';
    }

    
}
