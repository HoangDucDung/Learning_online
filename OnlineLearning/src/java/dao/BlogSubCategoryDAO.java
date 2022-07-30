/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BlogCategory;
import model.BlogSubCategory;

/**
 *
 * @author duc21
 */
public class BlogSubCategoryDAO extends DBContext{
    public ArrayList<BlogSubCategory> getAllBlogSubCategory() {
        ArrayList<BlogSubCategory> BlogSubCategorys = new ArrayList<>();
        try {
            String sql = "SELECT [BlogSubCategoryID]\n" +
                        "      ,[SubCategoryName]\n" +
                        "      ,[BlogCategoryID]\n" +
                        "      ,[Order]\n" +
                        "      ,[Status]\n" +
                        "      ,[type]\n" +
                        "  FROM [BlogSubCategory]";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                BlogSubCategory BlogSubCategory = new BlogSubCategory();
                BlogCategory BlogCategory = new BlogCategory();
                BlogSubCategory.setBlogSubCategoryId(rs.getInt("BlogSubCategoryID"));
                BlogSubCategory.setSubCategoryName(rs.getString("SubCategoryName"));
                BlogSubCategory.setOrder(rs.getInt("Order"));
                if (rs.getInt("Status") == 1) {
                    BlogSubCategory.setStatus(true);
                } else {
                    BlogSubCategory.setStatus(false);
                }
                BlogSubCategory.setType(rs.getString("type"));
                BlogCategory.setBlogCategoryID(rs.getInt("BlogCategoryID"));
                BlogSubCategory.setBlogCategoryId(BlogCategory);
                
                BlogSubCategorys.add(BlogSubCategory);

            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return BlogSubCategorys;
    }

    public void insertBlogSubCategory(BlogSubCategory s) {
        String sql = "INSERT INTO [BlogSubCategory]\n" +
                    "           ([SubCategoryName]\n" +
                    "           ,[BlogCategoryID]\n" +
                    "           ,[Order]\n" +
                    "           ,[Status]\n" +
                    "           ,[type])\n" +
                    "     VALUES\n" +
                    "           (?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?\n" +
                    "           ,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, s.getSubCategoryName());
            stm.setInt(2, s.getBlogCategoryId().getBlogCategoryID());
            stm.setInt(3, s.getOrder());
            stm.setBoolean(4, s.isStatus());
            stm.setString(5, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void updateBlogSubCategory(BlogSubCategory s) {
        String sql = "UPDATE [BlogSubCategory]\n" +
                    "   SET [SubCategoryName] = ?\n" +
                    "      ,[Order] = ?\n" +
                    "      ,[Status] = ?\n" +
                    "      ,[type] = ?\n" +
                    " WHERE [BlogSubCategoryID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(5, s.getBlogSubCategoryId());
            stm.setString(1, s.getSubCategoryName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void deleteBlogSubCategory(int id) {
        String sql = "DELETE FROM [BlogSubCategory]\n" +
                    "      WHERE [BlogSubCategoryID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public BlogSubCategory getBlogSubCategoryLast() {
        try {
            String sql = "SELECT * FROM BlogSubCategory WHERE BlogSubCategoryID = (SELECT MAX(BlogSubCategoryID) FROM BlogSubCategory)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                BlogSubCategory BlogSubCategory = new BlogSubCategory();
                BlogSubCategory.setBlogSubCategoryId(rs.getInt("BlogSubCategoryID"));
                BlogCategory BlogCategory = new BlogCategory();
                BlogCategory.setBlogCategoryID(rs.getInt("BlogCategoryID"));
                BlogSubCategory.setBlogCategoryId(BlogCategory);
                BlogSubCategory.setOrder(rs.getInt("Order"));
                BlogSubCategory.setStatus(rs.getBoolean("Status"));
                BlogSubCategory.setSubCategoryName(rs.getString("SubCategoryName"));
                BlogSubCategory.setType(rs.getString("type"));

                return BlogSubCategory;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int getSuperBlogCategory(int settingID) {
        int superid = 0;
        try {
            String sql = "select BlogSubCategory.BlogCategoryID\n" +
                "from Setting join BlogSubCategory on Setting.id = BlogSubCategory.[BlogSubCategoryID]\n" +
                "where SettingID = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, settingID);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                superid = rs.getInt("BlogCategoryID");

            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogSubCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return superid;
    }
}
