package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.QuizLesson;

public class QuizDAO extends DBContext {

    public QuizLesson getQuizLessonByID(int id) {
        try {
            String sql = "select LessonID, note, PassScore from QuizLesson \n"
                    + "where LessonID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                QuizLesson quizLesson = new QuizLesson();
                quizLesson.setLessonID(rs.getInt("LessonID"));
                quizLesson.setNote(rs.getString("note"));
                quizLesson.setPassScore(rs.getInt("PassScore"));
                return quizLesson;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(BlogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<QuizLesson> getAllQuizLessons() {
        ArrayList<QuizLesson> quizLessons = new ArrayList<>();
        try {
            String sql = "select * from QuizLesson";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                QuizLesson QuizLesson = new QuizLesson();
                QuestionDAO QuestionDAO = new QuestionDAO();
                SubjectDAO SubjectDAO = new SubjectDAO();
                QuizLesson.setQuizID(rs.getInt("QuizID"));
                QuizLesson.setName(rs.getString("Name"));
                QuizLesson.setLessonID(rs.getInt("LessonID"));
                QuizLesson.setNote(rs.getString("Note"));
                int totalQues = new QuestionDAO().countbyID(QuizLesson.getLessonID());
                QuizLesson.setTotalQues(totalQues);
                QuizLesson.setPassScore(rs.getInt("PassScore"));
                QuizLesson.setExamTimeInMinute(rs.getInt("QuizTimeInMinute"));
                QuizLesson.setSubjectID(rs.getInt("SubjectID"));
                if(SubjectDAO.getSubjectNamebyID(QuizLesson.getSubjectID()) == null){
                    QuizLesson.setSubjectName(null);
                }else{
                QuizLesson.setSubjectName(SubjectDAO.getSubjectNamebyID(QuizLesson.getSubjectID()).getName());
                }
                QuizLesson.setLevel(rs.getString("Level"));
                QuizLesson.setType(rs.getString("Type"));
                QuizLesson.setQuestions(QuestionDAO.total(QuizLesson.getLessonID()));
                System.out.println("rterh" + QuizLesson);
                quizLessons.add(QuizLesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizLessons;
    }

    public ArrayList<QuizLesson> getAllQuizType() {
        ArrayList<QuizLesson> quizLessons = new ArrayList<>();
        try {
            String sql = "select distinct type from QuizLesson";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                QuizLesson quizLesson = new QuizLesson();
                quizLesson.setType(rs.getString("Type"));
                quizLessons.add(quizLesson);

            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizLessons;
    }
    
    public ArrayList<QuizLesson> getAllQuizLevel() {
        ArrayList<QuizLesson> quizLessons = new ArrayList<>();
        try {
            String sql = "select distinct level from QuizLesson";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                QuizLesson quizLesson = new QuizLesson();
                quizLesson.setLevel(rs.getString("level"));
                quizLessons.add(quizLesson);

            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizLessons;
    }

    public int count() {
        try {
            String sql = "SELECT count(*) as Total FROM QuizLesson";

            PreparedStatement stm = connection.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<QuizLesson> searchby_Name(String value) {
        ArrayList<QuizLesson> quizLessons = new ArrayList<>();
        try {
            String sql = "select *\n"
                    + "from QuizLesson where QuizLesson.[Name] = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, value);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuizLesson QuizLesson = new QuizLesson();
                QuestionDAO QuestionDAO = new QuestionDAO();
                SubjectDAO SubjectDAO = new SubjectDAO();
                QuizLesson.setQuizID(rs.getInt("QuizID"));
                QuizLesson.setName(rs.getString("Name"));
                QuizLesson.setLessonID(rs.getInt("LessonID"));
                QuizLesson.setNote(rs.getString("Note"));
                QuizLesson.setPassScore(rs.getInt("PassScore"));
                QuizLesson.setExamTimeInMinute(rs.getInt("QuizTimeInMinute"));
                QuizLesson.setSubjectID(rs.getInt("SubjectID"));
                QuizLesson.setSubjectName(SubjectDAO.getSubjectNamebyID(QuizLesson.getSubjectID()).getName());
                QuizLesson.setLevel(rs.getString("Level"));
                QuizLesson.setType(rs.getString("Type"));
                QuizLesson.setTotalQues(QuestionDAO.count(QuizLesson.getLessonID()));
                QuizLesson.setQuestions(QuestionDAO.total(QuizLesson.getLessonID()));
                quizLessons.add(QuizLesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizLessons;
    }

    public ArrayList<QuizLesson> searchBy_Filter(int id, String type) {
        ArrayList<QuizLesson> quizLessons = new ArrayList<>();
        try {
            String sql = "select * from QuizLesson ";
            if (id != -1 && !type.equalsIgnoreCase("-1")) {
                sql += "where SubjectID = ? and Type = ?";

            } else if (id != -1) {
                sql += "where SubjectID = ?";

            } else if (id == -1) {
                sql += "where Type = ?";

            }
            PreparedStatement stm = connection.prepareStatement(sql);
            if (id != -1 && !type.equalsIgnoreCase("-1")) {
                stm.setInt(1, id);
                stm.setString(2, type);

            } else if (id != -1) {
                stm.setInt(1, id);
            } else if (id == -1) {
                stm.setString(1, type);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                QuizLesson QuizLesson = new QuizLesson();
                QuestionDAO QuestionDAO = new QuestionDAO();
                SubjectDAO SubjectDAO = new SubjectDAO();
                QuizLesson.setQuizID(rs.getInt("QuizID"));
                QuizLesson.setName(rs.getString("Name"));
                QuizLesson.setLessonID(rs.getInt("LessonID"));
                QuizLesson.setNote(rs.getString("Note"));
                QuizLesson.setPassScore(rs.getInt("PassScore"));
                QuizLesson.setExamTimeInMinute(rs.getInt("QuizTimeInMinute"));
                QuizLesson.setSubjectID(rs.getInt("SubjectID"));
                QuizLesson.setSubjectName(SubjectDAO.getSubjectNamebyID(QuizLesson.getSubjectID()).getName());
                QuizLesson.setLevel(rs.getString("Level"));
                QuizLesson.setType(rs.getString("Type"));
                QuizLesson.setTotalQues(QuestionDAO.count(QuizLesson.getLessonID()));
                QuizLesson.setQuestions(QuestionDAO.total(QuizLesson.getLessonID()));
                quizLessons.add(QuizLesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizLessons;
    }

    public QuizLesson getQuizLessonDetail(int id) {
        try {
            String sql = "select * from QuizLesson\n"
                    + "where QuizLesson.QuizID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                QuizLesson QuizLesson = new QuizLesson();
                QuestionDAO QuestionDAO = new QuestionDAO();
                SubjectDAO SubjectDAO = new SubjectDAO();
                QuizLesson.setQuizID(rs.getInt("QuizID"));
                QuizLesson.setName(rs.getString("Name"));
                QuizLesson.setLessonID(rs.getInt("LessonID"));
                QuizLesson.setTotalQues(rs.getInt("TotalQuestion"));
                QuizLesson.setNote(rs.getString("Note"));
                QuizLesson.setPassScore(rs.getInt("PassScore"));
                QuizLesson.setExamTimeInMinute(rs.getInt("QuizTimeInMinute"));
                QuizLesson.setSubjectID(rs.getInt("SubjectID"));
                if(SubjectDAO.getSubjectNamebyID(QuizLesson.getSubjectID()) == null){
                    QuizLesson.setSubjectName(null);
                }else{
                QuizLesson.setSubjectName(SubjectDAO.getSubjectNamebyID(QuizLesson.getSubjectID()).getName());
                }
                QuizLesson.setLevel(rs.getString("Level"));
                QuizLesson.setType(rs.getString("Type"));
                QuizLesson.setQuestions(QuestionDAO.total(QuizLesson.getLessonID()));
                return QuizLesson;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateQuiz(QuizLesson s) {
        String sql = "UPDATE [QuizLesson]\n"
                + "   SET [LessonID] = ?\n"
                + "      ,[PassScore] = ?\n"
                + "      ,[QuizTimeInMinute] = ?\n"
                + "      ,[Name] = ?\n"
                + "      ,[SubjectID] = ?\n"
                + "      ,[Level] = ?\n"
                + "      ,[TotalQuestion] = ?\n"
                + "      ,[Type] = ?\n"
                + " WHERE [QuizID] = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(9, s.getQuizID());
            stm.setInt(1, s.getLessonID());
            stm.setInt(2, s.getPassScore());
            stm.setInt(3, s.getExamTimeInMinute());
            stm.setString(4, s.getName());
            stm.setInt(5, s.getSubjectID());
            stm.setString(6, s.getLevel());
            stm.setInt(7, s.getTotalQues());
            stm.setString(8, s.getType());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void insertQuizLesson(QuizLesson s) {
        String sql = "INSERT INTO [QuizLesson]\n"
                + "           ([LessonID]\n"
                + "           ,[PassScore]\n"
                + "           ,[QuizTimeInMinute]\n"
                + "           ,[Name]\n"
                + "           ,[SubjectID]\n"
                + "           ,[Level]\n"
                + "           ,[TotalQuestion]\n"
                + "           ,[TestTypeID]\n"
                + "           ,[Type])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, s.getLessonID());
            stm.setInt(2, s.getPassScore());
            stm.setInt(3, s.getExamTimeInMinute());
            stm.setString(4, s.getName());
            stm.setInt(5, s.getSubjectID());
            stm.setString(6, s.getLevel());
            stm.setInt(7, s.getTotalQues());
            stm.setInt(8, s.getTestype().getTestTypeID());
            stm.setString(9, s.getType());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public QuizLesson getQuizLessonLast() {
        try {
            String sql = "SELECT * FROM QuizLesson WHERE QuizID = (SELECT MAX(QuizID) FROM QuizLesson)";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                QuizLesson QuizLesson = new QuizLesson();
                QuizLesson.setQuizID(rs.getInt("QuizID"));

                return QuizLesson;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteQuizLesson(int quizID) {
        String sql = "DELETE FROM [QuizLesson]\n"
                + "      WHERE QuizID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, quizID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
