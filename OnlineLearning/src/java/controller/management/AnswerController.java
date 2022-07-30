package controller.management;

import com.google.gson.Gson;
import dao.AnswerDAO;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import model.Answer;
import util.RequestParamUtil;

@WebServlet(name = "AnswerController", urlPatterns = {"/management/answer"})
public class AnswerController extends HttpServlet {

    /**
     * Create an Answer POST /management/answer.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.APPLICATION_JSON);

        Answer answer = new Answer();
        answer.setText(req.getParameter("text"));
        answer.setExplain(req.getParameter("explain"));
        answer.setStatus(RequestParamUtil.parseInt(req, "status"));
        answer.setQuestionId(RequestParamUtil.parseInt(req, "questionId"));

        AnswerDAO answerDAO = new AnswerDAO();
        try {
            int id = answerDAO.save(answer);
            answer.setId(id);
            Gson gson = new Gson();

            resp.getWriter().print(gson.toJson(answer));
            resp.setStatus(Status.OK.getStatusCode());
        } catch (Exception ex) {
            resp.setStatus(Status.BAD_REQUEST.getStatusCode());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Reader reader = new InputStreamReader(req.getInputStream());
        Gson gson = new Gson();
        Answer answer = gson.fromJson(reader, Answer.class);
        AnswerDAO answerDAO = new AnswerDAO();
        System.out.println(gson.toJson(answer));
        try {
            answerDAO.update(answer);
            resp.setStatus(Status.OK.getStatusCode());
        } catch (Exception ex) {
            resp.setStatus(Status.NOT_FOUND.getStatusCode());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnswerDAO answerDAO = new AnswerDAO();
        try {
            int id = RequestParamUtil.parseInt(req, "id");
            answerDAO.delete(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(Status.NOT_FOUND.getStatusCode());
        }
    }
}
