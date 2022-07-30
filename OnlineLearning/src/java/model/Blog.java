package model;

import java.sql.Date;
import java.util.ArrayList;

    public class Blog {

    private int blogID;
    private String title;
    private String description;
    private String content;
    private Date createdDate;
    private Account authorID;
    private boolean display;
    private String thumbnailUrl;
    private int numberOfView;
    private ArrayList<BlogCategory> blogCategories;
    private ArrayList<BlogSubCategory> blogSubCategories;

    public ArrayList<BlogSubCategory> getBlogSubCategories() {
        return blogSubCategories;
    }

    public void setBlogSubCategories(ArrayList<BlogSubCategory> blogSubCategories) {
        this.blogSubCategories = blogSubCategories;
    }

    public Blog() {
    }

    public Blog(int blogID, String title, String description, String content, Date createdDate, Account authorID, boolean display, String thumbnailUrl, int numberOfView, ArrayList<BlogCategory> blogCategories) {
        this.blogID = blogID;
        this.title = title;
        this.description = description;
        this.content = content;
        this.createdDate = createdDate;
        this.authorID = authorID;
        this.display = display;
        this.thumbnailUrl = thumbnailUrl;
        this.numberOfView = numberOfView;
        this.blogCategories = blogCategories;
    }

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Account getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Account authorID) {
        this.authorID = authorID;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getNumberOfView() {
        return numberOfView;
    }

    public void setNumberOfView(int numberOfView) {
        this.numberOfView = numberOfView;
    }

    public ArrayList<BlogCategory> getBlogCategories() {
        return blogCategories;
    }

    public void setBlogCategories(ArrayList<BlogCategory> blogCategories) {
        this.blogCategories = blogCategories;
    }


}
