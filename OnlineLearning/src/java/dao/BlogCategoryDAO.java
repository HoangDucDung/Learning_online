package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BlogCategory;

public class BlogCategoryDAO extends DBContext {

    public ArrayList<BlogCategory> getAllBlogCategory() {
        String sql = "select * from [BlogCategory]";
        ArrayList<BlogCategory> bclist = new ArrayList<>();
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                BlogCategory bc = new BlogCategory(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                bclist.add(bc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bclist;
    }

    public ArrayList<BlogCategory> getCategoryByID(int blogId) {
        ArrayList<BlogCategory> blogCategorys = new ArrayList<>();
        try {
            String sql = "Select BlogCategory.BlogCategoryID, BlogCategory.Description, BlogCategory.IconUrl,\n"
                    + "BlogCategory.Name, BlogCategory.ThumbnailUrl,BlogCategoryBlog.BlogID\n"
                    + "FROM BlogCategory INNER JOIN BlogCategoryBlog ON BlogCategory.BlogCategoryID = BlogCategoryBlog.BlogCategoryID\n"
                    + "WHERE BlogCategoryBlog.BlogID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, blogId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                BlogCategory blogCategory = new BlogCategory();
                blogCategory.setName(rs.getString("Name"));
                blogCategory.setBlogCategoryID(rs.getInt("BlogCategoryID"));
                blogCategory.setDescription(rs.getString("Description"));
                blogCategory.setIconUrl(rs.getString("IconUrl"));
                blogCategory.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                blogCategorys.add(blogCategory);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogCategorys;
    }

    public ArrayList<BlogCategory> getAllBlogCategory_DistinctName() {
        String sql = "select distinct name, BlogCategoryID from [BlogCategory]";
        ArrayList<BlogCategory> bclist = new ArrayList<>();
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                BlogCategory bc = new BlogCategory();
                bc.setName(rs.getString("Name"));
                bc.setBlogCategoryID(rs.getInt("BlogCategoryID"));
                bclist.add(bc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bclist;
    }
}
