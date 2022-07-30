package controller.myCourse;

import dao.CourseAccountDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "VotedController", urlPatterns = {"/voted"})
public class VotedController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account)request.getSession().getAttribute("account");
        int accountID = account.getAccountID();
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int star = Integer.parseInt(request.getParameter("star"));
        new CourseAccountDAO().votedCourse(accountID, courseId, star);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
