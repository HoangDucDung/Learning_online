package controller.management;

import dao.AccountDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "AccountListController", urlPatterns = {"/management/account-list"})
public class AccountListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> accounts = null;
        try {
            accounts = accountDAO.findAll();
        } catch (Exception ex) {
            Logger.getLogger(AccountListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("../view/account-list.jsp").forward(request, response);
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