package controller.login;

import dao.PasswordDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author duc21
 */
@WebServlet(name = "ModifiedPasswordController", urlPatterns = {"/modified"})
public class ModifiedPasswordController extends HttpServlet {

    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int accountid = Integer.parseInt(request.getParameter("accountid"));
        request.setAttribute("accountid", accountid);

        request.getRequestDispatcher("view/modified-password.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int accountid = Integer.parseInt(request.getParameter("accountid"));
        String newPass = request.getParameter("newPassword"); 
        PasswordDAO passwordDAO = new PasswordDAO();
        passwordDAO.changePassword(accountid, newPass);
        
        String isNoti = "yes";
        request.setAttribute("isNoti", isNoti);
        
        request.getRequestDispatcher("view/modified-password.jsp").forward(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
