package model;

public class BlogSubCategory {
    private int blogSubCategoryId;
    private String subCategoryName;
    private BlogCategory blogCategoryId;
    private int order;
    private boolean status;
    private String type;

    public BlogSubCategory() {
    }

    public BlogSubCategory(int blogSubCategoryId, String subCategoryName, BlogCategory blogCategoryId) {
        this.blogSubCategoryId = blogSubCategoryId;
        this.subCategoryName = subCategoryName;
        this.blogCategoryId = blogCategoryId;
    }

    public BlogSubCategory(int blogSubCategoryId, String subCategoryName, BlogCategory blogCategoryId, int order, boolean status, String type) {
        this.blogSubCategoryId = blogSubCategoryId;
        this.subCategoryName = subCategoryName;
        this.blogCategoryId = blogCategoryId;
        this.order = order;
        this.status = status;
        this.type = type;
    }
    
    
    public int getBlogSubCategoryId() {
        return blogSubCategoryId;
    }

    public void setBlogSubCategoryId(int blogSubCategoryId) {
        this.blogSubCategoryId = blogSubCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public BlogCategory getBlogCategoryId() {
        return blogCategoryId;
    }

    public void setBlogCategoryId(BlogCategory blogCategoryId) {
        this.blogCategoryId = blogCategoryId;
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
