package controller.login;

import dao.AccountDAO;
import dao.PasswordDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

@WebServlet(name = "VerifyController", urlPatterns = {"/verify"})

public class VerifyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accountDao = new AccountDAO();
        PasswordDAO passwordDao = new PasswordDAO();
        String email = request.getParameter("email");
        String fname = request.getParameter("f-name");
        String lname = request.getParameter("l-name");
        boolean gender = request.getParameter("gender").equals("Male");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        
        accountDao.insertAccount(email, fname, lname, gender, phone, true);
        
        int accountId = accountDao.getAccountIdByEmail(email);

        passwordDao.insertPassword(accountId, password);
        
        Account account = accountDao.getAccount(email, password);
        
        request.getSession().setAttribute("account", account);

        response.sendRedirect("home");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
