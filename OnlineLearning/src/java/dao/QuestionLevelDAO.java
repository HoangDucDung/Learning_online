package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import model.QuestionLevel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionLevelDAO extends DBContext {

    static void mapping(QuestionLevel q, ResultSet rs) throws SQLException {
        q.setId(rs.getInt("QuestionLevelID"));
        q.setLevelName(rs.getString("LevelName"));
    }

    public List<QuestionLevel> findAll() {
        ArrayList<QuestionLevel> ls = new ArrayList<>();
        String sql = "select * from QuestionLevel";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                QuestionLevel ql = new QuestionLevel();
                mapping(ql, rs);
                ls.add(ql);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ls;
    }

    public QuestionLevel findById(int id) {
        String sql = "select * from QuestionLevel where QuestionLevelID = ?";
        QuestionLevel level = null;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                level = new QuestionLevel();
                mapping(level, rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return level;
    }

    public ArrayList<QuestionLevel> getAllQuestionLevels() {
        ArrayList<QuestionLevel> questionLevels = new ArrayList<>();
        try {
            String sql = "SELECT [QuestionLevelID]\n"
                    + "      ,[LevelName], [Order], Status\n"
                    + "  FROM [QuestionLevel]";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs;
            rs = stm.executeQuery();

            while (rs.next()) {
                QuestionLevel questionLevel = new QuestionLevel();
                questionLevel.setId(rs.getInt("QuestionLevelID"));
                questionLevel.setLevelName(rs.getString("LevelName"));
                questionLevel.setOrder(rs.getInt("Order"));
                if (rs.getInt("Status") == 1) {
                    questionLevel.setStatus(true);
                } else {
                    questionLevel.setStatus(false);
                }

                questionLevels.add(questionLevel);

            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questionLevels;
    }

    public void insertQuestionLevel(QuestionLevel s) {
        String sql = "INSERT INTO [QuestionLevel]\n"
                + "           ([LevelName]\n"
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
            stm.setString(1, s.getLevelName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void updateQuestionLevel(QuestionLevel s) {
        String sql = "UPDATE [QuestionLevel]\n"
                + "   SET [LevelName] = ?\n"
                + "      ,[Order] = ?\n"
                + "      ,[Status] = ?\n"
                + "      ,[type] = ?\n"
                + " WHERE [QuestionLevelID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(5, s.getId());
            stm.setString(1, s.getLevelName());
            stm.setInt(2, s.getOrder());
            stm.setBoolean(3, s.isStatus());
            stm.setString(4, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void deleteQuestionLevel(int id) {
        String sql = "DELETE QuestionLevel"
                + " WHERE QuestionLevelID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public QuestionLevel getQuestionLevelLast() {
        try {
            String sql = "SELECT * FROM QuestionLevel WHERE QuestionLevelID = (SELECT MAX(QuestionLevelID) FROM QuestionLevel)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                QuestionLevel questionLevel = new QuestionLevel();
                questionLevel.setId(rs.getInt("QuestionLevelID"));
                questionLevel.setOrder(rs.getInt("Order"));
                questionLevel.setStatus(rs.getBoolean("Status"));
                questionLevel.setLevelName(rs.getString("LevelName"));
                questionLevel.setType(rs.getString("type"));

                return questionLevel;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionLevelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
