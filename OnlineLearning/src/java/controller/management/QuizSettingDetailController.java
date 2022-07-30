package controller.management;

import dao.CompletedQuestionDAO;
import dao.QuizDAO;
import dao.QuizSessionDAO;
import dao.SubjectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.CompletedQuestion;
import model.QuizLesson;
import model.QuizSession;
import model.Subject;

/**
 *
 * @author duc21
 */
@WebServlet(name = "QuizSettingDetailController", urlPatterns = {"/management/quizsettingdetail"})
public class QuizSettingDetailController extends HttpServlet {

    
    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        QuizDAO QuizDAO = new QuizDAO();
        Account acc = (Account) request.getSession().getAttribute("account");
        SubjectDAO SubjectDAO = new SubjectDAO();
        QuizLesson quizLessonDetail = QuizDAO.getQuizLessonDetail(id);
        Subject subjectNamebyID = SubjectDAO.getSubjectNamebyID(quizLessonDetail.getSubjectID());
        System.out.println("quizLessonDetail" + quizLessonDetail);
        System.out.println("acc" + acc.getAccountID());

        QuizSessionDAO QuizSessionDAO = new QuizSessionDAO();
        QuizSession find = QuizSessionDAO.find(acc.getAccountID(), id);
        String alert = null;
        if(find != null){
            alert = "This Quiz has been taken. You can't edit";
        }
        
        request.setAttribute("alert", alert);
        request.setAttribute("subjectNamebyID", subjectNamebyID);
        request.setAttribute("quizLessonDetail", quizLessonDetail);
        request.getRequestDispatcher("../view/quizzsetting-detail.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
