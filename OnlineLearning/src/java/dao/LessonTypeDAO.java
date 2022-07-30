package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LessonType;

public class LessonTypeDAO extends DBContext {

    public ArrayList<LessonType> getAllLessonTypes() {
        ArrayList<LessonType> lessonTypes = new ArrayList<>();
        try {
            String sql = "SELECT [LessonTypeID]\n"
                    + "      ,[Name], [Order], Status\n"
                    + "  FROM [LessonType]";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                LessonType lessonType = new LessonType();
                lessonType.setLessonTypeID(rs.getInt("LessonTypeID"));
                lessonType.setName(rs.getString("Name"));
                lessonType.setOrder(rs.getInt("Order"));
                if (rs.getInt("Status") == 1) {
                    lessonType.setStatus(true);
                } else {
                    lessonType.setStatus(false);
                }
                lessonTypes.add(lessonType);

            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessonTypes;
    }

    public void insertLessonType(LessonType s) {
        String sql = "INSERT INTO [LessonType]\n"
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
            Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void updateLessonType(LessonType s) {
        String sql = "UPDATE [LessonType]\n"
                + "   SET [Name] = ?\n"
                + "      ,[Order] = ?\n"
                + "      ,[Status] = ?\n"
                + "      ,[type] = ?\n"
                + " WHERE [LessonTypeID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(5, s.getLessonTypeID());
            stm.setString(1, s.getName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void deleteLessonType(int id) {
        String sql = "DELETE LessonType"
                + " WHERE LessonTypeID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public LessonType getLessonTypeLast() {
        try {
            String sql = "SELECT * FROM LessonType WHERE LessonTypeID = (SELECT MAX(LessonTypeID) FROM LessonType)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                LessonType lessonType = new LessonType();
                lessonType.setLessonTypeID(rs.getInt("LessonTypeID"));
                lessonType.setOrder(rs.getInt("Order"));
                lessonType.setStatus(rs.getBoolean("Status"));
                lessonType.setName(rs.getString("Name"));
                lessonType.setType(rs.getString("type"));

                return lessonType;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonTypeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
