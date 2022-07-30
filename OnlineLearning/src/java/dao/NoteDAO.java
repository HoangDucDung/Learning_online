package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Lesson;
import model.Note;

public class NoteDAO extends DBContext {

    public String insertNote(int accountID, int lessonID, String noteDescription, Timestamp createdTime, String noteTimeInVideo) {
        String isSuccess = "true";
        try {
            String sql = "INSERT INTO [Note]([LessonID],[NoteDescription],[CreatedTime], [NoteTimeInVideo], [AccountID])\n"
                    + "VALUES(?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, lessonID);
            ps.setString(2, noteDescription);
            ps.setTimestamp(3, createdTime);
            ps.setString(4, noteTimeInVideo);
            ps.setInt(5, accountID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            isSuccess = ex.getMessage();
        }
        return isSuccess;
    }

    public ArrayList<Note> getNotesByLessonID(int accountID, int lessonID) {
        ArrayList<Note> listNote = new ArrayList<>();
        try {
            String sql = "select n.NoteID, n.NoteDescription, n.CreatedTime, n.NoteTimeInVideo, "
                    + "n.AccountID, l.* \n"
                    + "from note n join lesson l \n"
                    + "on l.LessonID = n.LessonID \n"
                    + "where l.LessonID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, lessonID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccountID(accountID);
                
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

                Note note = new Note();
                note.setNoteID(rs.getInt("NoteID"));
                note.setLessonID(lesson);
                note.setNoteDescription(rs.getString("NoteDescription"));
                note.setCreatedTime(rs.getTimestamp("CreatedTime"));
                note.setNoteTimeInVideo(rs.getString("NoteTimeInVideo"));
                note.setAccountID(account);
                
                listNote.add(note);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNote;
    }

    public void updateDescNote(int noteID, String desc) {
        try {
            String sql = "UPDATE [Note]\n"
                    + "   SET [NoteDescription] = ?\n"
                    + " WHERE NoteID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, desc);
            ps.setInt(2, noteID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteNoteById(int noteID) {
        try {
            String sql = "DELETE FROM [Note]\n"
                    + "      WHERE noteid = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, noteID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
