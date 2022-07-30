package controller.quiz;

import dao.QuizLessonDAO;
import dao.QuizSessionDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.QuizLesson;
import model.QuizSession;
import util.SessionUtil;

@WebServlet(name = "TakeQuizController", urlPatterns = {"/takequiz"})
public class TakeQuizController extends HttpServlet {

    private boolean canUserTakeQuiz(Account account, QuizLesson quiz) {
        QuizSessionDAO quizSessionDAO = new QuizSessionDAO();
        LocalDateTime current = LocalDateTime.now();
        QuizSession session = quizSessionDAO.find(account, quiz);
        
        if (current.isBefore(session.getExpiredTime())) {
            return true;
        }

        return false;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Account account = SessionUtil.getAccount(req);
        int quizId = Integer.parseInt(req.getParameter("id"));
        QuizLesson quiz = new QuizLessonDAO().findById(quizId);
        
        if(account == null) {
            resp.sendError(401);
            return;
        }
        
        if(quiz == null) {
            resp.sendError(404);
            return;
        }
        
        Map<String, LocalDateTime> quizCompletedTime = new QuizSessionDAO().getTimeDoQuiz(account, quiz);
        if(quizCompletedTime.get("EndTime") != null) {
            resp.sendRedirect("./quiz?id=" + quizId);
            return;
        }
        
        if (canUserTakeQuiz(account, quiz)) {
            req.getRequestDispatcher("./view/question.jsp").forward(req, resp);
        } else {
            resp.sendError(403, "You can not do this quiz");
        }

    }
}
