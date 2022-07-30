package controller.management;

import dao.QuizDAO;
import dao.SubjectDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.QuizLesson;
import model.Subject;

/**
 *
 * @author duc21
 */
@WebServlet(name = "QuizSettingController", urlPatterns = {"/management/quizsetting"})
public class QuizSettingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO QuizDAO = new QuizDAO();
        SubjectDAO subjectDAO = new SubjectDAO();

        String id_raw = request.getParameter("id");
        int id = -1;
        if (id_raw != null) {
            id = Integer.parseInt(id_raw);
        } else {
            id = -1;
        }
        String type_raw = request.getParameter("type");
        String type = "-1";
        if (type_raw != null) {
            type = type_raw;
        }

        ArrayList<QuizLesson> allQuizLesson_Type = new ArrayList<>();
        ArrayList<Subject> allSubjectName = new ArrayList<>();

        if (id == -1 && type.equalsIgnoreCase("-1")) {
            ArrayList<QuizLesson> allQuizLessons = QuizDAO.getAllQuizLessons();
            for (QuizLesson allQuizLesson : allQuizLessons) {
                System.out.println("ergerth" + allQuizLesson);
            }
            allQuizLesson_Type = QuizDAO.getAllQuizType();
            allSubjectName = subjectDAO.getAllSubjectName();
            for (QuizLesson allQuizLesson : allQuizLessons) {
                System.out.println("totalques: " + allQuizLesson.getTotalQues());
            }
            request.setAttribute("listQuizLesson", allQuizLessons);

        }
        
        if (id != -1 || !type.equalsIgnoreCase("-1")) {
            ArrayList<QuizLesson> filterResult = QuizDAO.searchBy_Filter(id, type);
            allQuizLesson_Type = QuizDAO.getAllQuizType();
            allSubjectName = subjectDAO.getAllSubjectName();
            
            request.setAttribute("listQuizLesson", filterResult);
        }
        request.setAttribute("request", request.getRequestURI() + "?");
        request.setAttribute("allSubjectName", allSubjectName);
        request.setAttribute("allQuizType", allQuizLesson_Type);
        request.getRequestDispatcher("../view/quiz-setting.jsp").forward(request, response);
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
