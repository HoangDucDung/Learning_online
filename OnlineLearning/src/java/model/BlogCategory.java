package model;

public class BlogCategory{
    private int blogCategoryID;
    private String name;
    private String description;
    private String iconUrl;
    private String thumbnailUrl;

    public BlogCategory() {
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
}
