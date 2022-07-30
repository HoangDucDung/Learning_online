package model;

import java.util.ArrayList;

public class BlogCategory {

    private int blogCategoryID;
    private String name;
    private String description;
    private String iconUrl;
    private String thumbnailUrl;
    private int order;
    private boolean status;
    private String type;
    private ArrayList<BlogSubCategory> blogSubCategories;

    public ArrayList<BlogSubCategory> getBlogSubCategories() {
        return blogSubCategories;
    }

    public void setBlogSubCategories(ArrayList<BlogSubCategory> blogSubCategories) {
        this.blogSubCategories = blogSubCategories;
    }
      
    public BlogCategory() {
    }

    public BlogCategory(int blogCategoryID, String name, String description, String iconUrl, String thumbnailUrl, int order, boolean status, String type) {
        this.blogCategoryID = blogCategoryID;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.order = order;
        this.status = status;
        this.type = type;
    }

    public BlogCategory(int blogCategoryID, String name, String description, String iconUrl, String thumbnailUrl) {
        this.blogCategoryID = blogCategoryID;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getBlogCategoryID() {
        return blogCategoryID;
    }

    public void setBlogCategoryID(int blogCategoryID) {
        this.blogCategoryID = blogCategoryID;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
