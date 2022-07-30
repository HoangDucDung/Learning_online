package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import model.Question;
import model.QuestionLevel;

public class QuestionDAO extends DBContext {

    private static final Logger LOG = Logger.getLogger(QuestionDAO.class.getName());

    static Question mapping(Question q, ResultSet rs) throws SQLException {
        q.setId(rs.getInt("QuestionID"));
        q.setText(rs.getString("QuestionText"));
        q.setImageUrl(rs.getString("QuestionImageUrl"));
        q.setLessonId(rs.getInt("LessonID"));
        q.setOrder(rs.getInt("Order"));
        q.setActive(rs.getBoolean("Status"));
        return q;
    }

    public List<Question> findAll() {
        List<Question> ls = new ArrayList<>();
        String sql = "SELECT q.QuestionID, q.QuestionText, q.QuestionImageUrl, q.LessonID, q.QuestionLevelID, q.[Order], "
                + "q.Status, l.LevelName FROM Question AS q LEFT JOIN QuestionLevel AS l "
                + "ON q.QuestionLevelID = l.QuestionLevelID";

        try ( Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            LOG.info(stmt.toString());
            while (rs.next()) {
                QuestionLevel level = QuestionLevelDAO.mapping(new QuestionLevel(), rs);
                Question question = mapping(new Question(), rs);
                question.setLevel(level);
                ls.add(question);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ls;
    }

    public List<Question> findAll(Comparator<Question> sorter) {
        List<Question> ls = findAll();
        ls.sort(sorter);
        return ls;
    }

    public Question findById(int id) {
        String sql = "SELECT q.QuestionID, q.QuestionText, q.QuestionImageUrl, q.LessonID, q.QuestionLevelID, q.[Order], "
                + "q.Status, l.LevelName FROM Question AS q LEFT JOIN QuestionLevel AS l "
                + "ON q.QuestionLevelID = l.QuestionLevelID WHERE QuestionID = ?";
        Question question = null;
        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            LOG.info(stmt.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                QuestionLevel level = QuestionLevelDAO.mapping(new QuestionLevel(), rs);
                question = mapping(new Question(), rs);
                question.setLevel(level);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return question;
    }
}
