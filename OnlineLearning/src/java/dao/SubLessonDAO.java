package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lesson;
import model.SubLesson;

public class SubLessonDAO extends DBContext{
    public ArrayList<SubLesson> getListSubLessonByCourseID(int courseID) {
        ArrayList<SubLesson> list = new ArrayList<>();
        try {
            String sql = "select * from sublesson where CourseID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, courseID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {   
                
                SubLesson sub = new SubLesson();
                sub.setSubLessonID(rs.getInt("SubLessonID"));
                sub.setName(rs.getString("Name"));
                
                ArrayList<Lesson> listLesson = 
                        new LessonDAO().getListLessonBySubLessonID(rs.getInt("SubLessonID"));
                
                sub.setListLesson(listLesson);
                list.add(sub);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SubLessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<SubLesson> getListSubLesson() {
        ArrayList<SubLesson> list = new ArrayList<>();
        try {
            String sql = "select * from sublesson";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {   
                
                SubLesson sub = new SubLesson();
                sub.setSubLessonID(rs.getInt("SubLessonID"));
                sub.setName(rs.getString("Name"));
                
                ArrayList<Lesson> listLesson = 
                        new LessonDAO().getListLessonBySubLessonID(rs.getInt("SubLessonID"));
                
                sub.setListLesson(listLesson);
                list.add(sub);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SubLessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
