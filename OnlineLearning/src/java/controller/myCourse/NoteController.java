package controller.myCourse;

import dao.NoteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.Note;

@WebServlet(name = "NoteController", urlPatterns = {"/note"})
public class NoteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account)request.getSession().getAttribute("account");
        if (account == null) {
            response.sendRedirect("login");
            return;
        }
        int accountID = account.getAccountID();
        
        int lessonID = Integer.parseInt(request.getParameter("lessonID"));

        ArrayList<Note> listNote = new NoteDAO().getNotesByLessonID(accountID, lessonID);
        for (Note note : listNote) {
            response.getWriter().write("<form>\n"
                    + "                <div class=\"note-item\">\n"
                    + "                    <div class=\"note-item-header\">\n"
                    + "                        <div class=\"note-item-header-left\">\n"
                    + "                            <div class=\"note-time\" onclick='showVideo(this)'>"+ note.getNoteTimeInVideo() +"</div>\n"
                    + "                            <div class=\"note-title-lesson\">" + note.getLessonID().getName() + "</div>\n"
                    + "                        </div>\n"
                    + "                        <div class=\"note-item-header-right\">\n"
                    + "                            <i class=\"fa-solid fa-pen edit-btn\" onclick=\"editNote(this)\"></i>\n"
                    + "                            <div class=\"position-relative\">\n"
                    + "                                <i class=\"fa-solid fa-trash\" onclick=\"showNoticeDelete(this)\"></i>\n"
                    + "                                <div class=\"notice-delete\">\n"
                    + "                                    <p>Delete this note?</p>\n"
                    + "                                    <form>\n"
                    + "                                        <div class=\"holder-button-delete\">\n"
                    + "                                            <input type=\"hidden\" value="+ note.getNoteID() +" class=\"id-note-delete\">\n"
                    + "                                            <button type=\"button\" class=\"btn-delete-delete\" onclick=\"deleteDeleteNote(this)\">Delete</button>\n"
                    + "                                            <button type=\"button\" class=\"btn-cancel-delete\" onclick=\"cancelDeleteNote(this)\">Cancel</button>\n"
                    + "                                        </div>\n"
                    + "                                    </form>\n"
                    + "                                </div>\n"               
                    + "                            </div>"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"note-item-content\">\n"
                    + "                        " + note.getNoteDescription() + "\n"
                    + "                    </div>\n"
                    + "                    <div class=\"button-for-edit\">\n"
                    + "                         <input type='hidden' value=" + note.getNoteID() + " id='note-id-edit' />\n"
                    + "                         <button type='button' value='save' class='edit-note-cancel-btn' onclick='cancelEditNote(this)'>Cancel</button>\n"
                    + "                         <button type='button' value='save' class='edit-note-save-btn' onclick='saveEditNote(this)'>Save</button>\n"
                    + "                    </div>"
                    + "                </div></form>");
        }

        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        int accountID = account.getAccountID();
        int lessonID = Integer.parseInt(request.getParameter("lessonID"));
        String noteDescription = request.getParameter("description");
        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        String noteTimeInVideo = request.getParameter("timeVideoNote");
        String isSuccess = new NoteDAO().insertNote(accountID, lessonID, noteDescription, createdTime, noteTimeInVideo);
      
        if ("true".equalsIgnoreCase(isSuccess)) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            out.write("{\"check\":\"true\"}");
            out.flush();
        } else {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            out.write("{\"check\":\"" + isSuccess + "\"}");
            out.flush();
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
