package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CompletedQuestion;
import model.Question;
import model.QuestionLevel;

public class CompletedQuestionDAO extends DBContext {

    public ArrayList<CompletedQuestion> listAllQuizReview(int AccountID, int lessonID) {
        ArrayList<CompletedQuestion> list = new ArrayList<>();
        try {
            String sql = "select c.*, q.* from [CompletedQuestion] c, Question q where AccountID = ? "
                    + "and q.QuestionID = c.QuestionID and q.LessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, AccountID);
            stm.setInt(2, lessonID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                //create Question add to CompletedQuestion
                Question question = new Question();

                question.setId(rs.getInt("QuestionID"));
                question.setText(rs.getString("QuestionText"));
                question.setImageUrl(rs.getString("QuestionImageUrl"));
                question.setLessonId(rs.getInt("LessonID"));

                QuestionLevel questionLevel = new QuestionLevel(rs.getInt("QuestionLevelID"));

                question.setLevel(questionLevel);
                question.setOrder(rs.getInt("Order"));
                question.setActive(rs.getBoolean("Status"));

                CompletedQuestion compeletedQuestion = new CompletedQuestion();
                compeletedQuestion.setAccountID(rs.getInt("AccountID"));
                compeletedQuestion.setQuestionID(question);
                compeletedQuestion.setSelectedAnswerID(rs.getInt("SelectedAnswerID"));
                compeletedQuestion.setStatus(rs.getInt("Status"));

                list.add(compeletedQuestion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompletedQuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<CompletedQuestion> getSelectedAnswerByQuestion(int AccountID, int questionid) {
        ArrayList<CompletedQuestion> list = new ArrayList<>();
        try {
            String sql = "  select c.SelectedAnswerID, q.QuestionID from [CompletedQuestion] c, Question q "
                    + "where AccountID = ? and q.QuestionID = c.QuestionID and q.QuestionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, AccountID);
            stm.setInt(2, questionid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                //create Question add to CompletedQuestion
                CompletedQuestion compeletedQuestion = new CompletedQuestion();

                Question question = new Question();
                question.setId(rs.getInt("QuestionID"));

                compeletedQuestion.setQuestionID(question);
                compeletedQuestion.setSelectedAnswerID(rs.getInt("SelectedAnswerID"));
                list.add(compeletedQuestion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompletedQuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<CompletedQuestion> listSizeQuizReview(int AccountID, int lessonID) {
        ArrayList<CompletedQuestion> list = new ArrayList<>();
        try {
            String sql = "  select c.QuestionID from [CompletedQuestion] c, Question q where AccountID = ?  "
                    + "and q.QuestionID = c.QuestionID and q.LessonID = ? group by c.QuestionID";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, AccountID);
            stm.setInt(2, lessonID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CompletedQuestion compeletedQuestion = new CompletedQuestion();

                Question question = new Question();
                question.setId(rs.getInt("QuestionID"));

                compeletedQuestion.setQuestionID(question);
                list.add(compeletedQuestion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompletedQuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<CompletedQuestion> listSelectedByQuiz(int AccountID, int lessonID, int questionID) {
        ArrayList<CompletedQuestion> list = new ArrayList<>();
        try {
            String sql = "select c.QuestionID, c.SelectedAnswerID from [CompletedQuestion] c, "
                    + "Question q where AccountID = ? and q.QuestionID = c.QuestionID and q.LessonID = ? and q.QuestionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, AccountID);
            stm.setInt(2, lessonID);
            stm.setInt(3, questionID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CompletedQuestion compeletedQuestion = new CompletedQuestion();

                Question question = new Question();
                question.setId(rs.getInt("QuestionID"));

                compeletedQuestion.setQuestionID(question);
                compeletedQuestion.setSelectedAnswerID(rs.getInt("SelectedAnswerID"));
                list.add(compeletedQuestion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompletedQuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
