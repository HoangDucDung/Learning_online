package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.QuizLesson;

public class QuizLessonDAO extends DBContext {

    static void mapping(ResultSet rs, QuizLesson quiz) throws SQLException {
        quiz.setId(rs.getInt("LessonID"));
        quiz.setName(rs.getString("Name"));
        quiz.setNote(rs.getString("Note"));
        quiz.setTotalQues(rs.getInt("TotalQuestion"));
        quiz.setPassScore(rs.getInt("PassScore"));
        quiz.setExamTimeInMinute(rs.getInt("QuizTimeInMinute"));
    }

    public QuizLesson findById(int id) {
        QuizLesson quiz = null;
        String sql = "select * from QuizLesson where LessonID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                quiz = new QuizLesson();
                mapping(rs, quiz);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quiz;
    }
}
