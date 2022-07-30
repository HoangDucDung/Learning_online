package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Lesson;
import model.LessonBeingLearned;

public class LessonBeingLearnedDAO extends DBContext {

    public void insertLessonBeingLearned(int accountID, int lessonID, int timeContinue) {
        try {
            String sql = "INSERT INTO [LessonBeingLearned]\n"
                    + "([AccountID]\n"
                    + ",[LessonID]\n"
                    + ",[TimeContinue])\n"
                    + "VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountID);
            ps.setInt(2, lessonID);
            ps.setInt(3, timeContinue);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonBeingLearnedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LessonBeingLearned getLessonBeingLearned(int accountID, int lessonID) {
        try {
            String sql = "select * from LessonBeingLearned "
                    + "where accountid = ? and lessonid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountID);
            ps.setInt(2, lessonID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LessonBeingLearned lessonBeingLearned = new LessonBeingLearned();

                Lesson lesson = new Lesson();
                lesson.setLessonID(lessonID);

                Account account = new Account();
                account.setAccountID(accountID);

                lessonBeingLearned.setAccountID(account);
                lessonBeingLearned.setLessonID(lesson);
                lessonBeingLearned.setTimeContinue(rs.getInt("timeContinue"));

                return lessonBeingLearned;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonBeingLearnedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateLessonBeingLearned(int accountID, int lessonID, int timeContinue) {
        try {
            String sql = "UPDATE [LessonBeingLearned]\n"
                    + "   SET [TimeContinue] = ?\n"
                    + " WHERE accountID = ? and lessonID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, timeContinue);
            ps.setInt(2, accountID);
            ps.setInt(3, lessonID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonBeingLearnedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteLessonBeing(int accountID, int lessonID) {
        try {
            String sql = "DELETE FROM [LessonBeingLearned]\n"
                    + "WHERE accountid = ? and lessonid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accountID);
            ps.setInt(2, lessonID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonBeingLearnedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
