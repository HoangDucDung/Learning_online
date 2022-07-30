package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Blog;
import model.BlogCategory;
import model.BlogCategoryBlog;

public class BlogCategoryBlogDAO extends DBContext {

    public void updateBlogCategoryBlog(BlogCategoryBlog blogCategoryBlog) {
        try {
            String sql = "UPDATE [BlogCategoryBlog]\n"
                    + "   SET [BlogCategoryID] = ?\n"
                    + " WHERE BlogID = ?";
            PreparedStatement stm = connection.prepareCall(sql);
            stm.setInt(1, blogCategoryBlog.getBlogCategoryID().getBlogCategoryID());
            stm.setInt(2, blogCategoryBlog.getBlogID().getBlogID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryBlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertBlogCategoryBlog(BlogCategoryBlog blogCategoryBlog) {
        try {
            String sql = "INSERT INTO [BlogCategoryBlog]([BlogID],[BlogCategoryID])\n"
                    + " VALUES (?,?)";
            PreparedStatement stm = connection.prepareCall(sql);
            stm.setInt(1, blogCategoryBlog.getBlogID().getBlogID());
            stm.setInt(2, blogCategoryBlog.getBlogCategoryID().getBlogCategoryID());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryBlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteByBlogID(int blogId) {
        try {
            String sql = "DELETE FROM [BlogCategoryBlog]\n"
                    + "WHERE BlogID = ?";
            PreparedStatement stm = connection.prepareCall(sql);
            stm.setInt(1, blogId);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryBlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void deleteSubByBlogID(int blogId) {
        try {
            String sql = "DELETE FROM [BlogSubCategoryBlog]\n"
                    + "WHERE BlogID = ?";
            PreparedStatement stm = connection.prepareCall(sql);
            stm.setInt(1, blogId);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryBlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertBlogSubCategoryBlog(int blogID, int subCategoryID) {
        try {
            String sql = "INSERT INTO [BlogSubCategoryBlog]([BlogID],[BlogSubCategoryID])\n"
                    + " VALUES (?,?)";
            PreparedStatement stm = connection.prepareCall(sql);
            stm.setInt(1, blogID);
            stm.setInt(2, subCategoryID);

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryBlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
