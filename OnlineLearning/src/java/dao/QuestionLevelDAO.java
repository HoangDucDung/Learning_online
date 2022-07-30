package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import model.QuestionLevel;
import java.util.ArrayList;
import java.util.logging.Logger;

public class QuestionLevelDAO extends DBContext {

    private static final Logger LOG = Logger.getLogger(QuestionLevelDAO.class.getName());

    static QuestionLevel mapping(QuestionLevel q, ResultSet rs) throws SQLException {
        q.setId(rs.getInt("QuestionLevelID"));
        q.setLevelName(rs.getString("LevelName"));
        return q;
    }

    public List<QuestionLevel> findAll() {
        ArrayList<QuestionLevel> ls = new ArrayList<>();
        String sql = "select * from QuestionLevel";
        try ( Statement stmt = connection.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            LOG.info(stmt.toString());
            while (rs.next()) {
                ls.add(mapping(new QuestionLevel(), rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ls;
    }

    public QuestionLevel findById(int id) {
        String sql = "select * from QuestionLevel where QuestionLevelID = ?";
        QuestionLevel level = null;
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            LOG.info(stmt.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mapping(level, rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return level;
    }
}
