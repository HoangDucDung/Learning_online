package controller.management;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
import dao.*;
import java.util.List;
import javax.ws.rs.core.Response.Status;
import util.RequestParamUtil;

@WebServlet(name = "QuizQuestionController", urlPatterns = {"/management/quiz-question"})
public class QuizQuestionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int quizId = RequestParamUtil.parseInt(req, "quizId");
        QuizLesson quiz = new QuizLessonDAO().findById(quizId);
        
        if(quiz == null) {
            resp.setStatus(Status.NOT_FOUND.getStatusCode());
            return;
        }
        
        List<Question> questions = new QuestionDAO().findByLessonId(quizId);
        List<QuestionLevel> levels = new QuestionLevelDAO().findAll();
        
        req.setAttribute("levels", levels);
        req.setAttribute("quiz", quiz);
        req.setAttribute("questions", questions);
        
        req.getRequestDispatcher("/view/quiz-question.jsp").forward(req, resp);
    }   
    
}

