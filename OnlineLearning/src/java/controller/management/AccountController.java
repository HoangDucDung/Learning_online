package controller.management;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import dao.AccountDAO;
import dao.RoleDAO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import model.Role;

@WebServlet(name = "AccountController", urlPatterns = {"/management/account"})
public class AccountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accountDAo = new AccountDAO();
        RoleDAO roleDAO = new RoleDAO();
        String type = request.getParameter("cid");
        String cid = "-1";
        if (type != null) {
            cid = type;
        }
        String status_raw = request.getParameter("status");
        String status = "-1";
        if (status_raw != null && status_raw.equalsIgnoreCase("true")) {
            status = "true";
        } else if (status_raw != null && status_raw.equalsIgnoreCase("false")) {
            status = "false";
        } else {
            status = "-1";
        }

        
        
        ArrayList<Role> allRole_Name = new ArrayList<>();
        if (status.equalsIgnoreCase("-1") && cid.equalsIgnoreCase("-1")) {
            ArrayList<Account> allAccount = accountDAo.getAllAccount();
            allRole_Name = roleDAO.allRole_Name();
            request.setAttribute("listAccount", allAccount);
        }
        
        if (!status.equalsIgnoreCase("-1") || !cid.equalsIgnoreCase("-1")) {
            ArrayList<Account> filterResult = accountDAo.searchBy_Filter(cid, status);
            allRole_Name = roleDAO.allRole_Name();
            request.setAttribute("listAccount", filterResult);
        }
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        request.setAttribute("allRoleName", allRole_Name);
        request.setAttribute("request", request.getRequestURI() + "?");
        request.getRequestDispatcher("../view/account-management.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountDAO accountDAO = new AccountDAO();
        if (request.getParameter("id-hide") != null) {
            accountDAO.setStatusAccount(Integer.parseInt(request.getParameter("id-hide")), false);
        }
        if (request.getParameter("id-show") != null) {
            accountDAO.setStatusAccount(Integer.parseInt(request.getParameter("id-show")), true);
        }
        response.getWriter().flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        new AccountDAO().deleteAccountById(id);
        response.getWriter().flush();
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
