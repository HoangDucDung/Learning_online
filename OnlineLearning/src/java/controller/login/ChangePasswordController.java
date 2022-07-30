package controller.login;

import dao.PasswordDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "ChangePasswordController", urlPatterns = {"/change-password"})

public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("view/change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PasswordDAO passwordDAO = new PasswordDAO();
        Account oldAccount=(Account) request.getSession().getAttribute("account");
        int accountID = oldAccount.getAccountID();
        String newPassword = request.getParameter("newPassword");
        passwordDAO.changePassword(accountID, newPassword);
        String isNoti = "yes";
        request.setAttribute("isNoti", isNoti);
        
        oldAccount.setPassword(newPassword);
        request.getSession().setAttribute("account", oldAccount);
        
        request.getRequestDispatcher("view/change-password.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
