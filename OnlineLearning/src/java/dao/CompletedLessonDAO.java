package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.CompletedLesson;
import model.Gender;
import model.Lesson;

public class CompletedLessonDAO extends DBContext {

    public CompletedLesson CompletedLesson(int lessonId) {
        try {
            String sql = "select * from CompletedLesson where LessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lessonId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                CompletedLesson CompletedLesson = new CompletedLesson();
                Account account=new Account();
                account.setAccountID(rs.getInt("AccountID"));
                CompletedLesson.setAccountID(account);
                Lesson lesson=new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                CompletedLesson.setLessonID(lesson);
                CompletedLesson.setScore(rs.getInt("Score"));
                CompletedLesson.setStatus(rs.getBoolean("Status"));
                CompletedLesson.setStartTime(rs.getTimestamp("StartTime"));
                CompletedLesson.setEndTime(rs.getTimestamp("EndTime"));

                return CompletedLesson;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuestionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<CompletedLesson> getListCompletedLesson(int accountID, int courseID) {
        ArrayList<CompletedLesson> list = new ArrayList<>();
        try {
            String sql = "select a.*, l.*, cl.Score, cl.[Status]\n"
                    + "from CompletedLesson cl join Lesson l\n"
                    + "on l.LessonID = cl.LessonID join Account a\n"
                    + "on a.AccountID = cl.AccountID \n"
                    + "where cl.[Status] = 1 and a.AccountID = ? \n"
                    + "AND l.CourseID = ? AND l.LessonTypeID = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, accountID);
            stm.setInt(2, courseID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(rs.getInt("AccountID"));
                account.setFirstName(rs.getString("FirstName"));
                account.setLastName(rs.getString("LastName"));
                account.setEmail(rs.getString("Email"));
                account.setProfilePictureUrl(rs.getString("ProfilePictureUrl"));
                account.setCreatedTime(rs.getTimestamp("CreatedTime"));
                account.setModifiedTime(rs.getTimestamp("ModifiedTime"));
                account.setPhone(rs.getString("Phone"));
                account.setAddress(rs.getString("Address"));
                account.setGender(Gender.of(rs.getBoolean("Gender")));
                account.setBalance(rs.getBigDecimal("Balance"));

                Lesson lesson = new Lesson();
                lesson.setLessonID(rs.getInt("LessonID"));
                lesson.setName(rs.getString("Name"));
                lesson.setCreatedTime(rs.getTimestamp("CreatedTime"));
                lesson.setUpdatedTime(rs.getTimestamp("UpdatedTime"));
                lesson.setWideImageUrl(rs.getString("WideImageUrl"));
                lesson.setTinyImageUrl(rs.getString("TinyImageUrl"));
                lesson.setOrder(rs.getInt("Order"));
                lesson.setVideoUrl(rs.getString("VideoUrl"));
                lesson.setStatus(rs.getBoolean("Status"));

                CompletedLesson completedLesson = new CompletedLesson();
                completedLesson.setAccountID(account);
                completedLesson.setLessonID(lesson);
                completedLesson.setScore(rs.getInt("Score"));
                completedLesson.setStatus(rs.getBoolean("Status"));

                list.add(completedLesson);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CompletedLessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void updateScore(CompletedLesson s) {
        String sql = "UPDATE [CompletedLesson]\n"
                + "   SET \n"
                + "      [Score] = ?\n"
                + " WHERE AccountID = ? and LessonID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(2, s.getAccountID().getAccountID());
            stm.setInt(3, s.getLessonID().getLessonID());
            stm.setInt(1, s.getScore());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CompletedLessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CompletedLessonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CompletedLessonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
