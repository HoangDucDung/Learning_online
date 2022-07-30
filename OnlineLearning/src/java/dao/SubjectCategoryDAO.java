package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SubjectCategory;

public class SubjectCategoryDAO extends DBContext {

    public ArrayList<SubjectCategory> getAllSubjectCategory() {
        ArrayList<SubjectCategory> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM SubjectCategory";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SubjectCategory sc = new SubjectCategory();
                sc.setCategoryID(rs.getInt("CategoryID"));
                sc.setName(rs.getString("Name"));

                list.add(sc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void insertSubjectCategory(SubjectCategory s) {
        String sql = "INSERT INTO [SubjectCategory]\n"
                + "           ([Name]\n"
                + "           ,[type]\n"
                + "           ,[Order]\n"
                + "           ,[Status])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, s.getName());
            stm.setString(2, s.getType());
            stm.setInt(3, s.getOrder());
            stm.setBoolean(4, s.isStatus());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void updateSubjectCategory(SubjectCategory s) {
        String sql = "UPDATE [SubjectCategory]\n"
                + "   SET [Name] = ?\n"
                + "      ,[type] = ?\n"
                + "      ,[Order] = ?\n"
                + "      ,[Status] = ?\n"
                + " WHERE [CategoryID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(5, s.getCategoryID());
            stm.setString(1, s.getName());
            stm.setString(2, s.getType());
            stm.setInt(3, s.getOrder());
            stm.setBoolean(4, s.isStatus());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void deleteSubjectCategory(int id) {
        String sql = "DELETE FROM [SubjectCategory]\n"
                + "      WHERE CategoryID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public SubjectCategory getSubjectCategoryLast() {
        try {
            String sql = "SELECT * FROM SubjectCategory WHERE CategoryID = (SELECT MAX(CategoryID) FROM SubjectCategory)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                SubjectCategory SubjectCategory = new SubjectCategory();
                SubjectCategory.setCategoryID(rs.getInt("CategoryID"));
                SubjectCategory.setOrder(rs.getInt("Order"));
                SubjectCategory.setStatus(rs.getBoolean("Status"));
                SubjectCategory.setName(rs.getString("Name"));
                SubjectCategory.setType(rs.getString("type"));

                return SubjectCategory;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
