package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Course {

    private int courseId;
    private String name;
    private String description;
    private Account instructorId;
    private String tinyPictureUrl;
    private String thumbnailUrl;
    private Date createdDate;
    private Date modifiedDate;
    private BigDecimal price;
    private boolean status;

    public Course() {
    }

    public Course(int courseId, String name, String description, Account instructorId, String tinyPictureUrl, String thumbnailUrl, Date createdDate, Date modifiedDate, BigDecimal price, boolean status) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.instructorId = instructorId;
        this.tinyPictureUrl = tinyPictureUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.price = price;
        this.status = status;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public Account getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Account instructorId) {
        this.instructorId = instructorId;
    }

    public String getTinyPictureUrl() {
        return tinyPictureUrl;
    }

    public void setTinyPictureUrl(String tinyPictureUrl) {
        this.tinyPictureUrl = tinyPictureUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}