package controller.management;

import com.google.gson.Gson;
import dao.AnswerDAO;
import dao.QuestionDAO;
import dao.QuestionLevelDAO;
import dao.QuizLessonDAO;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import model.Answer;
import model.Question;
import model.QuestionLevel;
import model.QuizLesson;
import util.RequestParamUtil;

@WebServlet(name = "QuestionController", urlPatterns = {"/management/question"})
public class QuestionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int questionId = util.RequestParamUtil.parseInt(req, "questionId");
        Question question = new QuestionDAO().findById(questionId);

        if (question == null) {
            resp.setStatus(404);
            return;
        }
        List<QuestionLevel> levels = new QuestionLevelDAO().findAll();
        
        req.setAttribute("levels", levels);
        req.setAttribute("question", question);

        req.getRequestDispatcher("/view/edit-question.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String questionText = req.getParameter("questionTextInput");
        int questionLevelId = RequestParamUtil.parseInt(req, "questionLevelId");
        int quizId = RequestParamUtil.parseInt(req, "quizId");

        Question question = new Question();
        question.setText(questionText);
        question.setLessonId(quizId);
        question.setQuestionLevelId(questionLevelId);
        question.setActive(true);
        
        QuestionDAO questionDAO = new QuestionDAO();

        int id = questionDAO.save(question);

        resp.sendRedirect("./question?questionId=" + id);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(req.getInputStream());
        Question question = gson.fromJson(reader, Question.class);
        QuestionDAO questionDAO = new QuestionDAO();
        questionDAO.update(question);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int questionId = RequestParamUtil.parseInt(req, "questionId");
        QuestionDAO questionDAO = new QuestionDAO();
        try {
            questionDAO.delete(questionId);
            resp.setStatus(Status.OK.getStatusCode());
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(Status.NOT_FOUND.getStatusCode());
        }
    }
}
