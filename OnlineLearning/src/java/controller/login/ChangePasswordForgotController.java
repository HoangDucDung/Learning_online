
package controller.login;

import dao.PasswordDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ChangePasswordForgotController", urlPatterns = {"/change-password-forgot"})
public class ChangePasswordForgotController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("view/change-password-forgot.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String newPass = request.getParameter("new-password");
        int accountId = (int)request.getSession().getAttribute("accountId");
        new PasswordDAO().changePassword(accountId, newPass);
        request.setAttribute("success", "Password has been changed succesfully!");
        request.getRequestDispatcher("view/change-password-forgot.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
