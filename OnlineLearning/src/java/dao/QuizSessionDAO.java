package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import model.Account;
import model.QuizLesson;
import model.QuizSession;

public class QuizSessionDAO extends DBContext {

    public QuizSession find(Account account , QuizLesson quiz) {
        return find(account.getId(), quiz.getId());
    }
    
    public QuizSession find(int accountId, int quizId) {
        QuizSession session = null;
        String sql = "select top 1 * from QuizSession where AccountID = ? and QuizLessonID = ? order by SessionID";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            stmt.setInt(2, quizId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                session = new QuizSession(rs.getInt("SessionID"),
                        rs.getInt("AccountID"),
                        rs.getInt("QuizLessonID"),
                        rs.getTimestamp("StartTime").toLocalDateTime(),
                        rs.getTimestamp("ExpiredTime").toLocalDateTime()
                );
            }
            return session;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void insertOrUpdate(Account account, QuizLesson quiz,
            LocalDateTime startTime, LocalDateTime expiredTime) throws SQLException {
        String sql = "select top 1 * from QuizSession where AccountID = ? and QuizLessonID = ? order by SessionID";
        try (PreparedStatement stmt
                = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setInt(1, account.getId());
            stmt.setInt(2, quiz.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rs.updateTimestamp("StartTime", java.sql.Timestamp.valueOf(startTime));
                rs.updateTimestamp("ExpiredTime", java.sql.Timestamp.valueOf(expiredTime));
                rs.updateRow();
            } else {
                rs.moveToInsertRow();
                rs.updateInt("AccountID", account.getId());
                rs.updateInt("QuizLessonID", quiz.getId());
                rs.updateTimestamp("StartTime", java.sql.Timestamp.valueOf(startTime));
                rs.updateTimestamp("ExpiredTime", java.sql.Timestamp.valueOf(expiredTime));
                rs.insertRow();
            }
            rs.close();
        }
    }

    /**
     * Returns Map has StartTime and EndTime property
     *
     * @param account
     * @param quiz
     * @return
     */
    public Map<String, LocalDateTime> getTimeDoQuiz(Account account, QuizLesson quiz) {
        String sql = "select StartTime, EndTime from CompletedLesson where AccountID = ? and LessonID = ?";
        Map<String, LocalDateTime> time = new HashMap<>();
        time.put("StartTime", null);
        time.put("EndTime", null);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, account.getId());
            stmt.setInt(2, quiz.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp t1 = rs.getTimestamp("StartTime");
                Timestamp t2 = rs.getTimestamp("EndTime");

                if (t1 != null) {
                    time.put("StartTime", t1.toLocalDateTime());
                }

                if (t2 != null) {
                    time.put("EndTime", t2.toLocalDateTime());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return time;
    }

    public void startQuizTime(Account account, QuizLesson quiz) throws SQLException {

        String sql = "select * from CompletedLesson where AccountID = ? and LessonID = ?";

        ResultSet rs = null;
        try (PreparedStatement stmt
                = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setInt(1, account.getId());
            stmt.setInt(2, quiz.getId());
            rs = stmt.executeQuery();
            LocalDateTime start = LocalDateTime.now();
            if (rs.next()) {
                rs.updateInt("Score", 0);
                rs.updateBoolean("Status", false);
                rs.updateTimestamp("StartTime", Timestamp.valueOf(start));
                rs.updateTimestamp("EndTime", null);
                rs.updateRow();
            } else {
                rs.moveToInsertRow();
                rs.updateInt("Score", 0);
                rs.updateInt("AccountID", account.getId());
                rs.updateInt("LessonID", quiz.getId());
                rs.updateBoolean("Status", false);
                rs.updateTimestamp("StartTime", Timestamp.valueOf(start));
                rs.updateTimestamp("EndTime", null);
                rs.insertRow();
            }

            insertOrUpdate(account, quiz, start, start.plusMinutes(quiz.getExamTimeInMinute()));
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }
    
    public void finishQuiz(int accountId, int quizId, LocalDateTime endTime) throws SQLException {
        String sql = "UPDATE dbo.CompletedLesson SET EndTime = ? WHERE AccountID = ? AND LessonID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(endTime));
            stmt.setInt(2, accountId);
            stmt.setInt(3, quizId);
            stmt.executeUpdate();
        }
    }
    
    
//    public static void main(String[] args) throws SQLException {
//        QuizSessionDAO dao = new QuizSessionDAO();
//        Account a = new Account();
//        QuizLesson q = new QuizLesson();
//        a.setAccountID(1);
//        q.setId(21);
//
//        dao.startQuizTime(a, q);
//    }
}
