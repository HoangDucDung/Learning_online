package model;

import java.util.ArrayList;

public class SubjectCategory {

    private int categoryID;
    private String name;
    private String type;
    private int order;
    private boolean status;

    public SubjectCategory(int categoryID, String name, String type, int order, boolean status, ArrayList<Subject> listSubject) {
        this.categoryID = categoryID;
        this.name = name;
        this.type = type;
        this.order = order;
        this.status = status;
        this.listSubject = listSubject;
    }
    
    
    
    private ArrayList<Subject> listSubject;

    public ArrayList<Subject> getListSubject() {
        return listSubject;
    }

    public void setListSubject(ArrayList<Subject> listSubject) {
        this.listSubject = listSubject;
    }
    
    public SubjectCategory() {
    }

    public SubjectCategory(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    
}
