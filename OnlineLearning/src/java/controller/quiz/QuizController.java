package controller.quiz;

import dao.CompletedLessonDAO;
import dao.LessonDAO;
import dao.QuizLessonDAO;
import dao.QuizSessionDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.CompletedLesson;
import model.QuizLesson;
import model.QuizSession;
import util.SessionUtil;

@WebServlet(name = "QuizController", urlPatterns = {"/quiz"})
public class QuizController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = SessionUtil.getAccount(request);
        int quizId = Integer.parseInt(request.getParameter("id"));

        if (account == null) {
            response.sendError(401);
            return;
        }

        QuizLesson quiz = new QuizLessonDAO().findById(quizId);
        
        if (quiz == null) {
            response.sendError(404);
            return;
        }
        // Must fix this line below
        String quizName = new LessonDAO().getLessonByID(quizId).getName();
        quiz.setName(quizName);
        
        QuizSessionDAO quizSessionDAO = new QuizSessionDAO();
        CompletedLessonDAO completedLessonDAO = new CompletedLessonDAO();
        CompletedLesson CompletedLesson = completedLessonDAO.CompletedLesson(quizId);
        
        if ("true".equals(request.getParameter("redo-quiz"))) {
            try {
                quizSessionDAO.startQuizTime(account, quiz);
                response.sendRedirect("./takequiz?id=" + quiz.getId());
            } catch (Exception ex) {
                response.sendError(500);
                ex.printStackTrace();
            }
        } else {
            Map completedQuizTime = quizSessionDAO.getTimeDoQuiz(account, quiz);
            String dueTime = LocalDateTime.now().plusMinutes(quiz.getExamTimeInMinute())
                        .format(DateTimeFormatter.ofPattern("MMM, dd hh:mm a"));
            QuizSessionDAO QuizSessionDAO = new QuizSessionDAO();
            Account acc = (Account) request.getSession().getAttribute("account");
            QuizSession find = QuizSessionDAO.find(acc.getAccountID(), quizId);
            if(find != null){
            request.setAttribute("lastDoing", CompletedLesson.getEndTime());
            request.setAttribute("score", CompletedLesson.getScore());
            request.setAttribute("total", quiz.getTotalQues());
            request.setAttribute("passScore", quiz.getPassScore());
            }
            request.setAttribute("find", find);
            request.setAttribute("dueTime", dueTime);
            request.setAttribute("quiz", quiz);
            request.setAttribute("completedQuizTime", completedQuizTime);
            request.getRequestDispatcher("./view/quiz-lesson.jsp").forward(request, response);
        }
    }
}
