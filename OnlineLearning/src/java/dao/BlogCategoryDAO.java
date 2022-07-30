package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BlogCategory;
import model.BlogSubCategory;

public class BlogCategoryDAO extends DBContext {

    public ArrayList<BlogCategory> getAllBlogCategory() {
        String sql = "select * from [BlogCategory]";
        ArrayList<BlogCategory> bclist = new ArrayList<>();
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                BlogCategory bc = new BlogCategory();
                bc.setBlogCategoryID(rs.getInt("BlogCategoryID"));
                bc.setName(rs.getString("Name"));
                bc.setDescription(rs.getString("Description"));
                bc.setIconUrl(rs.getString("IconUrl"));
                bc.setThumbnailUrl(rs.getString("ThumbnailUrl"));
                bc.setOrder(rs.getInt("Order"));
                if (rs.getInt("Status") == 1) {
                    bc.setStatus(true);
                } else {
                    bc.setStatus(false);
                }
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
                
                ArrayList<BlogSubCategory> list = new BlogCategoryDAO().getSubCategoryByID(blogId);               
                blogCategory.setBlogSubCategories(list);
                
                blogCategorys.add(blogCategory);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogCategorys;
    }

    public ArrayList<BlogSubCategory> getSubCategoryByID(int blogId) {
        ArrayList<BlogSubCategory> blogSubCategorys = new ArrayList<>();
        try {
            String sql = "select bsc.* from blog b join BlogSubCategoryBlog bscb\n"
                    + "on b.BlogID = bscb.blogid join BlogSubCategory bsc\n"
                    + "on bsc.BlogSubCategoryID = bscb.BlogSubCategoryID\n"
                    + "where b.blogid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, blogId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                BlogCategory cate = new BlogCategory();
                cate.setBlogCategoryID(rs.getInt("BlogCategoryID"));
                
                BlogSubCategory sub = new BlogSubCategory();
                sub.setBlogSubCategoryId(rs.getInt("BlogSubCategoryID"));
                sub.setSubCategoryName(rs.getString("SubCategoryName"));
                sub.setBlogCategoryId(cate);
                blogSubCategorys.add(sub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blogSubCategorys;
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

    public void insertBlogCategory(BlogCategory s) {
        String sql = "INSERT INTO [BlogCategory]\n"
                + "           ([Name]\n"
                + "           ,[Order]\n"
                + "           ,[Status]\n"
                + "           ,[type])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, s.getName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void updateBlogCategory(BlogCategory s) {
        String sql = "UPDATE [BlogCategory]\n"
                + "   SET [Name] = ?\n"
                + "      ,[Order] = ?\n"
                + "      ,[Status] = ?\n"
                + "      ,[type] = ?\n"
                + " WHERE [BlogCategoryID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(5, s.getBlogCategoryID());
            stm.setString(1, s.getName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void deleteBlogCategory(int id) {
        String sql = "DELETE BlogCategory"
                + " WHERE BlogCategoryID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public BlogCategory getBlogCategoryLast() {
        try {
            String sql = "SELECT * FROM BlogCategory WHERE BlogCategoryID = (SELECT MAX(BlogCategoryID) FROM BlogCategory)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                BlogCategory blogCategory = new BlogCategory();
                blogCategory.setBlogCategoryID(rs.getInt("BlogCategoryID"));
                blogCategory.setOrder(rs.getInt("Order"));
                blogCategory.setStatus(rs.getBoolean("Status"));
                blogCategory.setName(rs.getString("Name"));
                blogCategory.setType(rs.getString("type"));

                return blogCategory;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<BlogSubCategory> getAllBlogSubCategory() {
        ArrayList<BlogSubCategory> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM BlogSubCategory";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                BlogCategory blogCategory = new BlogCategory();
                blogCategory.setBlogCategoryID(rs.getInt("BlogCategoryID"));

                BlogSubCategory subCategory = new BlogSubCategory();
                subCategory.setBlogSubCategoryId(rs.getInt("BlogSubCategoryID"));
                subCategory.setSubCategoryName(rs.getString("SubCategoryName"));
                subCategory.setBlogCategoryId(blogCategory);

                list.add(subCategory);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
