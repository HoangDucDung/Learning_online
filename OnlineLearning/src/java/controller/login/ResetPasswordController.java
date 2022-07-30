package controller.login;

import config.AppConfig;
import dao.AccountDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.SendEmail;

@WebServlet(name = "ResetPasswordController", urlPatterns = {"/reset"})


public class ResetPasswordController extends HttpServlet {  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/reset-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email-address");
        
        boolean isExistAccount = new AccountDAO().isExistAccount(email);
        
        if(!isExistAccount) {
            request.setAttribute("error", "Email is not exist. Please try again!");
        }
        else {
            String subject = "Change your password";
            String message = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head></head>\n"
                + "<body style=\"color:#000;\">\n"
                + "    <h3>Online learning system</h3>\n"
                + "    <p>Please click here to change your password</p>\n"
                + "    <form id=\"myForm\" method=\"POST\" action=" + AppConfig.LINK_CHANGE_PASSWORD + ">\n"
                + "        <input type=\"hidden\" value=" + email + " id=\"email\" name=\"email\">\n"
                + "        <input type=\"submit\" value=\"Change password\" \n"
                + "            style=\"padding: 10px 15px;color: #fff;background-color: rgb(0, 149, 255);border-radius: 10px;border:none\">\n"
                + "    </form>\n"
                + "    <h4>Thank you very much</h4>\n"
                + "</body>\n"
                + "</html>";
        
        SendEmail.sendMail(email, subject, message, AppConfig.USERNAME_EMAIL, AppConfig.PASSWORD_EMAIL);
        new AccountDAO().updateDateModify(email);
        request.setAttribute("success", "Change password link has been sent to your email");
        }
        
        request.getRequestDispatcher("view/reset-password.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
