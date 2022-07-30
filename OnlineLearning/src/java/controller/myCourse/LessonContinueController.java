package controller.myCourse;

import dao.LessonBeingLearnedDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.LessonBeingLearned;

@WebServlet(name = "LessonContinueController", urlPatterns = {"/lesson/continue"})
public class LessonContinueController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        int accountID = account.getAccountID();
        int lessonID = Integer.parseInt(request.getParameter("lessonID"));
        int timeContinue = Integer.parseInt(request.getParameter("timeContinue"));
        LessonBeingLearnedDAO lessonBeingDao = new LessonBeingLearnedDAO();
        LessonBeingLearned lessonBeingLearned = lessonBeingDao.getLessonBeingLearned(accountID, lessonID);
        
        if(lessonBeingLearned == null) {
            lessonBeingDao.insertLessonBeingLearned(accountID,lessonID, timeContinue);
        } else {
            lessonBeingDao.updateLessonBeingLearned(accountID,lessonID, timeContinue);
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
