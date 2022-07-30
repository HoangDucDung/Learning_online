
package controller.management;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;


@WebServlet(name = "AccountDetailController", urlPatterns = {"/management/accountdetail"})
public class AccountDetailController extends HttpServlet {

    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        AccountDAO AccountDAO = new AccountDAO();
        Account Accountsearchby_ID = AccountDAO.searchby_ID(id);
        request.setAttribute("Accountsearchby_ID", Accountsearchby_ID);
        request.getRequestDispatcher("../view/account-detail.jsp").forward(request, response);
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
