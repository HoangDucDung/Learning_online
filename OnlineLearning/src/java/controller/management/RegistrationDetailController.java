package controller.management;

import dao.TransactionHistoryDAO;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TransactionHistory;

@WebServlet(name = "RegistrationDetailController", urlPatterns = {"/management/registration-detail"})
public class RegistrationDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        TransactionHistory trans = new TransactionHistoryDAO().getTransactionHistoryById(id);
        request.setAttribute("trans", trans);
        
        Date unlimited = new Date(-1);
        request.setAttribute("unlimited", unlimited);
        
        Date now = Date.valueOf(LocalDate.now());
        request.setAttribute("now", now);
        request.getRequestDispatcher("../view/registration-detail-management.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
