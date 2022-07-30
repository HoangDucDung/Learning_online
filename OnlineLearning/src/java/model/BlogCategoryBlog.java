package model;

public class BlogCategoryBlog {
    private Blog blogID;
    private BlogCategory blogCategoryID;

    public BlogCategoryBlog() {
    }

    public BlogCategoryBlog(Blog BlogID, BlogCategory BlogCategoryID) {
        this.blogID = BlogID;
        this.blogCategoryID = BlogCategoryID;
    }

    public Blog getBlogID() {
        return blogID;
    }

    public void setBlogID(Blog BlogID) {
        this.blogID = BlogID;
    }

    public BlogCategory getBlogCategoryID() {
        return blogCategoryID;
    }

    public void setBlogCategoryID(BlogCategory BlogCategoryID) {
        this.blogCategoryID = BlogCategoryID;
    }
    
}
