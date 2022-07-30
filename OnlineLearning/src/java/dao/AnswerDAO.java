package dao;

import exception.CrudException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Answer;
import model.Question;

public class AnswerDAO extends DBContext {

    static void mapping(ResultSet rs, Answer a) throws SQLException {
        a.setId(rs.getInt("AnswerID"));
        a.setStatus(rs.getBoolean("Status") ? 1 : 0); // Urgly cast
        a.setText(rs.getString("AnswerText"));
        a.setExplain(rs.getString("Explain"));
        a.setQuestionId(rs.getInt("QuestionID"));
    }

    public void update(Answer a) {
        String sql = "UPDATE Answer SET"
                + "    [AnswerText] = ?,"
                + "    [Explain] = ?,"
                + "    [QuestionID] = ?,"
                + "    [Status] = ? "
                + " WHERE [AnswerID] = ?";

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setString(1, a.getText());
            s.setString(2, a.getExplain());
            s.setInt(3, a.getQuestionId());
            s.setBoolean(4, a.getStatus() == 1);
            s.setInt(5, a.getId());

            s.executeUpdate();
        } catch (SQLException ex) {
            throw new CrudException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Save an Answer to database and generate the id of Answer
     *
     * @param a an Answer
     * @return id of a new Answer or 0 as a default when can't save Answer to
     * database
     */
    public int save(Answer a) {
        String sql = "INSERT INTO Answer VALUES (?, ?, ?, ?)";
        int id = 0;

        try (PreparedStatement s = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, a.getText());
            s.setString(2, a.getExplain());
            boolean isTrueAnswer = a.getStatus() == 1;
            s.setBoolean(3, isTrueAnswer);
            s.setInt(4, a.getQuestionId());

            s.executeUpdate();

            ResultSet rs = s.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new CrudException(ex.getMessage(), ex.getCause());
        }

        return id;
    }

    public void delete(int id) {
        String sql = "DELETE Answer WHERE AnswerID = ?";

        try (PreparedStatement s = connection.prepareStatement(sql)) {
            s.setInt(1, id);
            s.executeUpdate();
        } catch (SQLException ex) {
            throw new CrudException(ex.getMessage(), ex.getCause());
        }
    }

    public List<Answer> findByQuestionId(int id) {
        List<Answer> answers = new ArrayList<>();
        String sql = "select * from Answer where QuestionID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Answer a = new Answer();
                mapping(rs, a);
                answers.add(a);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return answers;
    }

    public ArrayList<Answer> listAllAnsByQues(int lessonId, int questionID) {
        ArrayList<Answer> list = new ArrayList<>();
        try {
            String sql = "select a.* from [Question] q, [Answer] a  "
                    + "where q.LessonID = ? and a.QuestionID = ?  and q.QuestionID = a.QuestionID";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonId);
            stm.setInt(2, questionID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setAnswerID(rs.getInt("AnswerID"));
                answer.setAnswerText(rs.getString("AnswerText"));
                answer.setExplain(rs.getString("Explain"));
                answer.setStatus(rs.getInt("Status"));

                Question question = new Question();
                question.setId(rs.getInt("QuestionID"));
                answer.setQuestionID(question);
                list.add(answer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Answer> listquestionbyQuestionID(int lessonId, int questionID) {
        ArrayList<Answer> list = new ArrayList<>();
        try {
            String sql = "select a.* from [Question] q, [Answer] a  "
                    + "where q.LessonID = ? and a.QuestionID = ?  and q.QuestionID = a.QuestionID and a.[Status] = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonId);
            stm.setInt(2, questionID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setAnswerID(rs.getInt("AnswerID"));
                answer.setAnswerText(rs.getString("AnswerText"));
                answer.setExplain(rs.getString("Explain"));
                answer.setStatus(rs.getInt("Status"));

                Question question = new Question();
                question.setId(rs.getInt("QuestionID"));
                answer.setQuestionID(question);
                list.add(answer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnswerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
